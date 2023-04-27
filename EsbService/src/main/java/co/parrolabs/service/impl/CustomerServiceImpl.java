package co.parrolabs.service.impl;

import co.parrolabs.dto.CustomerDto;
import co.parrolabs.dto.mongodb.CustomerMongoDbDto;
import co.parrolabs.dto.request.CustomerRequest;
import co.parrolabs.httpclient.feign.ClientFeignServiceModelsCustomer;
import co.parrolabs.model.Customer;
import co.parrolabs.model.mongodb.CustomerMongoDb;
import co.parrolabs.service.CustomerService;
import co.parrolabs.util.MongoDbConstants;
import co.parrolabs.util.Transformation;
import co.parrolabs.util.ZoneDateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {


    private ClientFeignServiceModelsCustomer clientFeignServiceModelsCustomer;

    @Autowired
    public CustomerServiceImpl(ClientFeignServiceModelsCustomer clientFeignServiceModelsCustomer) {
        this.clientFeignServiceModelsCustomer = clientFeignServiceModelsCustomer;
    }

    @Override
    @Cacheable(value = "customerCache", key = "#id", unless = "#result == null")
    public Optional<CustomerDto> getCustomerById(UUID id) {
        return Optional.of(clientFeignServiceModelsCustomer.getCustomerById(id));
    }

    @Override
    public Optional<CustomerDto> saveCustomer(CustomerRequest customerRequest) {

        return Optional.of(clientFeignServiceModelsCustomer.saveCustomer(customerRequest));
    }


    @Override
    public Optional<CustomerMongoDbDto> deleteCustomer(UUID id) {
        return Optional.of(clientFeignServiceModelsCustomer.deleteCustomer(id));
    }

}
