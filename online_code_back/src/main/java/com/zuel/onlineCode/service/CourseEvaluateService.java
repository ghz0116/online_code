package com.zuel.onlineCode.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zuel.onlineCode.entity.CourseEvaluate;

import java.util.List;

public interface CourseEvaluateService extends IService<CourseEvaluate> {
    List<CourseEvaluate> geCourseEvaluate(String courseId);
}
