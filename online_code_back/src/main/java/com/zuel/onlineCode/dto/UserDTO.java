package com.zuel.onlineCode.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@TableName("user")
public class UserDTO {
    @TableId(value = "id")
    private Integer id;
    private String realName;
    private String username;
    private String email;
    private String headPortrait;
    private int identity;
    private String telephone;
    private int gender;
    private Date birthday;


}
