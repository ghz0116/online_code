package com.zuel.onlineCode.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zuel.onlineCode.entity.CourseUser;
import com.zuel.onlineCode.mapper.CourseUserMapper;
import com.zuel.onlineCode.service.CourseUserService;
import org.springframework.stereotype.Service;

@Service
public class CourseUserServiceImpl extends ServiceImpl<CourseUserMapper, CourseUser> implements CourseUserService {
}
