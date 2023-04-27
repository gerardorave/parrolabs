package co.parrolabs.builder.customer;

import co.parrolabs.dto.CustomerDto;
import co.parrolabs.dto.request.CustomerRequest;
import co.parrolabs.model.Customer;

import java.util.UUID;

public class CustomerBuilder {


    public static CustomerDto customerDto() {
        return CustomerDto.builder()
                .id(UUID.randomUUID())
                .email("juan.luis@gmail.com")
                .name("Customer name")
                .phone("555-3232423")
                .build();
    }

    public static Customer customer() {
        Customer c = new Customer();
        c.setId(UUID.randomUUID());
        c.setEmail("juan.luis@gmail.com");
        c.setName("Customer name");
        c.setPhone("555-3232423");
        return c;
    }

    public static CustomerRequest customerRequest(CustomerDto CustomerDto) {
        return CustomerRequest.builder()
                .id(UUID.randomUUID())
                .email("juan.luis@gmail.com")
                .name("Customer name")
                .phone("555-3232423")
                .build();

    }

}
