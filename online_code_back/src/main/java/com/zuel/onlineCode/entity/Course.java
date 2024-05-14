package com.zuel.onlineCode.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@TableName("course")
public class Course {
    @TableId(value = "course_id")
    private int courseId;
    private String courseName;
    private String courseCover;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private String description;
    private Integer teacherId;
    @TableField(exist = false)
    private User teacher;
    @TableField(exist = false)
    private Integer studentCount;
    @TableField(exist = false)
    private boolean joined;
}
