package com.zuel.onlineCode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zuel.onlineCode.entity.QuestionAnswer;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionAnswerMapper extends BaseMapper<QuestionAnswer> {
    @Select("select count(*) from question_answer where question_id=#{questionId}")
    Integer getSolutionNum(Integer questionId);

    @Select("SELECT \n" +
            "    SUM(CASE WHEN correct = 1 THEN 1 ELSE 0 END) / COUNT(*) AS ratio\n" +
            "FROM \n" +
            "    question_answer \n" +
            "WHERE \n" +
            "    question_id = #{questionId};")
    Double getQuestionRate(Integer questionId);


}


