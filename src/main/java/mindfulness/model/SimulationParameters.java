package mindfulness.model;

import lombok.Data;

import javax.persistence.Entity;
import java.util.HashMap;

@Data
@Entity
public class SimulationParameters {
    private HashMap<String, Long> parameters;
}