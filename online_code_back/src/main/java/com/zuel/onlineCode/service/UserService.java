package com.zuel.onlineCode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zuel.onlineCode.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService extends IService<User> {


    ResponseEntity<?> matchAccountEmail(String username, String email);

    ResponseEntity<?> checkCode(String username, String email, String code);

    ResponseEntity<?> resetPassword(String username, String password, String code);

    boolean checkAccount(String username);


    ResponseEntity<?> login(User user);


    User personalInfo(String username);

    boolean updatePersonal(User user);

    String uploadImage(MultipartFile file);

    ResponseEntity<byte[]> getImage(String filename);

    List<User> getCourseUsers(int courseId);
}

