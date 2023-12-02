package lk.ijse.InrichDesignStudio.dto;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class invoiceDto {
    private String orderId;
    private String itemCode;
    private String description;
    private int qty;
    private double unitPrice;

}
