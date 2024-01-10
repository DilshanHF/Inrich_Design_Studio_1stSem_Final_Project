package lk.ijse.InrichDesignStudio.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDto {
    private  String orderId;
    private LocalDate date ;
    private String customerId;
    private LocalDate handOverDate;
}
