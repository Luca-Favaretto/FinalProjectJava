package lucafavaretto.FinalProjectJava.event;

import lucafavaretto.FinalProjectJava.exceptions.BadRequestException;
import lucafavaretto.FinalProjectJava.user.User;
import lucafavaretto.FinalProjectJava.user.UserDTO;
import lucafavaretto.FinalProjectJava.user.UserSRV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventCTRL {
    @Autowired
    EventSRV eventSRV;

    @GetMapping
    public Page<Event> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                              @RequestParam(defaultValue = "10") int pageSize,
                              @RequestParam(defaultValue = "date") String orderBy) {
        return eventSRV.getAll(pageNumber, pageSize, orderBy);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Event findById(@PathVariable long id) {
        return eventSRV.findById(id);
    }

    @PostMapping
    @PreAuthorize(("hasAuthority('MANAGER')"))
    @ResponseStatus(HttpStatus.CREATED)
    public Event save(@RequestBody EventDTO eventDTO, @AuthenticationPrincipal User currentAuthenticatedUser) throws IOException {
        return this.eventSRV.save(eventDTO, currentAuthenticatedUser);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Event findByIdAndUpdate(@PathVariable long id, @RequestBody EventDTO eventDTO, @AuthenticationPrincipal User currentAuthenticatedUser) {

        return eventSRV.findByIdAndUpdate(id, eventDTO, currentAuthenticatedUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthorById(@PathVariable long id) {
        eventSRV.deleteById(id);
    }

}

