package lk.ijse.InrichDesignStudio.entity;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Item {
    private  String id;
    private  String name;
    private String type;
    private  double amount;
}
