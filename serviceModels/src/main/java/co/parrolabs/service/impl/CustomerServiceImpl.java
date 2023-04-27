package co.parrolabs.service.impl;

import co.parrolabs.dto.CustomerDto;
import co.parrolabs.dto.mongodb.CustomerMongoDbDto;
import co.parrolabs.dto.request.CustomerRequest;
import co.parrolabs.error.ErrorMessages;
import co.parrolabs.error.GenericServiceException;
import co.parrolabs.httpclient.ClientHttp;
import co.parrolabs.model.Customer;
import co.parrolabs.model.mongodb.CustomerMongoDb;
import co.parrolabs.repository.jpa.CustomerRepository;
import co.parrolabs.repository.mongodb.CustomerMongoDbRepository;
import co.parrolabs.service.CustomerService;
import co.parrolabs.util.*;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    private ModelMapper mapper;

    private MongoOperations mongoOperations;

    private CustomerMongoDbRepository customerMongoDbRepository;

    private ClientHttp clientHttp;


    private void throwAndLogValidationError(Seq<String> errors) {
        log.error(ErrorMessages.ERROR_CUSTOMER);
        errors.forEach(log::error);
        throw new GenericServiceException(ErrorMessages.ERROR_CUSTOMER + errors.get(0));
    }

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
        return customerOptional.isPresent() ? mapper.map(customerOptional.get(), CustomerDto.class) : null;
    }

    @Override
    public CustomerDto saveCustomer(CustomerRequest customerRequest) {

        try {
            if (customerRequest.getId() == null || "".equals(customerRequest.getId())) {
                customerRequest.setId(UUID.randomUUID());
            }
            Customer customer = customerRepository.save(mapper.map(customerRequest, Customer.class));
            return mapper.map(customer, CustomerDto.class);
        } catch (Exception e) {
            Seq<String> errors = List.of(e.getMessage());
            throwAndLogValidationError(errors);
        }
        return null;
    }


    @Override
    public CustomerMongoDbDto deleteCustomer(UUID id) {
        try {
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
        } catch (Exception e) {
            Seq<String> errors = List.of(e.getMessage());
            throwAndLogValidationError(errors);
        }
        return null;
    }

}
