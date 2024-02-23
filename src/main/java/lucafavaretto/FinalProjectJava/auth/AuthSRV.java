package lucafavaretto.FinalProjectJava.auth;

import lucafavaretto.FinalProjectJava.auth.authDTO.UserLoginDTO;
import lucafavaretto.FinalProjectJava.auth.security.JWTTools;
import lucafavaretto.FinalProjectJava.user.User;
import lucafavaretto.FinalProjectJava.user.UserSRV;
import lucafavaretto.FinalProjectJava.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthSRV {
    @Autowired
    private UserSRV userSRV;

    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;

    public String authenticateUserAndGenerateToken(UserLoginDTO payload) {

        User user = userSRV.findByEmail(payload.email());
        if (bcrypt.matches(payload.password(), user.getPassword())) {

            return jwtTools.createToken(user);
        } else {

            throw new UnauthorizedException("Wrong credentials!");
        }

    }
}
