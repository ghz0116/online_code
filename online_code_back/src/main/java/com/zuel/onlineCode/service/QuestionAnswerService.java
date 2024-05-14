package com.zuel.onlineCode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zuel.onlineCode.dto.CodeRunResult;
import com.zuel.onlineCode.entity.Question;
import com.zuel.onlineCode.entity.QuestionAnswer;
import com.zuel.onlineCode.entity.QuestionTestCase;

import java.util.ArrayList;
import java.util.List;

public interface QuestionAnswerService extends IService<QuestionAnswer> {
    String saveCodeToLocal(String code);

    ArrayList<CodeRunResult> runCode(Question question, QuestionAnswer answer, List<QuestionTestCase> testCases);

    List<QuestionAnswer> getSubmitRecords(Integer id, int questionId);

    String getAnswerCode(Integer answerId);
}
