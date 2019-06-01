package mindfulness;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;

@Data
@Entity
@AllArgsConstructor
public class Simulation {

    private @NotEmpty(message = "Simulation user id is mandatory for linking with user.")
    @NotBlank(message = "Simulation user id cannot be blank.")
    @Email(message = "Invalid email type for simulation user id.")
    @Id String userId;
    private final @NotEmpty(message = "Simulation must have a timestamp.")
    Timestamp timestamp;
    private final @NotEmpty(message = "Simulation type has not been specified.")
    SimulationType simulationType;
    private final @NotEmpty(message = "Simulation filename is empty.")
    @NotBlank(message = "Simulation filename is blank.")
    String fileName;

}
