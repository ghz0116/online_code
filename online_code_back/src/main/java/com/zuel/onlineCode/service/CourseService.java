package com.zuel.onlineCode.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zuel.onlineCode.entity.Course;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface CourseService extends IService<Course> {
    List<Course> getTeachCourse(String username);

    byte[] getCourseCover(String filename);

    boolean addCourse(Course course);

    Page<Course> getCourseList(String searchContent, int page, String username);

    boolean joinCourse(Integer courseId, String username);

    Course fetchDetail(Integer courseId, String username);

    List<Course> top();

    HashMap<String, List> getChartData(Integer courseId);

    List<Course> getStudyCourse(String username);

    ByteArrayOutputStream exportScore(Integer courseId) throws IOException;
}
