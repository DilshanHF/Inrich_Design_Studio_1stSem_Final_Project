package lk.ijse.InrichDesignStudio.dto;

import lombok.*;

import java.time.LocalDate;

@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class inventoryDto {
     private String orderId;
     private String itemCode;
     private int qty;
     private LocalDate date;
}
