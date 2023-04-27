package co.parrolabs.service.cucumber;

import co.parrolabs.builder.customer.CustomerBuilder;
import co.parrolabs.dto.CustomerDto;
import co.parrolabs.model.Customer;
import co.parrolabs.repository.jpa.CustomerRepository;
import co.parrolabs.service.CustomerService;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;


public class CustomerExpected {


    private CustomerDto customerDto;
    private Customer customer;


    private CustomerRepository customerRepository;

    private ModelMapper mapper;

    private CustomerService customerService;

    public void setUp(CustomerService CustomerService,
                      CustomerRepository CustomerRepository,
                      ModelMapper mapper) {
        this.customerRepository = CustomerRepository;
        this.mapper = mapper;
        this.customerService = CustomerService;
        UUID id = UUID.fromString("57ba5331-dee1-4803-86c3-0dd3511097bf");
        customerDto = CustomerBuilder.customerDto();
        customerDto.setId(id);
        customer = CustomerBuilder.customer();
        customer.setId(id);
        Mockito.when(this.customerRepository.findById(any(UUID.class)))
                .thenAnswer(
                        invocation -> {
                            UUID arg = UUID.fromString(String.valueOf(invocation.getArguments()[0]));
                            if (UUID.fromString("57ba5331-dee1-4803-86c3-0dd3511097bf").equals(arg)) {
                                return Optional.of(customer);
                            }else
                                return Optional.empty();
                        });
        Mockito.when(mapper.map(customer, CustomerDto.class))
                .thenReturn(customerDto);
    }

    public CustomerDto getCustomerDto(UUID idCustomer) {
        CustomerDto CustomerDto = this.customerService.getCustomerById(idCustomer);
        return CustomerDto;
    }

}
