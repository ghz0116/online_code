package com.zuel.onlineCode.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class QuestionFavorite {
    @TableId
    private Integer id;
    private Integer userId;
    private Integer questionId;
}
