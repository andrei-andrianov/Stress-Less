package mindfulness;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@AllArgsConstructor
public class User {

    private @Id @NotEmpty(message = "User email is mandatory for identification.")
    @NotBlank(message = "User id cannot be blank.")
    @Email(message = "Invalid email type for user id.")
    String id;
    private String name;
    private int age;
    private String gender;
    private String occupation;
    private String ethnicity;

}