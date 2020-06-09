package bd.edu.seu.busseatallocationsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class BusSeatAllocationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusSeatAllocationSystemApplication.class, args);
    }

}
