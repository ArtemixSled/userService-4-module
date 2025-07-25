package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.dto.UserDto;
import org.example.dto.UserModelAssembler;
import org.example.service.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * REST-контроллер для управления пользователями.
 * <p>Обрабатывает HTTP-запросы по пути /api/users и делегирует операции UserService.</p>
 */
@Tag(name = "Пользователи", description = "Операции над пользователями")
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;
    private final UserModelAssembler assembler;

    public UserController(UserService service, UserModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Operation(summary = "Получить всех пользователей")
    @GetMapping
    public CollectionModel<EntityModel<UserDto>> getAll() {
        List<EntityModel<UserDto>> users = service.findAll().stream()
                .map(assembler::toModel)
                .toList();

        return CollectionModel.of(users, linkTo(methodOn(UserController.class).getAll()).withSelfRel()
        );
    }

    @Operation(summary = "Получить пользователя по ID")
    @GetMapping("/{id}")
    public EntityModel<UserDto> getById(@PathVariable Long id) {
        UserDto dto = service.findById(id);
        return assembler.toModel(dto);
    }

    @Operation(summary = "Создать нового пользователя")
    @PostMapping
    public ResponseEntity<EntityModel<UserDto>> create(@Valid @RequestBody UserDto dto) {
        UserDto created = service.create(dto);
        EntityModel<UserDto> model = assembler.toModel(created);
        return ResponseEntity
                .created(model.getRequiredLink("self").toUri())
                .body(model);
    }

    @Operation(summary = "Обновить пользователя по ID")
    @PutMapping("/{id}")
    public EntityModel<UserDto> update(@PathVariable Long id, @Valid @RequestBody UserDto dto) {
        UserDto updated = service.update(id, dto);
        return assembler.toModel(updated);
    }

    @Operation(summary = "Удалить пользователя по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
