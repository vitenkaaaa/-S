package ru.netology.CloudStorage.Сontroller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.CloudStorage.Login.LoginRequest;
import ru.netology.CloudStorage.Logout.LogoutRequest;
import ru.netology.CloudStorage.Login.LoginResponse;
import ru.netology.CloudStorage.Service.AuthService.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            String sessionId = authService.login(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(new LoginResponse(sessionId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    public void login(String username, String password) {
        System.out.println("Вызван метод login");
        System.out.println("Пользователь пытается войти: " + username);
        if ("admin".equals(username) && "password".equals(password)) {
            System.out.println("Пользователь " + username + " успешно аутентифицирован");
        } else {
            System.out.println("Ошибка аутентификации для пользователя: " + username);
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequest request) {
        try {
            authService.logout(request.getSessionId());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}



