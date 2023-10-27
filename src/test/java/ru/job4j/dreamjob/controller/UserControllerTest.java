package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {
    private UserService userService;
    private UserController userController;

    @BeforeEach
    public void initServices() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    public void whenRequestGetRegistrationPage() {
        assertThat(userController.getRegistrationPage()).isEqualTo("users/register");
    }

    @Test
    public void whenPostRegisterUserThenRedirectToVacanciesPage() {
        var user = new User(1, "email", "name", "password");
        var userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        when(userService.save(userArgumentCaptor.capture())).thenReturn(Optional.of(user));

        var model = new ConcurrentModel();
        var view = userController.register(model, user);
        var actualUser = userArgumentCaptor.getValue();

        assertThat(view).isEqualTo("redirect:/vacancies");
        assertThat(actualUser).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    public void whenSomeExceptionRegisterUserThenGetErrorPageWithMessage() {
        var model = new ConcurrentModel();
        var view = userController.register(model, new User());
        var actualExceptionMessage = model.getAttribute("message");
        assertThat(view).isEqualTo("errors/404");
        assertThat(actualExceptionMessage).isEqualTo("Пользователь с такой почтой уже существует");
    }

    @Test
    public void whenRequestGetLoginPage() {
        assertThat(userController.getLoginPage()).isEqualTo("users/login");
    }

    @Test
    public void whenPostLoginUserThenRedirectToVacanciesPage() {
        var user = new User(1, "email", "name", "password");
        when(userService.findByEmailAndPassword(user.getEmail(), user.getPassword())).thenReturn(Optional.of(user));
        var model = new ConcurrentModel();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        var view = userController.loginUser(user, model, request);
        assertThat(view).isEqualTo("redirect:/vacancies");
    }

    @Test
    public void whenSomeExceptionLoginUserThenGetErrorPageWithMessage() {
        var user = new User(1, "email", "name", "password");
        var model = new ConcurrentModel();
        HttpServletRequest request = mock(HttpServletRequest.class);
        var view = userController.loginUser(user, model, request);
        var actualExceptionMessage = model.getAttribute("error");
        assertThat(view).isEqualTo("users/login");
        assertThat(actualExceptionMessage).isEqualTo("Почта или пароль введены неверно");
    }

    @Test
    public void whenRequestLogoutPage() {
        HttpSession session = mock(HttpSession.class);
        assertThat(userController.logout(session)).isEqualTo("redirect:/users/login");
    }
}