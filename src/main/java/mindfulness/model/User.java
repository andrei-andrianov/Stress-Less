package mindfulness.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
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
}