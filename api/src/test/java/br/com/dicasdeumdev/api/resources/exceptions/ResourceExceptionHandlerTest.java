package br.com.dicasdeumdev.api.resources.exceptions;

import br.com.dicasdeumdev.api.domain.User;
import br.com.dicasdeumdev.api.domain.dto.UserDTO;
import br.com.dicasdeumdev.api.resources.UserResource;
import br.com.dicasdeumdev.api.services.exceptions.DataIntegrityViolationException;
import br.com.dicasdeumdev.api.services.exceptions.ObjectNotFoundException;
import br.com.dicasdeumdev.api.services.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResourceExceptionHandlerTest {


    public static final String RESOURCE_NOT_FOUND = "Resource not found ";
    public static final String EMAIL_JÁ_CADASTRADO = "Email já cadastrado";
    @InjectMocks
    private ResourceExceptionHandler resourceExceptionHandler;
    private User user;
    private UserDTO userDTO;
    public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks ( this );
    }

    @Test
    void whenObjectNotFoundExceptionThenReturnAResponseEntity() {
        ResponseEntity<StandardError> response = resourceExceptionHandler.objectNotFound
                ( new ObjectNotFoundException ( OBJETO_NAO_ENCONTRADO ), new MockHttpServletRequest () );

        assertNotNull ( response );
        assertNotNull ( response.getBody () );
        assertNotNull ( OBJETO_NAO_ENCONTRADO,response.getBody ().getError () );

        assertEquals ( HttpStatus.NOT_FOUND,response.getStatusCode () );
        assertEquals ( ResponseEntity.class,response.getClass () );
        assertEquals ( StandardError.class,response.getBody ().getClass () );
        assertEquals ( 404,response.getBody ().getStatus () );

        assertNotEquals ( LocalDateTime.now (),response.getBody ().getTimestamp () );
    }

    @Test
    void whenDataIntegratyViolationExceptionThenReturnAResponseEntity() {
        ResponseEntity<StandardError> response = resourceExceptionHandler.dataIntegratyViolationException (
                ( new DataIntegrityViolationException ( EMAIL_JÁ_CADASTRADO )), new MockHttpServletRequest () );


        assertNotNull ( response );
        assertNotNull ( response.getBody () );
        assertNotNull ( EMAIL_JÁ_CADASTRADO,response.getBody ().getError () );

        assertEquals ( HttpStatus.BAD_REQUEST,response.getStatusCode () );
        assertEquals ( ResponseEntity.class,response.getClass () );
        assertEquals ( StandardError.class,response.getBody ().getClass () );
        assertEquals ( 400,response.getBody ().getStatus () );
    }


}