package br.com.dicasdeumdev.api.services.impl;

import br.com.dicasdeumdev.api.domain.User;
import br.com.dicasdeumdev.api.domain.dto.UserDTO;
import br.com.dicasdeumdev.api.repositories.UserRepository;
import br.com.dicasdeumdev.api.services.exceptions.DataIntegrityViolationException;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    public static final Integer ID = 1;
    public static final String NAME = "Lucas";
    public static final String EMAIL = "lucascostaaguiar@hotmail.com";
    public static final String PASSWORD = "1234";
    public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";
    public static final int INDEX = 0;
    public static final String EMAIL_JA_CADASTRADO = "E-mail já está cadastrado no sistema.";
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
        } catch (Exception ex) {
            assertEquals ( ObjectNotFoundException.class, ex.getClass () );
            assertEquals ( OBJETO_NAO_ENCONTRADO, ex.getMessage () );
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfUsers() { //Quando buscar todos, me retorne uma lista de usuarios {
        when ( repository.findAll () ).thenReturn ( List.of ( user ) );
        List<User> response = service.findAll ();
        assertNotNull ( response );
        assertEquals ( 1, response.size () );
        assertEquals ( User.class, response.get ( INDEX ).getClass () );

        assertEquals ( ID, response.get ( INDEX ).getId () );
        assertEquals ( NAME, response.get ( INDEX ).getName () );
        assertEquals ( EMAIL, response.get ( INDEX ).getEmail () );
        assertEquals ( PASSWORD, response.get ( INDEX ).getPassword () );
    }

    @Test
    void whenCreateThenReturnSuccess() {// Quando criar , me retorne com sucesso
        when ( repository.save ( any () ) ).thenReturn ( user );
        User response = service.create ( userDTO );

        assertNotNull ( response );
        assertEquals ( User.class, response.getClass () );

        assertEquals ( ID, response.getId () );
        assertEquals ( NAME, response.getName () );
        assertEquals ( EMAIL, response.getEmail () );
        assertEquals ( PASSWORD, response.getPassword () );
    }

    @Test
    void whenCreateThenReturnAnDataIntegrityViolationException() {// Quando criar , me retorne
        // uma excessão de violacao de interegraçao de dados
        when ( repository.findByEmail ( anyString () ) ).thenReturn ( optionalUser );

        try {
            optionalUser.get ().setId ( 2 );
            service.create ( userDTO );
        } catch (DataIntegrityViolationException ex) {
            assertEquals ( DataIntegrityViolationException.class, ex.getClass () );
            assertEquals ( EMAIL_JA_CADASTRADO, ex.getMessage () );
        }

    }

    @Test
    void whenUpdateThenReturnSuccess() {// Quando fazer um update , me retorne com sucesso
        when ( repository.save ( any () ) ).thenReturn ( user );
        User response = service.update ( userDTO );

        assertNotNull ( response );
        assertEquals ( User.class, response.getClass () );

        assertEquals ( ID, response.getId () );
        assertEquals ( NAME, response.getName () );
        assertEquals ( EMAIL, response.getEmail () );
        assertEquals ( PASSWORD, response.getPassword () );
    }

    @Test
    void whenUpdateThenReturnAnDataIntegrityViolationException() {// Quando criar , me retorne
        // uma excessão de violacao de interegraçao de dados
        when ( repository.findByEmail ( anyString () ) ).thenReturn ( optionalUser );

        try {
            optionalUser.get ().setId ( 2 );
            service.create ( userDTO );
        } catch (DataIntegrityViolationException ex) {
            assertEquals ( DataIntegrityViolationException.class, ex.getClass () );
            assertEquals ( EMAIL_JA_CADASTRADO, ex.getMessage () );
        }

    }

    @Test
    void deleteWithSuccess() {
        // Simula o comportamento de repository.findById retornando optionalUser quando chamado
        when(repository.findById(anyInt())).thenReturn(optionalUser);

        // Não há necessidade de usar doNothing() aqui, pois deleteById é um método void
        doNothing().when(repository).deleteById(anyInt());

        // Executa o método delete da service
        service.delete(ID);

        // Verifica se deleteById foi chamado exatamente uma vez
        verify(repository, times(1)).deleteById(anyInt());
    }
    @Test
    void whenDeleteWithObjectNotFoundException() {
        when ( repository.findById ( anyInt () ) ).thenThrow ( new ObjectNotFoundException ( OBJETO_NAO_ENCONTRADO ) );
        try {
            service.delete ( ID );
        }catch (Exception ex){
            assertEquals ( ObjectNotFoundException.class,ex.getClass () );
            assertEquals ( OBJETO_NAO_ENCONTRADO,ex.getMessage () );
        }
    }

    private void startUser() {
        user = new User ( ID, NAME, EMAIL, PASSWORD );
        userDTO = new UserDTO ( ID, NAME, EMAIL, PASSWORD );
        optionalUser = Optional.of ( new User ( ID, NAME, EMAIL, PASSWORD ) );
    }
}