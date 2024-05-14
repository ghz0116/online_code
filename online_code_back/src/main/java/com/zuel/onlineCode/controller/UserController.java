package com.zuel.onlineCode.controller;

import com.zuel.onlineCode.entity.User;
import com.zuel.onlineCode.service.UserService;
import com.zuel.onlineCode.util.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        return userService.login(user);
    }

    @PostMapping("/personalInfo")
    public ResponseEntity<?> personalInfo(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.personalInfo(userDetails.getUsername());
        return new ResponseEntity<>(UserConverter.convertToDTO(user), HttpStatus.OK);
    }

    @PostMapping("/updatePersonal")
    public ResponseEntity<?> updatePersonal(@RequestBody User user) {
        boolean result = userService.updatePersonal(user);
        if (result) {
            return new ResponseEntity<>("修改成功", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("修改失败", HttpStatus.BAD_REQUEST);
        }
    }


    //上传头像图片
    @PostMapping("/upload_img")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        String fileName = userService.uploadImage(file);
        if (fileName != null) {
            return ResponseEntity.ok(fileName);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //获取头像图片
    @GetMapping(value = "/fetch_img/{filename:.+}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
        return userService.getImage(filename);
    }

    @PostMapping("course_users")
    public ResponseEntity<?> getCourseUsers(int courseId) {
        List<User> courseUsers = userService.getCourseUsers(courseId);
        return new ResponseEntity<>(courseUsers, HttpStatus.OK);
    }


    //添加用户时候，检查用户名是否可用
    @GetMapping("/checkAccount")
    public ResponseEntity<?> checkAccount(String username) {
        boolean result = userService.checkAccount(username);
        if (result) {
            return new ResponseEntity<>("用户名可用", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("用户名已存在", HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/verificationCode")
    public ResponseEntity<?> verificationCode(String email, String username) {
        return userService.matchAccountEmail(username, email);
    }

    @GetMapping("/checkCode")
    public ResponseEntity<?> checkCode(String email, String username, String code) {
        return userService.checkCode(username, email, code);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(String username, String password, String code) {
        return userService.resetPassword(username, password, code);
    }


}
