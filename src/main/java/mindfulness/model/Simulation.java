package mindfulness.model;

import lombok.Data;
import mindfulness.SimulationType;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.util.Map;

@Data
@Entity
@Table(name = "Simulation")
public class Simulation {
    @Column
    @Id
    @GeneratedValue
    private long id;

    @Column
    @NotEmpty(message = "Simulation user id is mandatory for linking with user.")
    @NotBlank(message = "Simulation user id cannot be blank.")
    @Email(message = "Invalid email type for simulation user id.")
    private String userId;

    @Column
    @NotEmpty(message = "Simulation must have a timestamp.")
    private final Timestamp timestamp;

    @Column
    @NotEmpty(message = "Simulation type has not been specified.")
    private final SimulationType simulationType;

    @Column
    @NotEmpty(message = "Simulation filename is empty.")
    @NotBlank(message = "Simulation filename is blank.")
    private final String fileName;

    private Map<String, Long> simulationParameters;

    public Simulation(String userId, Timestamp timestamp, SimulationType simulationType,
                      String fileName, Map<String, Long> simulationParameters){
        this.userId = userId;
        this.timestamp = timestamp;
        this.simulationType = simulationType;
        this.fileName = fileName;
        this.simulationParameters = simulationParameters;
    }
}