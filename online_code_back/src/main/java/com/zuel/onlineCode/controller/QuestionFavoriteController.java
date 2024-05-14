package com.zuel.onlineCode.controller;

import com.zuel.onlineCode.mapper.UserMapper;
import com.zuel.onlineCode.service.QuestionFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question_favorite")
public class QuestionFavoriteController {
    @Autowired
    private QuestionFavoriteService favoriteService;
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/change")
    public ResponseEntity<?> changeFavorite(@AuthenticationPrincipal UserDetails userDetails, int questionId) {
        String username = userDetails.getUsername();
        favoriteService.changeFavorite(userMapper.findByUsername(username).getId(), questionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
