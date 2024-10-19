package br.com.dicasdeumdev.api.resources;

import br.com.dicasdeumdev.api.domain.User;
import br.com.dicasdeumdev.api.domain.dto.UserDTO;
import br.com.dicasdeumdev.api.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserResourceTest {

    public static final Integer ID = 1;
    public static final String NAME = "Lucas";
    public static final String EMAIL = "lucascostaaguiar@hotmail.com";
    public static final String PASSWORD = "1234";
    public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";
    public static final int INDEX = 0;
    public static final String EMAIL_JA_CADASTRADO = "E-mail já está cadastrado no sistema.";
    @InjectMocks
    private UserResource userResource;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private UserServiceImpl userService;
    private User user;
    private UserDTO userDTO;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks ( this );
        startUser ();
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private void startUser() {
        user = new User ( ID, NAME, EMAIL, PASSWORD );
        userDTO = new UserDTO ( ID, NAME, EMAIL, PASSWORD );
    }
}