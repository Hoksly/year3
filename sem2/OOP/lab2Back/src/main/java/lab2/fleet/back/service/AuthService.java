package lab2.fleet.back.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import lab2.fleet.back.dto.AuthDTO;
import lab2.fleet.back.dto.LoginDTO;
import lab2.fleet.back.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import lab2.fleet.back.entity.Password;
import lab2.fleet.back.entity.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepo userRepository;

    public Optional<AuthDTO> auth(LoginDTO loginDTO){
        Optional<User> user = userRepository.findUserByEmail(loginDTO.getEmail());
        if(user.isEmpty()){
            return Optional.empty();
        }
        User confirmedUser = user.get();
        Password pwd = Password.builder()
                .hash(confirmedUser.getPassword())
                .salt(confirmedUser.getSalt())
                .build();
        String hashedPwd = BCrypt.hashpw(loginDTO.getPassword(), pwd.getSalt());
        if(!hashedPwd.equals(pwd.getHash())){
            return Optional.empty();
        }
        Algorithm algorithm = Algorithm.HMAC256("baeldung");
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("Baeldung")
                .build();
        String jwt = JWT.create()
                .withIssuer("Baeldung")
                .withClaim("id", confirmedUser.getId())
                .withClaim("login", confirmedUser.getLogin())
                .withClaim("email", confirmedUser.getEmail())
                .withClaim("role", confirmedUser.getRole())
                .sign(algorithm);
        return Optional.of(new AuthDTO(jwt));
    }
}
