package lucafavaretto.FinalProjectJava.user;

import lucafavaretto.FinalProjectJava.exceptions.BadRequestException;
import lucafavaretto.FinalProjectJava.exceptions.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserSRV {
    @Autowired
    UserDAO userDAO;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Page<User> getAll(int pageNumber, int pageSize, String orderBy) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        return userDAO.findAll(pageable);
    }

    public User findById(Long id) {
        return userDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User save(UserDTO userDTO) {
        if (userDAO.existsByEmail(userDTO.email())) throw new BadRequestException("email already exist");
        User user = new User(userDTO.name(), userDTO.surname(),
                userDTO.email(), passwordEncoder.encode(userDTO.password()));
        return userDAO.save(user);
    }

    public User findByEmail(String email) {
        return userDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("Email " + email + " don't found"));
    }
}
