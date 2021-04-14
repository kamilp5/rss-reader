package rss.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rss.exception.UserAlreadyExistsException;
import rss.repository.UserRepository;
import rss.user.User;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest
class UserServiceTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityContext securityContext;
        @Mock
    private Authentication authentication;
    @Spy
    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Test
    void createUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("email@email.com");
        user.setPassword("123");

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.getUserByEmail(user.getEmail())).thenReturn(Optional.empty());

        User result = userService.createUser(user);

        assertTrue(new BCryptPasswordEncoder().matches("123", result.getPassword()));
        verify(userRepository, times(1)).save(any());

    }

    @Test()
    void createUser_EmailNotAvailable() {
        User user = new User();
        user.setEmail("email@email.com");
        user.setPassword("123");

        when(userRepository.getUserByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Exception exception = assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(user));
        assertEquals("User with email: " + user.getEmail() + " already exists", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void getUserByEmail() {
        User user = new User();
        user.setEmail("email@email.com");
        user.setPassword("123");

        when(userRepository.getUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        User result = userService.getUserByEmail(user.getEmail());

        assertEquals(user, result);

    }

    @Test
    void saveUser() {
        User user = new User();
        user.setEmail("email@email.com");
        user.setPassword("123");

        when(userRepository.save(any(User.class))).thenReturn(any());

        userService.saveUser(user);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void getLoggedUser() {
        User user = new User();
        user.setEmail("email@email.com");
        user.setPassword("123");
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(new UsernamePasswordAuthenticationToken("email@email.com", "PASSWORD"));
        doReturn(user).when(userService).getUserByEmail(user.getEmail());

        User result = userService.getLoggedUser();

        verify(userService, times(1)).getUserByEmail(any());
        assertNotNull(result);
    }

    @Test
    void getLoggedUser_AnonymousUser() {
        User user = new User();
        user.setEmail("email@email.com");
        user.setPassword("123");
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(new AnonymousAuthenticationToken("GUEST","USERNAME", AuthorityUtils
                .createAuthorityList("ROLE_ONE", "ROLE_TWO")));

        User result = userService.getLoggedUser();
        verify(userService, never()).getUserByEmail(any());
        assertNull(result);
    }
}