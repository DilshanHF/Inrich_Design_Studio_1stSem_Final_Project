package lk.ijse.InrichDesignStudio.entity;

import lk.ijse.InrichDesignStudio.dto.Tm.cartTm;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class PlaceOrder {
    private String orderId;
    private LocalDate date;
    private String customerId;
    private String invoiceId;
    private LocalDate handOverdate;
    private String type;
    private double amount;
    private List<cartTm> cartTmList = new ArrayList<>();
}
