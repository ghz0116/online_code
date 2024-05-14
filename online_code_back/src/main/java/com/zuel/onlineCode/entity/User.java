package com.zuel.onlineCode.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@TableName("user")
public class User implements UserDetails {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String realName;
    private String username;
    private String password;
    private String email;
    private String headPortrait;//头像
    private int identity;//1是学生，2是老师
    private String telephone;
    private int gender;
    private Date birthday;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = null;
        if (this.identity == 1) {
            authority = new SimpleGrantedAuthority("ROLE_STUDENT");
        } else if (this.identity == 2) {
            authority = new SimpleGrantedAuthority("ROLE_TEACHER");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority); // 假设管理员角色为 "ROLE_ADMIN"
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
