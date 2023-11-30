package lk.ijse.InrichDesignStudio.dto;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@AllArgsConstructor
@Getter
@NoArgsConstructor
@ToString
public class attendDto {
    private String employeeId;
    private LocalTime time;
    private LocalDate date;
    private String status;

}
