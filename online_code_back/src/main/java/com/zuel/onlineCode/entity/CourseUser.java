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
public class CourseUser {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Integer courseId;
    private Integer userId;
}
