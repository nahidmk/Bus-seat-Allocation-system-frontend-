package bd.edu.seu.busseatallocationsystem.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Booking {
    private String name;
    private String phone ;
    private String busNumber ;
    private LocalDate date;
    private String paymentType;
    private String seatNumber;
}
