package tj.javadeveloper.issspyapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IssSpyAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(IssSpyAppApplication.class, args);
    }

    // TODO add csrf disable
    // TODO add custom errors
    // TODO add custom data for location
    // TODO geographical coordinate converter
    // TODO distance unit converter

}
