package br.com.dicasdeumdev.api.config;

import br.com.dicasdeumdev.api.domain.User;
import br.com.dicasdeumdev.api.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void startDB() {
        // Lista de usuários a serem inseridos
        List<User> users = Arrays.asList(
                new User(null, "Lucas", "lucascostaaguiar@hotmail.com", "123"),
                new User(null, "Adevalter", "adevalter@hotmail.com", "1234")
        );

        for (User user : users) {
            if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
                userRepository.save(user);
            } else {
                System.out.println("Usuário com e-mail " + user.getEmail() + " já existe.");
            }
        }
    }
}
