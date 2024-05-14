package com.zuel.onlineCode.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.zuel.onlineCode.entity.User;
import com.zuel.onlineCode.mapper.UserMapper;
import com.zuel.onlineCode.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {


    private final UserMapper userMapper;

    @Autowired
    public AuthenticationFilter(UserMapper userMapper) {
        super();
        this.userMapper = userMapper;
    }

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //    @NonNullApi 注解用于指示接口、类或方法的非空约束。如果未在方法上注释参数，可能会导致潜在的空指针异常。
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        if (token != null) {
            DecodedJWT verify = JWTUtils.verify(token);
            if (verify != null) {
                String username = verify.getClaim("username").asString();
                if (username != null) {
                    User user = userMapper.findByUsername(username);
                    UsernamePasswordAuthenticationToken userAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(userAuthenticationToken);
                }
            }
        }
// 放行所有请求
        filterChain.doFilter(request, response);
    }
}



