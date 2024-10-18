package br.com.dicasdeumdev.api.services.impl;

import br.com.dicasdeumdev.api.domain.User;
import br.com.dicasdeumdev.api.domain.dto.UserDTO;
import br.com.dicasdeumdev.api.repositories.UserRepository;
import br.com.dicasdeumdev.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {

    public static final Integer ID = 1;
    public static final String NAME = "Lucas";
    public static final String EMAIL = "lucascostaaguiar@hotmail.com";
    public static final String PASSWORD = "1234";
    public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";
    public static final int INDEX = 0;
    @InjectMocks
    private UserServiceImpl service;
    @Mock
    private UserRepository repository;
    @Mock
    private ModelMapper mapper;
    private User user;
    private UserDTO userDTO;
    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks ( this );
        startUser ();
    }

    @Test
    void whenFindByIdThenReturnAnUserInstance() { //Quando fizer a busca pelo id , retorne uma isntancia de usuario;
        when ( repository.findById ( anyInt () ) ).thenReturn ( optionalUser );
        User response = service.findById ( ID );

        assertNotNull ( response );
        assertEquals ( User.class, response.getClass () );
        assertEquals ( ID, response.getId () );
        assertEquals ( NAME, response.getName () );
        assertEquals ( EMAIL, response.getEmail () );
        assertEquals ( PASSWORD, response.getPassword () );

    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException() {//Quando for buscar pelo id,retorne um objeto não encontrado
        when ( repository.findById ( anyInt () ) ).thenThrow ( new ObjectNotFoundException ( OBJETO_NAO_ENCONTRADO ) );

        try {
            service.findById ( ID );
        } catch (Exception e) {
            assertEquals ( ObjectNotFoundException.class, e.getClass () );
            assertEquals ( OBJETO_NAO_ENCONTRADO, e.getMessage () );
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfUsers() { //Quando buscar todos, me retorne uma lista de usuarios {
        when ( repository.findAll () ).thenReturn (List.of(user));
        List<User>response = service.findAll ();
        assertNotNull ( response );
        assertEquals ( 1,response.size () );
        assertEquals ( User.class,response.get(INDEX).getClass () );

        assertEquals ( ID,response.get ( INDEX ).getId () );
        assertEquals ( NAME,response.get (INDEX).getName () );
        assertEquals ( EMAIL,response.get (INDEX).getEmail () );
        assertEquals ( PASSWORD,response.get (INDEX).getPassword () );
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

    @Test
    void findByEmail() {
    }

    private void startUser() {
        user = new User ( ID, NAME, EMAIL, PASSWORD );
        userDTO = new UserDTO ( ID, NAME, EMAIL, PASSWORD );
        optionalUser = Optional.of ( new User ( ID, NAME, EMAIL, PASSWORD ) );
    }
}