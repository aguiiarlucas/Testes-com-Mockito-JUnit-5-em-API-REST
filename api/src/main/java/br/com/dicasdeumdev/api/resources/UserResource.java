package br.com.dicasdeumdev.api.resources;

import br.com.dicasdeumdev.api.domain.User;
import br.com.dicasdeumdev.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable Integer id) {
        // Dummy implementation for demonstration purposes
        return ResponseEntity.ok().body (userService.findById ( id ) );
    }
}
