package com.zuel.onlineCode.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zuel.onlineCode.entity.QuestionTestCase;
import com.zuel.onlineCode.mapper.QuestionTestCaseMapper;
import com.zuel.onlineCode.service.QuestionTestCaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionTestCaseServiceImpl extends ServiceImpl<QuestionTestCaseMapper, QuestionTestCase> implements QuestionTestCaseService {
    @Override
    public List<QuestionTestCase> getCasesByQuestionId(Integer questionId) {
        QueryWrapper<QuestionTestCase> wrapper = new QueryWrapper<>();
        wrapper.eq("question_id", questionId);
        return list(wrapper);
    }
}
