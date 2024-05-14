package com.zuel.onlineCode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zuel.onlineCode.entity.CourseUser;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseUserMapper extends BaseMapper<CourseUser> {
    @Select("select user_id from course_user where course_id = #{courseId}")
    List<Integer> getCourseUserIds(int courseId);
}
