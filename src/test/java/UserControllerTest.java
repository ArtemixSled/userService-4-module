import org.example.UserServiceApplication;
import org.example.controller.UserController;
import org.example.dto.UserDto;
import org.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = UserServiceApplication.class)
class UserControllerTest {

    @Autowired MockMvc mvc;
    @MockBean UserService service;

    /**
     * Должен вернуть список пользователей и статус 200 при GET /api/users.
     */
    @Test
    void shouldReturnAllUsers() throws Exception {
        var dto = new UserDto(1L, "Alice", "a@ex.com", 30, LocalDateTime.now());
        given(service.findAll()).willReturn(List.of(dto));

        mvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    /**
     * Должен создать пользователя, вернуть статус 201, заголовок Location и JSON с id.
     */
    @Test
    void shouldCreateUser() throws Exception {
        var toCreate = new UserDto(null, "Bob", "b@ex.com", 25, null);
        var created  = new UserDto(2L, "Bob", "b@ex.com", 25, LocalDateTime.now());
        given(service.create(any(UserDto.class))).willReturn(created);

        mvc.perform(post("/api/users")
                        .contentType("application/json")
                        .content("""
                            {"name":"Bob","email":"b@ex.com","age":25}
                        """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/users/2"))
                .andExpect(jsonPath("$.id").value(2));
    }

    /**
     * Должен вернуть одного пользователя и статус 200 при GET /api/users/{id}.
     */
    @Test
    void shouldReturnUserById() throws Exception {
        var dto = new UserDto(3L, "Carol", "c@ex.com", 28, LocalDateTime.now());
        given(service.findById(3L)).willReturn(dto);

        mvc.perform(get("/api/users/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("Carol"))
                .andExpect(jsonPath("$.email").value("c@ex.com"));
    }

    /**
     * Должен обновить пользователя, вернуть статус 200 и обновлённый JSON.
     */
    @Test
    void shouldUpdateUser() throws Exception {
        var updated = new UserDto(4L, "Dave", "d@ex.com", 35, LocalDateTime.now());
        given(service.update(eq(4L), any(UserDto.class))).willReturn(updated);

        mvc.perform(put("/api/users/4")
                        .contentType("application/json")
                        .content("""
                            {"name":"Dave","email":"d@ex.com","age":35}
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.name").value("Dave"))
                .andExpect(jsonPath("$.age").value(35));
    }

    /**
     * Должен удалить пользователя и вернуть статус 204 при DELETE /api/users/{id}.
     */
    @Test
    void shouldDeleteUser() throws Exception {
        doNothing().when(service).delete(5L);

        mvc.perform(delete("/api/users/5"))
                .andExpect(status().isNoContent());
    }
}
