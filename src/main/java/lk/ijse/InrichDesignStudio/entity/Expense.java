package lk.ijse.InrichDesignStudio.entity;

import lombok.*;

import java.time.LocalDate;


@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Expense {
    private String id;
    private LocalDate date;
    private String des;
    private String type;
    private double amount;
}
