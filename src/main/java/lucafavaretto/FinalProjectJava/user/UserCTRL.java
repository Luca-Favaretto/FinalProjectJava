package lucafavaretto.FinalProjectJava.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserCTRL {
    @Autowired
    UserSRV userSRV;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<User> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                             @RequestParam(defaultValue = "10") int pageSize,
                             @RequestParam(defaultValue = "name") String orderBy) {
        return userSRV.getAll(pageNumber, pageSize, orderBy);
    }

}
