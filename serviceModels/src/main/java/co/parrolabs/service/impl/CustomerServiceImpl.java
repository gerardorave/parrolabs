package co.parrolabs.service.impl;

import co.parrolabs.dto.CustomerDto;
import co.parrolabs.dto.mongodb.CustomerMongoDbDto;
import co.parrolabs.dto.request.CustomerRequest;
import co.parrolabs.httpclient.ClientHttp;
import co.parrolabs.model.Customer;
import co.parrolabs.model.mongodb.CustomerMongoDb;
import co.parrolabs.repository.jpa.CustomerRepository;
import co.parrolabs.repository.mongodb.CustomerMongoDbRepository;
import co.parrolabs.service.CustomerService;
import co.parrolabs.util.MongoDbConstants;
import co.parrolabs.util.Transformation;
import co.parrolabs.util.ZoneDateTimeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    private ModelMapper mapper;

    private MongoOperations mongoOperations;

    private CustomerMongoDbRepository customerMongoDbRepository;

    private ClientHttp clientHttp;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               ModelMapper mapper,
                               MongoOperations mongoOperations,
                               CustomerMongoDbRepository customerMongoDbRepository,
                               ClientHttp clientHttp
    ) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
        this.mongoOperations = mongoOperations;
        this.customerMongoDbRepository = customerMongoDbRepository;
        this.clientHttp = clientHttp;
    }

    @Override
    public CustomerDto getCustomerById(UUID id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(!customerOptional.isPresent())
            return null;
        else {
            return mapper.map(customerOptional.get(), CustomerDto.class);
        }
    }

    @Override
    public CustomerDto saveCustomer(CustomerRequest customerRequest) {

        if (customerRequest.getId() == null || "".equals(customerRequest.getId())) {
            customerRequest.setId(UUID.randomUUID());
        }
        Customer customer = customerRepository.save(mapper.map(customerRequest, Customer.class));
        return mapper.map(customer, CustomerDto.class);
    }


    @Override
    public CustomerMongoDbDto deleteCustomer(UUID id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();

            customerRepository.delete(customer);
            CustomerDto customerDto = mapper.map(customer, CustomerDto.class);
            CustomerMongoDbDto customerMongoDbDto = Transformation.customerDtoToMongoDbDto(customerDto);
            CustomerMongoDb customerMongoDb = mapper.map(customerMongoDbDto, CustomerMongoDb.class);
            customerMongoDb.setIdentifier(UUID.randomUUID());
            customerMongoDb.setMessage("customer with id " + customerMongoDb.getId() + " deleted.");
            customerMongoDb.setTypeOfOperation(MongoDbConstants.DELETED);
            customerMongoDb.setDate(ZoneDateTimeUtils.nowUTCAsString());
            customerMongoDb = customerMongoDbRepository.insert(customerMongoDb);
            return mapper.map(customerMongoDb, CustomerMongoDbDto.class);
        }
        return null;
    }

}
