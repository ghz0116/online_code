package com.zuel.onlineCode.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zuel.onlineCode.dto.CodeRunResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class QuestionAnswer {
    @TableId(value = "answer_id", type = IdType.AUTO)
    private Integer answerId;
    private Integer questionId;
    @TableField(exist = false)
    private String code;
    private Integer userId;
    private String language;
    private String codeFile;
    private boolean correct;
    private long costTime;
    @TableField(exist = false)
    private ArrayList<CodeRunResult> caseResults;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date submitTime;
}
