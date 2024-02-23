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
import java.util.List;
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
    public Event findById(@PathVariable long id) {
        return eventSRV.findById(id);
    }

    @PostMapping("/me")
    @PreAuthorize(("hasAuthority('MANAGER')"))
    @ResponseStatus(HttpStatus.CREATED)
    public Event save(@RequestBody EventDTO eventDTO, @AuthenticationPrincipal User currentAuthenticatedUser) throws IOException {
        return this.eventSRV.save(eventDTO, currentAuthenticatedUser);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public Event findByIdAndUpdate(@PathVariable long id, @RequestBody EventDTO eventDTO, @AuthenticationPrincipal User currentAuthenticatedUser) {

        return eventSRV.findByIdAndUpdate(id, eventDTO, currentAuthenticatedUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthorById(@PathVariable long id, @AuthenticationPrincipal User currentAuthenticatedUser) {
        eventSRV.deleteById(id, currentAuthenticatedUser);
    }

    @PatchMapping("/me/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void reserve(@PathVariable long id, @AuthenticationPrincipal User currentAuthenticatedUser) {
        eventSRV.reserve(id, currentAuthenticatedUser);
    }

    @PatchMapping("/removeMe/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeReserve(@PathVariable long id, @AuthenticationPrincipal User currentAuthenticatedUser) {
        eventSRV.removeReserve(id, currentAuthenticatedUser);
    }

    @GetMapping("/me")
    public List<Event> findEventByParticipant(@AuthenticationPrincipal User currentAuthenticatedUser) {
        return eventSRV.findEventByParticipant(currentAuthenticatedUser);
    }


}

