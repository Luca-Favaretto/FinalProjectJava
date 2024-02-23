package lucafavaretto.FinalProjectJava.event;

import lucafavaretto.FinalProjectJava.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventDAO extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e WHERE :user MEMBER OF e.participants")
    List<Event> findEventByParticipant(User user);
}
