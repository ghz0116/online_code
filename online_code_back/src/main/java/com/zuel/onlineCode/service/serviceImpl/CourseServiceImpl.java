package com.zuel.onlineCode.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zuel.onlineCode.dto.TermScore;
import com.zuel.onlineCode.entity.Course;
import com.zuel.onlineCode.entity.CourseUser;
import com.zuel.onlineCode.entity.User;
import com.zuel.onlineCode.mapper.CourseMapper;
import com.zuel.onlineCode.mapper.CourseUserMapper;
import com.zuel.onlineCode.mapper.QuestionMapper;
import com.zuel.onlineCode.mapper.UserMapper;
import com.zuel.onlineCode.service.CourseService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    @Value("${courseCoverDir}")
    private String COURSE_COVER_DIR;


    @Autowired
    private CourseUserMapper courseUserMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public List<Course> getTeachCourse(String username) {
        User user = userMapper.findByUsername(username);
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id", user.getId());
        List<Course> courses = list(queryWrapper);
        for (Course course : courses) {
            QueryWrapper<CourseUser> wrapper = new QueryWrapper<>();
            wrapper.eq("course_id", course.getCourseId());
            Integer userCount = courseUserMapper.selectCount(wrapper);
            course.setStudentCount(userCount);
        }

        return courses;
    }

    @Override
    public byte[] getCourseCover(String filename) {
        Path imagePath = Paths.get(COURSE_COVER_DIR, filename);
        File imageFile = imagePath.toFile();
        // 检查文件是否存在且可读
        if (imageFile.exists() && imageFile.isFile() && imageFile.canRead()) {
            // 读取文件内容到字节数组
            byte[] imageBytes;
            try {
                imageBytes = Files.readAllBytes(imagePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return imageBytes;
        } else {
            return null;
        }
    }

    @Override
    public boolean addCourse(Course course) {
        String coverBase64 = course.getCourseCover();
        String[] split = coverBase64.split(",");
        String type = split[0].split("/")[1].split(";")[0];
        byte[] imageBytes = Base64.getDecoder().decode(split[1]);
        UUID uuid = UUID.randomUUID();
        String coverName = uuid + "." + type;
        Path path = Paths.get(COURSE_COVER_DIR + coverName);
        course.setCourseCover(coverName);
        try {
            Files.write(path, imageBytes);
            save(course);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Page<Course> getCourseList(String searchContent, int page, String username) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("course_name", searchContent);
        Page<Course> pageQuery = new Page<>(page, 15);
        Page<Course> coursePage = courseMapper.selectPage(pageQuery, queryWrapper);
        for (Course record : coursePage.getRecords()) {
            courseSetStudentCount(record);
            courseSetTeacher(record);
            if (username != null) {
                courseIsJoined(record, username);
            } else {
                record.setJoined(false);
            }
        }
        return coursePage;
    }

    private void courseIsJoined(Course record, String username) {
        User user = userMapper.findByUsername(username);
        QueryWrapper<CourseUser> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", record.getCourseId());
        wrapper.eq("user_id", user.getId());
        Integer count = courseUserMapper.selectCount(wrapper);
        record.setJoined(count > 0);
    }

    public void courseSetTeacher(Course record) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", record.getTeacherId());
        wrapper.select("real_name", "head_portrait");
        User user = userMapper.selectOne(wrapper);
        record.setTeacher(user);
    }

    public void courseSetStudentCount(Course record) {
        QueryWrapper<CourseUser> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", record.getCourseId());
        Integer userCount = courseUserMapper.selectCount(wrapper);
        record.setStudentCount(userCount);
    }

    @Override
    public boolean joinCourse(Integer courseId, String username) {
        User user = userMapper.findByUsername(username);
        CourseUser courseUser = new CourseUser();
        courseUser.setCourseId(courseId);
        courseUser.setUserId(user.getId());
        int insert = courseUserMapper.insert(courseUser);
        return insert > 0;
    }

    @Override
    public Course fetchDetail(Integer courseId, String username) {
        Course course = getById(courseId);
        courseSetStudentCount(course);
        courseSetTeacher(course);
        if (username != null) {
            courseIsJoined(course, username);
        } else {
            course.setJoined(false);
        }
        return course;
    }

    @Override
    public List<Course> top() {
        List<Course> topSix = courseMapper.getTopSix();
        for (Course record : topSix) {
            courseSetTeacher(record);
        }
        return topSix;
    }

    @Override
    public HashMap<String, List> getChartData(Integer courseId) {
        List<HashMap<String, Integer>> passInfo = questionMapper.getPassInfo(courseId);
        ArrayList<String> strings = new ArrayList<>();
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0; i < passInfo.size(); i++) {
            HashMap<String, Integer> map = passInfo.get(i);
            strings.add(map.get("question_id") + "");
            integers.add(map.get("correct_count"));
        }
        HashMap<String, List> stringListHashMap = new HashMap<>();
        stringListHashMap.put("ids", strings);
        stringListHashMap.put("counts", integers);
        return stringListHashMap;
    }

    @Override
    public List<Course> getStudyCourse(String username) {
        User user = userMapper.findByUsername(username);
        QueryWrapper<CourseUser> queryWrapper = new QueryWrapper<>();
        List<CourseUser> courseUsers = courseUserMapper.selectList(queryWrapper);
        ArrayList<Integer> courseIds = new ArrayList<>();
        for (CourseUser courseUser : courseUsers) {
            courseIds.add(courseUser.getCourseId());
        }
        QueryWrapper<Course> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.in("course_id", courseIds);
        List<Course> courses = list(queryWrapper1);
        for (Course course : courses) {
            QueryWrapper<CourseUser> wrapper = new QueryWrapper<>();
            wrapper.eq("course_id", course.getCourseId());
            Integer userCount = courseUserMapper.selectCount(wrapper);
            course.setStudentCount(userCount);
        }
        return courses;
    }

    @Override
    public ByteArrayOutputStream exportScore(Integer courseId) throws IOException {
        // 创建一个工作簿
        Workbook workbook = new XSSFWorkbook();
        // 创建一个工作表
        Sheet sheet = workbook.createSheet("Course Scores");
        // 添加表头
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("账号");
        headerRow.createCell(1).setCellValue("姓名");
        headerRow.createCell(2).setCellValue("得分");
        // 添加数据
        int rowNum = 1;
        for (TermScore termScore : courseMapper.getTermScore(courseId)) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(termScore.getUsername());
            row.createCell(1).setCellValue(termScore.getRealname());
            row.createCell(2).setCellValue(termScore.getScore());
        }
        // 将工作簿写入字节数组
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        // 返回字节数组给前端
        return outputStream;
    }
}




