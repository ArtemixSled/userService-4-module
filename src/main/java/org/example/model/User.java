package org.example.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Сущность пользователя.
 * <p>Отображается на таблицу {@code users} в базе данных.</p>
 */
@Data
@Entity
@Table(name = "users")
public class User {

    /**
     * Уникальный идентификатор пользователя.
     * Генерируется базой данных (AUTO_INCREMENT).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя пользователя.
     * Не может быть null.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Email пользователя.
     * Должен быть уникальным и не может быть null.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Возраст пользователя.
     * Может быть null.
     */
    @Column
    private Integer age;

    /**
     * Дата и время создания записи.
     * Заполняется автоматически перед сохранением и не обновляется позже.
     */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * Колбэк перед сохранением сущности.
     * Устанавливает {@code createdAt} в текущее время.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
