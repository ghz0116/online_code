package com.zuel.onlineCode.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class QuestionAnswerResult {
    private Integer id;
    private Integer answerId;
    private String info;
    private double memorySize;
    private Integer seconds;
}
