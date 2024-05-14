package com.zuel.onlineCode.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class QuestionTestCase {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer questionId;
    private String name;
    @TableField(exist = false)
    private HashMap<String, String> parameter;
    private String result;
    private String parameterMap;
}
