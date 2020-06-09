package bd.edu.seu.busseatallocationsystem.Service;

import bd.edu.seu.busseatallocationsystem.Model.Bus;
//import bd.edu.seu.busseatallocationsystem.Repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class BusService {

    private RestTemplate template = new RestTemplate();

//    public static final String BASE_URL = "http://localhost:8081/v2/bus";
    public static final String BASE_URL = "https://bus-seat-allocation-system-bac.herokuapp.com/v2/bus";

    public List<Bus> findAll()
    {
        ResponseEntity<Bus[]> responseEntity = template.getForEntity(BASE_URL,Bus[].class);
        Bus[] buses = responseEntity.getBody();
        List<Bus> buses1 = Arrays.asList(buses);
        return buses1;
    }

    public void CreateBus(Bus bus)
    {
        template.postForObject(BASE_URL,bus,Bus.class);
    }

    public void DeleteBus(Bus bus)
    {
        template.delete(BASE_URL+"/delete/"+bus.getBusNumber());
    }

    public Bus findByBusNumber(String busNumber)
    {
        return template.getForObject(BASE_URL + "/" + busNumber, Bus.class);

    }


}
