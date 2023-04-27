package co.parrolabs.service.impl;

import co.parrolabs.dto.*;
import co.parrolabs.dto.mongodb.ProductMongoDbDto;
import co.parrolabs.dto.request.AdditionalInfoForOrderRequest;

import co.parrolabs.dto.request.ProductRequest;
import co.parrolabs.model.Customer;
import co.parrolabs.model.PaymentType;
import co.parrolabs.model.Product;
import co.parrolabs.model.ShippingAddress;
import co.parrolabs.repository.jpa.CustomerRepository;
import co.parrolabs.repository.jpa.PaymentTypeRepository;
import co.parrolabs.repository.jpa.ProductRepository;
import co.parrolabs.repository.jpa.ShippingAddressRepository;
import co.parrolabs.repository.mongodb.ProductMongoDbRepository;
import co.parrolabs.service.OrderService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private ProductRepository productRepository;
    private ShippingAddressRepository shippingAddressRepository;

    private PaymentTypeRepository paymentTypeRepository;
    private CustomerRepository customerRepository;

    private ModelMapper mapper;

    private MongoOperations mongoOperations;

    private ProductMongoDbRepository productMongoDbRepository;

    @Autowired
    public OrderServiceImpl(ProductRepository productRepository,
                            ModelMapper mapper,
                            MongoOperations mongoOperations,
                            ProductMongoDbRepository productMongoDbRepository,
                            ShippingAddressRepository shippingAddressRepository,
                            CustomerRepository customerRepository,
                            PaymentTypeRepository paymentTypeRepository
    ) {
        this.productRepository = productRepository;
        this.mapper = mapper;
        this.mongoOperations = mongoOperations;
        this.productMongoDbRepository = productMongoDbRepository;
        this.shippingAddressRepository = shippingAddressRepository;
        this.customerRepository = customerRepository;
        this.paymentTypeRepository = paymentTypeRepository;
    }

    @Override
    public AdditionalInfoForOrderResponse getAdditionalInfoForOrder(AdditionalInfoForOrderRequest additionalInfoForOrderRequest){
        Customer customer = customerRepository.findById(additionalInfoForOrderRequest.getCustomerId()).get();
        PaymentType paymentType = paymentTypeRepository.findById(additionalInfoForOrderRequest.getPaymentTypeId()).get();
        Optional<ShippingAddress> address = Optional.empty();
        if(additionalInfoForOrderRequest.getShippingAddressId() != null &&
                !"".equals(additionalInfoForOrderRequest.getShippingAddressId().toString()) ){
            address = shippingAddressRepository.findById(additionalInfoForOrderRequest.getShippingAddressId());
        }
        List<String> productIdsStr = additionalInfoForOrderRequest.getProductsIds().stream().map(i->i.toString()).collect(Collectors.toList());
        List<Product> products = productRepository.getProductsByIds(productIdsStr);
        CustomerDto customerDto = mapper.map(customer, CustomerDto.class);
        AdditionalInfoForOrderResponse response = AdditionalInfoForOrderResponse.builder()
                .customer(customerDto)
                .paymentType(mapper.map(paymentType, PaymentTypeDto.class))
                .products(mapper.map(products,
                        new TypeToken<List<ProductDto>>() {
                        }.getType())).build();
        if(address.isPresent()){
            response.setShippingAddress(mapper.map(address.get(), ShippingAddressDto.class));
        }

        return response;
    }

}
