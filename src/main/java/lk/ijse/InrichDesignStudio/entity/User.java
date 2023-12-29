package lk.ijse.InrichDesignStudio.entity;

import lombok.*;

@Setter
@AllArgsConstructor
@Getter
@NoArgsConstructor
@ToString
public class User {
    private String first_name;
    private String last_name;
    private String email;
    private String password;
}
