package lk.ijse.InrichDesignStudio.entity;

import lombok.*;

import java.time.LocalDate;


@AllArgsConstructor
@ToString
@Setter
@NoArgsConstructor
@Getter

public class Attend {
    private String employeeId;
    private String name;
    private String nic;
    private LocalDate date;
    private String status;
}
