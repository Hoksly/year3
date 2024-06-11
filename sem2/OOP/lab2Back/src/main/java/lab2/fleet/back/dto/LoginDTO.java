package lab2.fleet.back.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private String login;
    private String email;
    private String password;
}
