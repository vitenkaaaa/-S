package ru.netology.CloudStorage.Security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ru.netology.CloudStorage.Security.CustomUserDetailsService;
import ru.netology.CloudStorage.Model.User;
import ru.netology.CloudStorage.Repository.UserRepository;

public class CustomUserDetailsServiceTest {

    private UserRepository userRepository;
    private CustomUserDetailsService userDetailsService;

    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
        userDetailsService = new CustomUserDetailsService();
        try {
            java.lang.reflect.Field repoField = CustomUserDetailsService.class.getDeclaredField("userRepository");
            repoField.setAccessible(true);
            repoField.set(userDetailsService, userRepository);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void loadUserByUsername_UserExists_ReturnsUserDetails() {
        String username = "testuser";
        String password = "password";

        User user = new User(username, password);
        when(userRepository.findByUsername(username)).thenReturn(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
    }

    @Test
    public void loadUserByUsername_UserNotFound_ThrowsException() {
        String username = "nonexistent";

        when(userRepository.findByUsername(username)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(username);
        });
    }
}