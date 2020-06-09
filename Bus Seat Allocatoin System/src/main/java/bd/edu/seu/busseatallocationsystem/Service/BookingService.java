package bd.edu.seu.busseatallocationsystem.Service;

import bd.edu.seu.busseatallocationsystem.Model.Booking;
import bd.edu.seu.busseatallocationsystem.Model.Bus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class BookingService {

    private RestTemplate template = new RestTemplate();

//    public static final String BASE_URL = "http://localhost:8081/v2/booking";
    public static final String BASE_URL = "https://bus-seat-allocation-system-bac.herokuapp.com/v2/booking";
    public List<Booking> findAll()
    {
        ResponseEntity<Booking[]> responseEntity = template.getForEntity(BASE_URL,Booking[].class);
        Booking[] bookings = responseEntity.getBody();
        List<Booking> bookings1 = Arrays.asList(bookings);
        System.out.println(bookings1);
        return bookings1;
    }

    public void CreateBooking(Booking booking)
    {
        template.postForObject(BASE_URL,booking,Booking.class);
    }

    public void DeleteBooking(String phone, String busNumber, int numberOfBooking)
    {
        template.delete(BASE_URL+"/"+phone+"/"+"/"+busNumber+"/"+numberOfBooking);
    }

    public List<Booking> findByPhoneNumber(String phone)
    {
        ResponseEntity<Booking[]> responseEntity = template.getForEntity(BASE_URL+"/by/"+phone,Booking[].class);
        Booking[] bookings = responseEntity.getBody();
        List<Booking> bookings1 = Arrays.asList(bookings);
        return bookings1;
    }

    public List<Booking> findByPhoneNumberAndBusNumber(String phone, String busNumber)
    {
        ResponseEntity<Booking[]> responseEntity = template.getForEntity(BASE_URL+"/by/"+phone+"/"+busNumber,Booking[].class);
        Booking[] bookings = responseEntity.getBody();
        List<Booking> bookings1 = Arrays.asList(bookings);
        System.out.println(bookings1);
        return bookings1;
    }

    public List<Booking> findByBusNumber(String busNumber)
    {
        ResponseEntity<Booking[]> responseEntity = template.getForEntity(BASE_URL+"/"+busNumber,Booking[].class);
        Booking[] bookings = responseEntity.getBody();
        List<Booking> bookings1 = Arrays.asList(bookings);
        return bookings1;
    }
    public void CreateBookingList(List<Booking> bookings)
    {
        template.postForObject(BASE_URL+"/"+bookings,bookings,Booking.class);
    }
}
