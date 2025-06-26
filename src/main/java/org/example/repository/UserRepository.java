package org.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.model.User;

/**
 * Репозиторий для работы с сущностью User.
 * <p>Наследует стандартный набор CRUD-операций из JpaRepository.</p>
 */
public interface UserRepository extends JpaRepository<User, Long> {

}
