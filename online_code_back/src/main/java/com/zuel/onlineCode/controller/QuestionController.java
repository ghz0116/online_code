package com.zuel.onlineCode.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zuel.onlineCode.dto.QuestionPage;
import com.zuel.onlineCode.entity.Question;
import com.zuel.onlineCode.entity.QuestionPreCode;
import com.zuel.onlineCode.entity.QuestionTestCase;
import com.zuel.onlineCode.entity.User;
import com.zuel.onlineCode.mapper.UserMapper;
import com.zuel.onlineCode.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserMapper userMapper;

    //获取问题列表
    @PostMapping("/list")
    public ResponseEntity<?> getList(@RequestBody QuestionPage questionPage, @AuthenticationPrincipal UserDetails userDetails) {
        int userId = 0;
        if (userDetails != null) {
            String username = userDetails.getUsername();
            User user = userMapper.findByUsername(username);
            userId = user.getId();
        }
        Page<?> page = questionService.getPage(questionPage, userId);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    //获取课程问题列表
    @PostMapping("/c_questions")
    public ResponseEntity<?> getCourseQuestions(Integer courseId) {
        List<Question> questions = questionService.getQuestionsByCourseId(courseId);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }


    //添加问题
    @PostMapping("/add")
    public ResponseEntity<?> addQuestion(@RequestBody Question question) {
        questionService.addQuestion(question);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //添加问题
    @PostMapping("/delete")
    public ResponseEntity<?> deleteQuestion(int questionId) {
        questionService.deleteQuestion(questionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    //获取问题信息
    @PostMapping("/fetch")
    public ResponseEntity<?> fetchQuestion(@RequestParam("questionId") int questionId) {
        Question one = questionService.getById(questionId);
        return new ResponseEntity<>(one, HttpStatus.OK);
    }

    //获取问题预设代码种类
    @PostMapping("/fetch_code_type")
    public ResponseEntity<?> fetchPreCodeTypes(@RequestParam("questionId") int questionId) {
        ArrayList<String> types = questionService.fetchPreCodeTypes(questionId);
        return ResponseEntity.ok(types);
    }

    @PostMapping("/fetch_test_case")
    public ResponseEntity<?> fetchTestCase(@RequestParam("questionId") int questionId) {
        List<QuestionTestCase> testCases = questionService.fetchTestCases(questionId);
        return ResponseEntity.ok(testCases);
    }

    @PostMapping("/fetch_pre_code")
    public ResponseEntity<?> fetchPreCode(@RequestParam("questionId") int questionId, @RequestParam("language") String language) {
        QuestionPreCode preCode = questionService.fetchPreCode(questionId, language);
        return ResponseEntity.ok(preCode);
    }

    //上传问题图片
    @PostMapping("/upload_img")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        String fileName = questionService.uploadImage(file);
        if (fileName != null) {
            return ResponseEntity.ok(fileName);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    //获取问题图片
    @GetMapping(value = "/fetch_img/{filename:.+}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
        return questionService.getImage(filename);
    }

    @PostMapping("predict_tags")
    public ResponseEntity<?> predictTags(String questionName) {
        String[] strings = questionService.predictTags(questionName);
        return new ResponseEntity<>(strings, HttpStatus.OK);
    }
}
