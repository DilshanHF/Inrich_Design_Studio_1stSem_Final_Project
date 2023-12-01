package lk.ijse.InrichDesignStudio.dto;


import lombok.*;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@ToString
public class ItemDto {
    private  String id;
    private  String name;
    private String type;
    private  double amount;
}
