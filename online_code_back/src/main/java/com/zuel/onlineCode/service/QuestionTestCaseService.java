package com.zuel.onlineCode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zuel.onlineCode.entity.QuestionTestCase;

import java.util.List;

public interface QuestionTestCaseService extends IService<QuestionTestCase> {
    List<QuestionTestCase> getCasesByQuestionId(Integer questionId);
}
