package lucafavaretto.FinalProjectJava.event;

import java.time.LocalDate;

public record EventDTO(String title,
                       String description,
                       LocalDate date,
                       String location,
                       int capacity) {
}
