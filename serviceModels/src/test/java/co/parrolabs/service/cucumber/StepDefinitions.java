package co.parrolabs.service.cucumber;

import co.parrolabs.dto.CustomerDto;
import co.parrolabs.repository.jpa.CustomerRepository;
import co.parrolabs.service.CustomerService;
import co.parrolabs.service.impl.CustomerServiceImpl;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.modelmapper.ModelMapper;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;


public class StepDefinitions{

    private UUID customerId;

    private CustomerExpected customerExpected;

    private CustomerService customerService;

    private CustomerRepository customerRepository;

    private ModelMapper mapper;

    @Before
    public void setUp() {

        customerExpected = new CustomerExpected();
        mapper = mock(ModelMapper.class);
        customerRepository = mock(CustomerRepository.class);
        customerService = new CustomerServiceImpl(customerRepository, mapper, null, null, null);
        customerExpected.setUp(customerService, customerRepository, mapper);

    }

    @Given("Customer Id is {string}")
    public void given_some_Customer(String id) {
        customerId = UUID.fromString(id);
    }

    @When("Searching for CustomerId")
    public void i_ask_if_Customer_is_null() {
        CustomerDto customerDto = customerExpected.getCustomerDto(customerId);
        if(customerDto == null)
            customerId = null;
        else
            customerId = customerDto.getId();
    }

    @Then("Return {string}")
    public void i_should_be_told(String id) {
        assertEquals(String.valueOf(customerId), id);
    }

}
