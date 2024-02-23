package lucafavaretto.FinalProjectJava.auth;

import lucafavaretto.FinalProjectJava.user.UserSRV;
import lucafavaretto.FinalProjectJava.auth.authDTO.LoginRegisterDTO;
import lucafavaretto.FinalProjectJava.auth.authDTO.UserLoginDTO;
import lucafavaretto.FinalProjectJava.user.User;
import lucafavaretto.FinalProjectJava.user.UserDTO;
import lucafavaretto.FinalProjectJava.user.UserSRV;
import lucafavaretto.FinalProjectJava.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthCTRL {
    @Autowired
    private AuthSRV authSRV;
    @Autowired
    private UserSRV employeeSRV;

    @PostMapping("/login")
    public LoginRegisterDTO login(@RequestBody UserLoginDTO payload) {
        return new LoginRegisterDTO(authSRV.authenticateUserAndGenerateToken(payload));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED) // Status Code 201
    public User saveUser(@RequestBody @Validated UserDTO employeeDTO, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        // Se non ci sono errori salvo lo user
        return this.employeeSRV.save(employeeDTO);
    }

}
