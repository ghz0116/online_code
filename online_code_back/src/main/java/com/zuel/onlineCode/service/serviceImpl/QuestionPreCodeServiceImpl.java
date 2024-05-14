package com.zuel.onlineCode.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zuel.onlineCode.entity.QuestionPreCode;
import com.zuel.onlineCode.mapper.QuestionPreCodeMapper;
import com.zuel.onlineCode.service.QuestionPreCodeService;
import org.springframework.stereotype.Service;

@Service
public class QuestionPreCodeServiceImpl extends ServiceImpl<QuestionPreCodeMapper, QuestionPreCode> implements QuestionPreCodeService {
}
