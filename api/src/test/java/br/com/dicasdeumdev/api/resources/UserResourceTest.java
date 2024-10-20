package br.com.dicasdeumdev.api.resources;

import br.com.dicasdeumdev.api.domain.User;
import br.com.dicasdeumdev.api.domain.dto.UserDTO;
import br.com.dicasdeumdev.api.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
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
    public static final String LOCATION = "Location";
    @InjectMocks
    private UserResource userResource;
    @Mock
    private ModelMapper mapper;
    @Mock
    private UserServiceImpl userService;
    private User user = new User ();
    private UserDTO userDTO = new UserDTO ();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks ( this );
        startUser ();
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        when ( userService.findById ( anyInt () ) ).thenReturn ( user );
        when ( mapper.map ( any (), any () ) ).thenReturn ( userDTO );
        ResponseEntity<UserDTO> response = userResource.findById ( ID );

        assertNotNull ( response );
        assertNotNull ( response.getBody () );
        assertEquals ( ResponseEntity.class, response.getClass () );
        assertEquals ( UserDTO.class, response.getBody ().getClass () );


        assertEquals ( ID, response.getBody ().getId () );
        assertEquals ( NAME, response.getBody ().getName () );
        assertEquals ( EMAIL, response.getBody ().getEmail () );
        assertEquals ( PASSWORD, response.getBody ().getPassword () );
    }

    @Test
    void whenFindAllThenReturnListOfUserDTO() {
        when ( userService.findAll () ).thenReturn ( List.of ( user ) );
        when ( mapper.map ( any (), any () ) ).thenReturn ( userDTO );

        ResponseEntity<List<UserDTO>> response = userResource.findAll ();

        assertNotNull ( response );
        assertNotNull ( response.getBody () );
        assertEquals ( HttpStatus.OK, response.getStatusCode () );

        assertEquals ( ResponseEntity.class, response.getClass () );
        assertEquals ( ArrayList.class, response.getBody ().getClass () );
        assertEquals ( UserDTO.class, response.getBody ().get ( INDEX ).getClass () );

        assertEquals ( ID, response.getBody ().get ( INDEX ).getId () );
        assertEquals ( NAME, response.getBody ().get ( INDEX ).getName () );
        assertEquals ( EMAIL, response.getBody ().get ( INDEX ).getEmail () );
        assertEquals ( PASSWORD, response.getBody ().get ( INDEX ).getPassword () );


    }

    @Test
    void whenCreateThenReturnCreated() {
        when ( userService.create ( any () ) ).thenReturn ( user );

        ResponseEntity<UserDTO> response = userResource.create ( userDTO );

        assertEquals ( ResponseEntity.class, response.getClass () );
        assertEquals ( HttpStatus.CREATED, response.getStatusCode () );
        assertNotNull ( response.getHeaders ().get ( LOCATION ) );
    }

    @Test
    void whenUpdateThenReturnUpdate() {
        when ( userService.update ( userDTO ) ).thenReturn ( user );
        when ( mapper.map ( any (), any () ) ).thenReturn ( userDTO );

        ResponseEntity<UserDTO> response = userResource.update ( ID, userDTO );

        assertNotNull ( response );
        assertNotNull ( response.getBody () );

        assertEquals ( ResponseEntity.class, response.getClass () );
        assertEquals ( HttpStatus.OK, response.getStatusCode () );
        assertEquals ( UserDTO.class, response.getBody ().getClass () );

        assertEquals ( ID, response.getBody ().getId () );
        assertEquals ( NAME, response.getBody ().getName () );
        assertEquals ( EMAIL, response.getBody ().getEmail () );
    }

    @Test
    void whenDeleteThenReturnSuccess() {
        doNothing().when(userService).delete (anyInt());
        ResponseEntity<UserDTO> response = userResource.delete ( ID );

        assertNotNull ( response );

        verify(userService, times(1)).delete (anyInt());

        assertEquals ( ResponseEntity.class, response.getClass () );
        assertEquals ( HttpStatus.NO_CONTENT,response.getStatusCode () );


    }

    private void startUser() {
        user = new User ( ID, NAME, EMAIL, PASSWORD );
        userDTO = new UserDTO ( ID, NAME, EMAIL, PASSWORD );
    }
}