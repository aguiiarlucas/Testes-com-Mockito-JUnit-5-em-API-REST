package br.com.dicasdeumdev.api.services.impl;

import br.com.dicasdeumdev.api.domain.User;
import br.com.dicasdeumdev.api.domain.dto.UserDTO;
import br.com.dicasdeumdev.api.repositories.UserRepository;
import br.com.dicasdeumdev.api.services.UserService;
import br.com.dicasdeumdev.api.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(Integer id) {
        Optional<User> user = userRepository.findById ( id );
        return user.orElseThrow (()->new ObjectNotFoundException ( "Objeto não encontrado" ));

    }
    public List<User> findAll(){
        return userRepository.findAll ();
    }

    @Override
    public User create(UserDTO obj) {
        return userRepository.save ( mapper.map ( obj,User.class ) );
    }
}
