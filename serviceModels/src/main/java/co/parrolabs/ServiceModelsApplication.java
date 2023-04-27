package co.parrolabs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;


@SpringBootApplication(exclude = MongoReactiveAutoConfiguration.class)
public class ServiceModelsApplication {

    public static void main(String[]args) {
        SpringApplication.run(ServiceModelsApplication.class, args);
    }
}
