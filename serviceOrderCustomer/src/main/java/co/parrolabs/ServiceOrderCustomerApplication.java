package co.parrolabs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class ServiceOrderCustomerApplication {

    public static void main(String[]args) {
        SpringApplication.run(ServiceOrderCustomerApplication.class, args);
    }
}
