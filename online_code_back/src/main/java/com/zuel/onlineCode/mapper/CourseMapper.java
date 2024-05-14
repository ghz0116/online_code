package com.zuel.onlineCode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zuel.onlineCode.dto.TermScore;
import com.zuel.onlineCode.entity.Course;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseMapper extends BaseMapper<Course> {
    @Select("SELECT c.* \n" +
            "FROM course c \n" +
            "JOIN (\n" +
            "    SELECT course_id \n" +
            "    FROM course_user \n" +
            "    GROUP BY course_id \n" +
            "    ORDER BY COUNT(id) DESC \n" +
            "    LIMIT 6\n" +
            ") cu ON c.course_id = cu.course_id;")
    List<Course> getTopSix();

    @Select("SELECT b.username,b.real_name,IFNULL(c.score, 0) AS score FROM (\n" +
            "SELECT id,username,real_name FROM USER WHERE id IN (\n" +
            "SELECT user_id FROM course_user WHERE course_id =  #{courseId})) AS b LEFT JOIN (\n" +
            "SELECT COUNT(*)*10 AS score,user_id FROM(\n" +
            "SELECT DISTINCT question_id,user_id FROM question_answer WHERE correct = 1 AND  question_id IN ( \n" +
            "SELECT question_id FROM question WHERE course_id =  #{courseId}) AND user_id IN (\n" +
            "SELECT user_id FROM course_user WHERE course_id =  #{courseId})) AS a GROUP BY user_id) AS c ON b.id=c.user_id")
    List<TermScore> getTermScore(Integer courseId);
}
