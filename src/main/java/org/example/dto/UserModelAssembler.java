package org.example.dto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.example.controller.UserController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

/**
 * Ассемблер для обёртки {@link UserDto} в HATEOAS-объект {@link EntityModel} с наборами ссылок.
 * <p>
 * Для каждого пользователя добавляются:
 * <ul>
 *   <li>Ссылка <em>self</em> на получение данного пользователя по ID;</li>
 *   <li>Ссылка <em>users</em> на получение списка всех пользователей.</li>
 * </ul>
 * </p>
 */
@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserDto, EntityModel<UserDto>> {

    @Override
    public EntityModel<UserDto> toModel(UserDto user) {
        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).getById(user.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).getAll()).withRel("users")
        );
    }
}
