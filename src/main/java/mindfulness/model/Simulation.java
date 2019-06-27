package mindfulness.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity(name = "Simulation")
@Table(name = "simulation")
public class Simulation {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    private Timestamp timestamp;
    private SimulationType simulationType;
    private String fileName;
}