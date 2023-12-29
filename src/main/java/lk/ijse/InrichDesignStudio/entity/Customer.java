package lk.ijse.InrichDesignStudio.entity;


import lombok.*;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Customer {
    private String id;
    private  String name;
    private String address;
    private  String tel;
    private  String email;
}
