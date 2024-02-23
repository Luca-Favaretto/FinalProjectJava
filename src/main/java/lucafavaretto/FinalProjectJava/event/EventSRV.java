package lucafavaretto.FinalProjectJava.event;

import lucafavaretto.FinalProjectJava.exceptions.FullException;
import lucafavaretto.FinalProjectJava.exceptions.NotFoundException;
import lucafavaretto.FinalProjectJava.exceptions.UnauthorizedException;
import lucafavaretto.FinalProjectJava.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventSRV {
    @Autowired
    EventDAO eventDAO;

    public Page<Event> getAll(int pageNumber, int pageSize, String orderBy) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        return eventDAO.findAll(pageable);
    }

    public Event findById(long id) {
        return eventDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Event save(EventDTO eventDTO, User user) {
        Event event = new Event(eventDTO.title(), eventDTO.description(), eventDTO.date(), eventDTO.location(), eventDTO.capacity(), user);
        return eventDAO.save(event);
    }

    public Event findByIdAndUpdate(long id, EventDTO eventDTO, User user) {
        Event found = findById(id);
        if (!user.getId().equals(found.getManager().getId())) throw new UnauthorizedException("Manager with wrong id");
        found.setTitle(eventDTO.title());
        found.setDescription(eventDTO.description());
        found.setDate(eventDTO.date());
        found.setLocation(eventDTO.location());
        found.setCapacity(eventDTO.capacity());
        return eventDAO.save(found);
    }

    public void deleteById(long id, User user) {
        Event found = findById(id);
        if (!user.getId().equals(found.getManager().getId())) throw new UnauthorizedException("Manager with wrong id");
        eventDAO.delete(found);
    }

    public void reserve(long id, User user) {
        Event found = findById(id);
        if (found.getParticipants().size() < found.getCapacity()) {
            found.addParticipant(user);
            eventDAO.save(found);
        } else throw new FullException();
    }

    public void removeReserve(long id, User user) {
        Event found = findById(id);
        found.removeParticipant(user);
        eventDAO.save(found);
    }

    public List<Event> findEventByParticipant(User user) {
        return eventDAO.findEventByParticipant(user);
    }


}