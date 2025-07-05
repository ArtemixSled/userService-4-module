package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.NotificationMessageDto;
import org.example.dto.UserDto;
import org.example.mapper.UserMapper;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Сервис для управления пользователями.
 * <p>Реализует интерфейс UserService, работает через Spring Data JPA репозиторий.</p>
 */
@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository repo;
    private final KafkaTemplate<String, NotificationMessageDto> kafka;

    /**
     * Конструктор для внедрения UserRepository.
     *
     * @param repo репозиторий пользователей
     */
    public UserServiceImpl(UserRepository repo, KafkaTemplate<String, NotificationMessageDto> kafka) {
        this.repo = repo;
        this.kafka = kafka;
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
        User saved = repo.save(UserMapper.toEntity(dto));
        UserDto result = UserMapper.toDto(saved);

        log.info("Создан пользователь {}, шлём событие CREATE в Kafka…", result.getEmail());
        kafka.send("user-events", new NotificationMessageDto("CREATE", result.getEmail()));

        return result;
    }

    /**
     * Обновить данные существующего пользователя.
     */
    @Override
    public UserDto update(Long id, UserDto dto) {
        User existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setAge(dto.getAge());
        User updated = repo.save(existing);
        return UserMapper.toDto(updated);
    }

    /**
     * Удалить пользователя по идентификатору.
     */
    @Override
    public void delete(Long id) {
        User existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        repo.deleteById(id);
        kafka.send("user-events", new NotificationMessageDto("DELETE", existing.getEmail()));
    }
}
