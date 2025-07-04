package org.example.service;

import org.example.dto.UserDto;
import java.util.List;

/**
 * Интерфейс сервиса для управления пользователями.
 */
public interface UserService {

    List<UserDto> findAll();

    UserDto findById(Long id);

    UserDto create(UserDto dto);

    UserDto update(Long id, UserDto dto);

    void delete(Long id);
}
