package com.zuel.onlineCode.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class QuestionPreCode {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer questionId;
    private String type;
    private String code;
}
