package ru.netology.CloudStorage.Security;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootTest
@ContextConfiguration(classes = {SecurityConfig.class})
public class SecurityConfigTest {

    @Autowired
    private SecurityFilterChain securityFilterChain;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void securityFilterChain_ShouldBeConfigured() {
        assertThat(securityFilterChain).isNotNull();
    }

    @Test
    public void authenticationManager_ShouldBeConfigured() {
        assertThat(authenticationManager).isNotNull();
    }

    @Test
    public void passwordEncoder_ShouldBeBCrypt() {
        assertThat(passwordEncoder).isInstanceOf(BCryptPasswordEncoder.class);
    }

    @Test
    public void userDetailsService_ShouldNotBeNull() {
        assertThat(userDetailsService).isNotNull();
    }
}