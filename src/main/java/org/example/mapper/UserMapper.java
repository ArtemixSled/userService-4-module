package org.example.mapper;

import org.example.dto.UserDto;
import org.example.model.User;

/**
 * Класс для преобразования между сущностью User и DTO UserDto.
 */

public class UserMapper {

    /**
     * Преобразует сущность User в объект UserDto.
     */
    public static UserDto toDto(User u) {
        UserDto dto = new UserDto();
        dto.setId(u.getId());
        dto.setName(u.getName());
        dto.setEmail(u.getEmail());
        dto.setAge(u.getAge());
        dto.setCreatedAt(u.getCreatedAt());
        return dto;
    }

    /**
     * Преобразует DTO UserDto в новую сущность User.
     */
    public static User toEntity(UserDto dto) {
        User u = new User();
        u.setName(dto.getName());
        u.setEmail(dto.getEmail());
        u.setAge(dto.getAge());
        return u;
    }
}
