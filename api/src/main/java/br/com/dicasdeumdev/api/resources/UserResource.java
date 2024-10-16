package br.com.dicasdeumdev.api.resources;

import br.com.dicasdeumdev.api.config.ModelMapperConfig;
import br.com.dicasdeumdev.api.domain.User;
import br.com.dicasdeumdev.api.domain.dto.UserDTO;
import br.com.dicasdeumdev.api.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/user")
public class UserResource {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UserService service;
    
    private static final String ID ="/{id}";

    @GetMapping(value = ID)
    public ResponseEntity<UserDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok ().body ( mapper.map ( service.findById ( id ), UserDTO.class ) );
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        var users = service.findAll ();
        var userDTOs = users.stream ()
                .map ( user -> mapper.map ( user, UserDTO.class ) )
                .collect ( Collectors.toList () );
        return ResponseEntity.ok ( userDTOs );
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO obj) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest ()
                .path ( ID ).buildAndExpand ( service.create ( obj ).getId () ).toUri ();
        return ResponseEntity.created ( uri ).build ();
    }

    @PutMapping(value = ID)
    public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody  UserDTO obj){
        obj.setId (id);
        return ResponseEntity.ok ().body ( mapper.map ( service.update ( obj),UserDTO.class ) );
    }

    @DeleteMapping(value = ID)
    public ResponseEntity<UserDTO>delete(@PathVariable Integer id){
        service.delete ( id );
        return ResponseEntity.noContent ().build ();
    }
}
