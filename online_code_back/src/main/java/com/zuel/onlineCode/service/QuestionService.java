package com.zuel.onlineCode.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zuel.onlineCode.dto.QuestionPage;
import com.zuel.onlineCode.entity.Question;
import com.zuel.onlineCode.entity.QuestionPreCode;
import com.zuel.onlineCode.entity.QuestionTestCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public interface QuestionService extends IService<Question> {
    Page<?> getPage(QuestionPage questionPage, Integer id);

    void addQuestion(Question question);

    String uploadImage(MultipartFile file);

    ResponseEntity<byte[]> getImage(String filename);


    ArrayList<String> fetchPreCodeTypes(int questionId);

    List<QuestionTestCase> fetchTestCases(int questionId);

    QuestionPreCode fetchPreCode(int questionId, String type);

    List<Question> getQuestionsByCourseId(Integer courseId);

    String[] predictTags(String questionName);

    void deleteQuestion(int questionId);
}
