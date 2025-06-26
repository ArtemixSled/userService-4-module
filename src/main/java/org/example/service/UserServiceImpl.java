package org.example.service;

import org.example.dto.UserDto;
import org.example.mapper.UserMapper;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static java.util.stream.Collectors.toList;

/**
 * Сервис для управления пользователями.
 * <p>Реализует интерфейс UserService, работает через Spring Data JPA репозиторий.</p>
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository repo;

    /**
     * Конструктор для внедрения UserRepository.
     *
     * @param repo репозиторий пользователей
     */
    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    /**
     * Получить всех пользователей.
     */
    @Override
    public List<UserDto> findAll() {
        return repo.findAll().stream()
                .map(UserMapper::toDto)
                .collect(toList());
    }

    /**
     * Найти пользователя по идентификатору.
     */
    @Override
    public UserDto findById(Long id) {
        return repo.findById(id)
                .map(UserMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    /**
     * Создать нового пользователя.
     */
    @Override
    public UserDto create(UserDto dto) {
        var saved = repo.save(UserMapper.toEntity(dto));
        return UserMapper.toDto(saved);
    }

    /**
     * Обновить данные существующего пользователя.
     */
    @Override
    public UserDto update(Long id, UserDto dto) {
        var existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setAge(dto.getAge());
        var updated = repo.save(existing);
        return UserMapper.toDto(updated);
    }

    /**
     * Удалить пользователя по идентификатору.
     */
    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
