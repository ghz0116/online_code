package com.zuel.onlineCode.util;

import com.zuel.onlineCode.dto.UserDTO;
import com.zuel.onlineCode.entity.User;

public class UserConverter {
    public static UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setRealName(user.getRealName());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setHeadPortrait(user.getHeadPortrait());
        dto.setIdentity(user.getIdentity());
        dto.setTelephone(user.getTelephone());
        dto.setGender(user.getGender());
        dto.setBirthday(user.getBirthday());
        return dto;
    }
}