package lk.ijse.InrichDesignStudio.entity;


import lombok.*;

@AllArgsConstructor
@ToString
@Setter
@Getter
@NoArgsConstructor
public class Invoice {
    private String orderId;
    private String itemCode;
    private String description;
    private int qty;
    private double unitPrice;
}
