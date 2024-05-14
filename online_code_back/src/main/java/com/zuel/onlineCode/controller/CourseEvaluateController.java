package com.zuel.onlineCode.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zuel.onlineCode.entity.Course;
import com.zuel.onlineCode.entity.CourseEvaluate;
import com.zuel.onlineCode.entity.User;
import com.zuel.onlineCode.service.CourseEvaluateService;
import com.zuel.onlineCode.service.CourseService;
import com.zuel.onlineCode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/evaluate")
public class CourseEvaluateController {
    @Autowired
    private CourseEvaluateService evaluateService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;

    private SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping("/addEvaluate")
    public void addEvaluate(@RequestBody CourseEvaluate evaluate) {
        evaluate.setCreateTime(sd.format(new Date()));
        evaluateService.save(evaluate);
    }


    @RequestMapping("/getCourseEvaluate")
    public String getCourseEvaluate(String courseId) {
        List<CourseEvaluate> list = evaluateService.geCourseEvaluate(courseId);
        return JSON.toJSONString(list);
    }


    @RequestMapping("/delete")
    public void deleteEvaluate(String evaluateId) {
        evaluateService.removeById(evaluateId);
    }
}
