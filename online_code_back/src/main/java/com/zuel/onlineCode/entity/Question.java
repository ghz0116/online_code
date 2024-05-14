package com.zuel.onlineCode.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@TableName("question")
public class Question {
    @TableId(value = "question_id", type = IdType.AUTO)
    private Integer questionId;
    private String questionName;
    private Integer solutionNum;
    private Double passRate;
    private Integer degree;
    private String questionDetail;
    private Integer courseId;
    @TableField(exist = false)
    private ArrayList<String> tags;
    private String tagsJson;
    @TableField(exist = false)
    private ArrayList<String> parameters;
    private String parameterStr;
    @TableField(exist = false)
    private ArrayList<QuestionPreCode> preCodes;
    @TableField(exist = false)
    private ArrayList<QuestionTestCase> testCases;
    @TableField(exist = false)
    private boolean favorite;
    private String md;
}
