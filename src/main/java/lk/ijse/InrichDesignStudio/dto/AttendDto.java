package lk.ijse.InrichDesignStudio.dto;


import lombok.*;

import java.time.LocalDate;

@Setter
@AllArgsConstructor
@Getter
@NoArgsConstructor
@ToString
public class AttendDto {
    private String employeeId;
    private String name;
    private String nic;
    private LocalDate date;
    private String status;

}
