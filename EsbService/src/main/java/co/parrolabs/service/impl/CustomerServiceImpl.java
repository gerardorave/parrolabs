package co.parrolabs.service.impl;

import co.parrolabs.dto.CustomerDto;
import co.parrolabs.dto.mongodb.CustomerMongoDbDto;
import co.parrolabs.dto.request.CustomerRequest;
import co.parrolabs.client.httpclient.feign.ClientFeignServiceModelsCustomer;
import co.parrolabs.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(rollbackFor=  Exception.class)
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
