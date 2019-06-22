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

    private String name;
    private int age;
    private String gender;
    private String occupation;
    private Long mindfulness;
    private Long humour;
    private Long music;
}