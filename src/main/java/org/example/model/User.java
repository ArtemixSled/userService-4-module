package org.example.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Сущность пользователя.
 * <p>Отображается на таблицу {@code users} в базе данных.</p>
 */
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
