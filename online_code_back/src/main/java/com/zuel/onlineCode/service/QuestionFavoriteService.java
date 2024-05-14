package com.zuel.onlineCode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zuel.onlineCode.entity.QuestionFavorite;

public interface QuestionFavoriteService extends IService<QuestionFavorite> {
    void changeFavorite(int userId, int questionId);
}
