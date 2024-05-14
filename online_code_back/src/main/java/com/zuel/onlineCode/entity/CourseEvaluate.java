package com.zuel.onlineCode.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CourseEvaluate {
    @TableId
    private int evaluateId;
    private int parentId;
    private String createTime;
    private int courseId;
    private int userId;
    @TableField(exist = false)
    private int isTeacher;
    private String content;
    @TableField(exist = false)
    private List<CourseEvaluate> children;
            @TableField(exist = false)
    private User sendUser;
}
