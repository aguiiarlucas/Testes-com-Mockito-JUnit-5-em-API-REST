package br.com.dicasdeumdev.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.*;


@Getter @Setter
@AllArgsConstructor
public class UserDTO {


    private Integer id;
    private String name;
    private String email;
    private String password;
}
