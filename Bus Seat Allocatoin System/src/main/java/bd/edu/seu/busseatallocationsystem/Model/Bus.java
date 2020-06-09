package bd.edu.seu.busseatallocationsystem.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bus {
    private String busNumber ;
    private String staffName ;
    private String boardingPoint ;
    private String time ;
    private Integer numberofSeatAreFree;
    private Integer costPerSeat ;

}
