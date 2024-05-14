package com.zuel.onlineCode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zuel.onlineCode.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where username = #{username}")
    User findByUsername(String username);

    @Select("select id from user")
    List<Integer> selectAllUsersIds();
}

