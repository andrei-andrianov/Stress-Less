package mindfulness.model;


import lombok.Data;
import mindfulness.SimulationType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

@Data
@Entity
@Table(name = "User")
public class User {

    @Column
    @NotEmpty(message = "User email is mandatory for identification.")
    @NotBlank(message = "User id cannot be blank.")
    @Email(message = "Invalid email type for user id.")
    @Id
    private String id;

    @Column
    private String name;

    @Column
    private int age;

    @Column
    private String gender;

    @Column
    private String occupation;

    @Column
    private String ethnicity;

    private Map<SimulationType, Long> preferences;

    public User(String id, String name, int age, String gender, String occupation, String ethnicity){
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.occupation = occupation;
        this.ethnicity = ethnicity;
    }

    public User(String id){
        this.id = id;
    }
}