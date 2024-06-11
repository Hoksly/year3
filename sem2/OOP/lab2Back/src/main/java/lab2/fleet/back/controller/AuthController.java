package lab2.fleet.back.controller;

import lab2.fleet.back.dto.AuthDTO;
import lab2.fleet.back.dto.LoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lab2.fleet.back.service.AuthService;
import lab2.fleet.back.service.JsonParser;

import java.util.Optional;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class AuthController {
    private final AuthService authService;
    @PostMapping
    private String auth(@RequestBody LoginDTO loginDto) throws Exception {
        Optional<AuthDTO> response = authService.auth(loginDto);
        if(response.isEmpty()){
            return "[]";
        }
        return JsonParser.toJsonObject(response.get());
    }

}
