package com.zuel.onlineCode.service.serviceImpl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zuel.onlineCode.entity.Course;
import com.zuel.onlineCode.entity.CourseEvaluate;
import com.zuel.onlineCode.entity.User;
import com.zuel.onlineCode.mapper.CourseEvaluateMapper;
import com.zuel.onlineCode.mapper.CourseMapper;
import com.zuel.onlineCode.mapper.UserMapper;
import com.zuel.onlineCode.service.CourseEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseEvaluateServiceImpl extends ServiceImpl<CourseEvaluateMapper, CourseEvaluate> implements CourseEvaluateService {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<CourseEvaluate> geCourseEvaluate(String courseId) {
        Course course = courseMapper.selectById(courseId);
        int teacherId = course.getTeacherId();
        QueryWrapper<CourseEvaluate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        queryWrapper.eq("parent_id", 0);
        queryWrapper.orderByDesc("create_time");
        List<CourseEvaluate> list = list(queryWrapper);
        getChildren(list, teacherId);
        return list;
    }

    public List<CourseEvaluate> getChildren(List<CourseEvaluate> evaluates, int teacherId) {
        for (CourseEvaluate evaluate : evaluates) {
            int userId = evaluate.getUserId();
            if (userId == teacherId) {
                evaluate.setIsTeacher(1);
            }
            User user = userMapper.selectById(userId);
            evaluate.setSendUser(user);
            int evaluateId = evaluate.getEvaluateId();
            QueryWrapper<CourseEvaluate> wrapper = new QueryWrapper<>();
            wrapper.eq("parent_id", evaluateId);
            List<CourseEvaluate> list = list(wrapper);
            if (!list.isEmpty()) {
                List<CourseEvaluate> children = getChildren(list, teacherId);
                evaluate.setChildren(children);
            }
        }
        return evaluates;
    }
}
