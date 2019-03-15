package tj.javadeveloper.issspyapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IssSpyAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(IssSpyAppApplication.class, args);
    }

    // TODO add LocationEntity and JPA Repository for H2
    // TODO add H2 config
    // TODO distance unit converter

}
