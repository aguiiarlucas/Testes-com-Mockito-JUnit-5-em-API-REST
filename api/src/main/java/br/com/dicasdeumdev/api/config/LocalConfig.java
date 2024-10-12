package br.com.dicasdeumdev.api.config;

import br.com.dicasdeumdev.api.domain.User;
import br.com.dicasdeumdev.api.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile ("local")
public class LocalConfig {

    @Autowired
    private UserRepository userRepository ;
    @PostConstruct
    public void startDB(){
        User u1 = new User (null,"Lucas","lucascsotaaguiar@hotmail.com","123");
        User u2 = new User (null,"Adevalter","adevalter@hotmail.com","1234");

        userRepository.saveAll ( List.of (u1,u2) );
    }
}
