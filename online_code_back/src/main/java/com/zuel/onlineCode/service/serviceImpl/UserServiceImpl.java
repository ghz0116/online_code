package com.zuel.onlineCode.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zuel.onlineCode.entity.CourseUser;
import com.zuel.onlineCode.entity.User;
import com.zuel.onlineCode.mapper.CourseUserMapper;
import com.zuel.onlineCode.mapper.UserMapper;
import com.zuel.onlineCode.service.UserService;
import com.zuel.onlineCode.util.JWTUtils;
import com.zuel.onlineCode.util.MailUtil;
import com.zuel.onlineCode.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final UserMapper userMapper;

    private final CourseUserMapper courseUserMapper;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Value("${headPortraitDir}")
    private String HEAD_PORTRAIT_DIR;


    @Autowired
    public UserServiceImpl(UserMapper userMapper, AuthenticationManager authenticationManager, CourseUserMapper courseUserMapper) {
        super();
        this.userMapper = userMapper;
        this.courseUserMapper = courseUserMapper;
    }


    @Override
    public ResponseEntity<?> matchAccountEmail(String username, String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user1 = userMapper.selectOne(queryWrapper);
        if (user1 == null) {
            return new ResponseEntity<>("用户名不存在", HttpStatus.BAD_REQUEST);
        } else {
            if (user1.getEmail().equals(email)) {
                return sendVerificationCode(user1);
            } else {
                return new ResponseEntity<>("邮箱不匹配", HttpStatus.BAD_REQUEST);
            }
        }
    }

    @Override
    public ResponseEntity<?> checkCode(String username, String email, String code) {
        String userJson = RedisUtil.get(code);
        if (userJson == null) {
            return new ResponseEntity<>("验证码已过期", HttpStatus.BAD_REQUEST);
        } else {
            User user = JSON.parseObject(userJson, User.class);
            if (user.getUsername().equals(username) && user.getEmail().equals(email)) {
                return new ResponseEntity<>("验证成功", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("验证失败", HttpStatus.BAD_REQUEST);
            }
        }
    }

    @Override
    public ResponseEntity<?> resetPassword(String username, String password, String code) {
        String userJson = RedisUtil.get(code);
        if (userJson == null) {
            return new ResponseEntity<>("设置密码超时", HttpStatus.BAD_REQUEST);
        } else {
            User user = JSON.parseObject(userJson, User.class);
            if (user.getUsername().equals(username)) {
                user.setPassword(passwordEncoder.encode(password));
                userMapper.updateById(user);
                RedisUtil.remove(code);
                return new ResponseEntity<>("密码重置成功", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("用户名不匹配", HttpStatus.BAD_REQUEST);
            }
        }
    }

    private ResponseEntity<?> sendVerificationCode(User user) {
        Random random = new Random();
        StringBuilder verificationCode = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            verificationCode.append(random.nextInt(10));
        }
        //验证码 用户信息
        RedisUtil.set(verificationCode.toString(), JSON.toJSONString(user), 60 * 5);
        try {
            MailUtil.sendMail(user.getEmail(), "验证码", "您的验证码为：" + verificationCode + "，有效期为5分钟，请尽快验证。");
        } catch (Exception e) {
            return new ResponseEntity<>("服务器繁忙，请稍后再试", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("验证码已发送", HttpStatus.OK);
    }


    /**
     * 认证管理器
     */

    @Override
    public ResponseEntity<?> login(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", user.getUsername());
        int identity = user.getIdentity();
        wrapper.eq("identity", identity);
        User userDetail = userMapper.selectOne(wrapper);
        if (userDetail == null) {
            return new ResponseEntity<>("用户名不存在", HttpStatus.UNAUTHORIZED); // 使用UNAUTHORIZED状态码
        }

        // 密码匹配
        if (!passwordEncoder.matches(user.getPassword(), userDetail.getPassword())) {
            return new ResponseEntity<>("密码错误", HttpStatus.UNAUTHORIZED); // 使用UNAUTHORIZED状态码
        }

        // 生成JWT，只存储标识信息，不包括密码
        HashMap<String, String> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("role", String.valueOf(user.getIdentity())); // 使用角色标识符而不是字符串

        // 设置过期时间，默认为
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, 1); // 设置为一个月
        String token = JWTUtils.createToken(claims, calendar.getTime()); // 设置过期时间
        // 构建响应
        HashMap<String, String> resultMap = new HashMap<>();
        resultMap.put("token", token);
        userDetail.setPassword(null);
        resultMap.put("user", JSON.toJSONString(userDetail));
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @Override
    public User personalInfo(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        wrapper.select("id", "username", "real_name", "email", "telephone", "identity", "gender", "birthday", "head_portrait");
        return getOne(wrapper);
    }

    @Override
    public boolean updatePersonal(User user) {
        User byId = getById(user.getId());
        user.setPassword(byId.getPassword());
        return updateById(user);
    }

    @Override
    public String uploadImage(MultipartFile file) {
        try {
            // 检查文件是否为空
            if (file.isEmpty()) {
                return null;
            }
            String originalFilename = file.getOriginalFilename();
            String[] split = new String[0];
            if (originalFilename != null) {
                split = originalFilename.split("\\.");
            }
            String fileType = split[split.length - 1];
            // 获取文件名
            String fileName = UUID.randomUUID() + "." + fileType;
            String filePath = Paths.get(HEAD_PORTRAIT_DIR, fileName).toString();
            // 将文件保存到服务器
            Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (Exception e) {
            // 处理异常，例如文件保存失败等
            return null;
        }
    }

    @Override
    public ResponseEntity<byte[]> getImage(String filename) {
        try {
            // 这里我们假设图片都放在resources/static/images目录下
            Path imagePath = Paths.get(HEAD_PORTRAIT_DIR, filename);
            File imageFile = imagePath.toFile();
            // 检查文件是否存在且可读
            if (imageFile.exists() && imageFile.isFile() && imageFile.canRead()) {
                // 读取文件内容到字节数组
                byte[] imageBytes = Files.readAllBytes(imagePath);
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(imageBytes);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public List<User> getCourseUsers(int courseId) {
        List<Integer> courseUserIds = courseUserMapper.getCourseUserIds(courseId);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.in("id", courseUserIds);
        wrapper.select("real_name","head_portrait");
        return list(wrapper);
    }

    /**
     * 检查账号是否存在
     *
     * @param username 用户名
     * @return true 不存在
     */
    @Override
    // 检查账号是否存在
    public boolean checkAccount(String username) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        count(userQueryWrapper);
        return count(userQueryWrapper) == 0;
    }
}
