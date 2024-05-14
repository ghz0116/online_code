package com.zuel.onlineCode.controller;

import com.zuel.onlineCode.dto.CodeRunResult;
import com.zuel.onlineCode.entity.Question;
import com.zuel.onlineCode.entity.QuestionAnswer;
import com.zuel.onlineCode.entity.QuestionTestCase;
import com.zuel.onlineCode.entity.User;
import com.zuel.onlineCode.mapper.UserMapper;
import com.zuel.onlineCode.service.QuestionAnswerService;
import com.zuel.onlineCode.service.QuestionService;
import com.zuel.onlineCode.service.QuestionTestCaseService;
import com.zuel.onlineCode.util.AiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/answer")
public class QuestionAnswerController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionAnswerService answerService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionTestCaseService testCaseService;

    //运行用户编写的代码
    @PostMapping("/submit")
    public ResponseEntity<?> submitAnswer(@AuthenticationPrincipal UserDetails userDetails, @RequestBody QuestionAnswer questionAnswer) {
        User user = userMapper.findByUsername(userDetails.getUsername());
        questionAnswer.setSubmitTime(new Date());
        questionAnswer.setUserId(user.getId());
        Integer questionId = questionAnswer.getQuestionId();
        //先将用户代码存放至本地文档
        String fileName = answerService.saveCodeToLocal(questionAnswer.getCode());
        questionAnswer.setCodeFile(fileName);
        //获取问题参数
        Question question = questionService.getById(questionId);
        //获取问题测试实例
        List<QuestionTestCase> testCases = testCaseService.getCasesByQuestionId(questionId);
        //运行并保存代码
        ArrayList<CodeRunResult> runResults = answerService.runCode(question, questionAnswer, testCases);
        return new ResponseEntity<>(runResults, HttpStatus.OK);
    }


    @PostMapping("/askAI")
    public ResponseEntity<?> askAI(@RequestBody QuestionAnswer questionAnswer) {
        try {
            return new ResponseEntity<>(AiUtil.ask(questionAnswer.getCode()), HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/records")
    public ResponseEntity<?> submitRecords(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("questionId") Integer questionId) {
        User user = userMapper.findByUsername(userDetails.getUsername());
        List<QuestionAnswer> answers = answerService.getSubmitRecords(user.getId(), questionId);
        return new ResponseEntity<>(answers, HttpStatus.OK);
    }
    @PostMapping("answer_code")
    public ResponseEntity<?> getAnswerCode(@RequestParam("answerId")Integer answerId){
        String code = answerService.getAnswerCode(answerId);
        return new ResponseEntity<>(code, HttpStatus.OK);
    }
}
