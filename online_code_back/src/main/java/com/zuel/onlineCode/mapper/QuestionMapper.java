package com.zuel.onlineCode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zuel.onlineCode.entity.Question;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface QuestionMapper extends BaseMapper<Question> {
    @Select("SELECT COUNT(user_id) AS correct_count,question_id FROM(\n" +
            "SELECT DISTINCT question_id, user_id\n" +
            "FROM question_answer\n" +
            "WHERE correct = 1 AND  \n" +
            "question_id IN ( SELECT question_id FROM question WHERE course_id = #{courseId}) AND \n" +
            "user_id IN (SELECT user_id FROM course_user WHERE course_id = #{courseId})) AS a GROUP BY question_id")
    public List<HashMap<String, Integer>> getPassInfo(Integer courseId);
}
