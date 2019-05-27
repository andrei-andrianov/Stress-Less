package mindfulness;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class User {

    private @Id @NotEmpty(message = "User email is mandatory for identification.") @NotNull(message = "User email is mandatory for identification.") @Email(message = "Invalid email type for user id.") String id;
    private String name;
    private int age;
    private String gender;
    private String occupation;
    private String ethnicity;

    public User(){

    }

    public User(String id, String name, int age, String gender, String occupation, String ethnicity) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.occupation = occupation;
        this.ethnicity = ethnicity;
    }

}