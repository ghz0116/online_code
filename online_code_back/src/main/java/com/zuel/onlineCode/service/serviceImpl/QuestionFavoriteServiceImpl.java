package com.zuel.onlineCode.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zuel.onlineCode.entity.QuestionFavorite;
import com.zuel.onlineCode.mapper.QuestionFavoriteMapper;
import com.zuel.onlineCode.service.QuestionFavoriteService;
import org.springframework.stereotype.Service;

@Service
public class QuestionFavoriteServiceImpl extends ServiceImpl<QuestionFavoriteMapper, QuestionFavorite> implements QuestionFavoriteService {
    @Override
    public void changeFavorite(int userId, int questionId) {
        QueryWrapper<QuestionFavorite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("question_id", questionId);
        QuestionFavorite one = getOne(queryWrapper);
        if (one != null) {
            remove(queryWrapper);
        } else {
            QuestionFavorite favorite = new QuestionFavorite(0, userId, questionId);
            save(favorite);
        }
    }
}
