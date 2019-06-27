package mindfulness.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Entity(name = "User")
@Table(name = "user")
public class User {

    @NotEmpty(message = "User email is mandatory for identification.")
    @Email(message = "Invalid email type for user id.")
    @Id
    private String id;
    private Long simulationId;

//    user info
    private String name;
    private Integer age;
    private String gender;
    private String occupation;

//    simulation preferences
    private Float mindfulness;
    private Float humour;
    private Float music;

//    initial values for parameters
    private Float stressEvent;
    private Float stressLevel;
    private Float positiveBelief;
    private Float negativeBelief;
}