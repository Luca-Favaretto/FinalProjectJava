package lucafavaretto.FinalProjectJava.user;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserSRV {
    @Autowired
    UserDAO userDAO;

    public Page<User> getAll(int pageNumber, int pageSize, String orderBy) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        return userDAO.findAll(pageable);
    }

    public User save(UserDTO userDTO) throws BadRequestException {
        if (userDAO.existsByEmail(userDTO.email())) throw new BadRequestException("email already exist");
        User user = new User(userDTO.name(), userDTO.surname(),
                userDTO.email(), userDTO.password());
        return userDAO.save(user);
    }
}
