package lk.ijse.InrichDesignStudio.entity;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Orders {
    private  String orderId;
    private LocalDate date ;
    private String customerId;
    private LocalDate handOverDate;
}
