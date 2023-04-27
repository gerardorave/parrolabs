package co.parrolabs.service.impl;

import co.parrolabs.dto.*;
import co.parrolabs.dto.mongodb.OrderCustomerMongoDbDto;
import co.parrolabs.dto.request.AdditionalInfoForOrderRequest;

import co.parrolabs.dto.request.OrderCustomerRequest;
import co.parrolabs.error.ErrorMessages;
import co.parrolabs.error.GenericServiceException;
import co.parrolabs.httpclient.feign.ClientFeign;
import co.parrolabs.model.*;

import co.parrolabs.model.mongodb.OrderCustomerMongoDb;
import co.parrolabs.repository.jpa.OrderCustomerRepository;
import co.parrolabs.repository.jpa.OrderProductRepository;
import co.parrolabs.repository.mongodb.OrderCustomerMongoDbRepository;
import co.parrolabs.repository.mongodb.OrderProductMongoDbRepository;
import co.parrolabs.service.OrderCustomerService;

import co.parrolabs.util.MongoDbConstants;
import co.parrolabs.util.Transformation;
import co.parrolabs.util.ZoneDateTimeUtils;
import io.vavr.collection.Seq;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class OrderCustomerServiceImpl implements OrderCustomerService {

    private OrderCustomerRepository orderCustomerRepository;
    private OrderProductRepository orderProductRepository;

    private ModelMapper mapper;

    private MongoOperations mongoOperations;

    private OrderProductMongoDbRepository orderProductMongoDbRepository;
    private OrderCustomerMongoDbRepository orderCustomerMongoDbRepository;

    private ClientFeign clientFeign;

    private void throwAndLogValidationError(Seq<String> errors) {
        log.error(ErrorMessages.ERROR_ORDER);
        errors.forEach(log::error);
        throw new GenericServiceException(ErrorMessages.ERROR_ORDER + errors.get(0));
    }

    @Autowired
    public OrderCustomerServiceImpl(OrderCustomerRepository orderCustomerRepository,
                                    OrderProductRepository orderProductRepository,
                                    ModelMapper mapper,
                                    MongoOperations mongoOperations,
                                    OrderProductMongoDbRepository orderProductMongoDbRepository,
                                    OrderCustomerMongoDbRepository orderCustomerMongoDbRepository,
                                    ClientFeign clientFeign

    ) {
        this.orderCustomerRepository = orderCustomerRepository;
        this.mapper = mapper;
        this.mongoOperations = mongoOperations;
        this.orderProductRepository = orderProductRepository;
        this.orderProductMongoDbRepository = orderProductMongoDbRepository;
        this.orderCustomerMongoDbRepository = orderCustomerMongoDbRepository;
        this.clientFeign = clientFeign;
    }

    @Override
    @Transactional
    public OrderCustomerDto save(OrderCustomerRequest orderCustomerRequest) {
        try {
            if (orderCustomerRequest.getId() == null || "".equals(orderCustomerRequest.getId())) {
                orderCustomerRequest.setId(UUID.randomUUID());
            }

            List<UUID> productsIds = orderCustomerRequest.getProductsAndQuantities().stream()
                    .map(i -> i.getIdProduct()).collect(Collectors.toList());
            AdditionalInfoForOrderRequest request = AdditionalInfoForOrderRequest.builder()
                    .customerId(orderCustomerRequest.getCustomerId())
                    .paymentTypeId(orderCustomerRequest.getPaymentTypeId())
                    .productsIds(productsIds).build();
            if (orderCustomerRequest.getShippingAddressId() != null) {
                request.setShippingAddressId(orderCustomerRequest.getShippingAddressId());
            }

            AdditionalInfoForOrderResponse response = clientFeign.getAdditionalInfoForOrder(request);
            OrderCustomer orderCustomer = new OrderCustomer();
            orderCustomer.setId(orderCustomerRequest.getId());
            orderCustomer.setOrderDate(ZoneDateTimeUtils.nowUTC());
            orderCustomer.setCustomer(mapper.map(response.getCustomer(), Customer.class));
            if (response.getShippingAddress() != null) {
                orderCustomer.setCustomerShippingAddressId(response.getShippingAddress().getId());
            }
            orderCustomer.setPaymentType(mapper.map(response.getPaymentType(), PaymentType.class));
            OrderCustomer orderCustomerResponse = orderCustomerRepository.save(orderCustomer);
            List<OrderProduct> orderProducts = new ArrayList<>();
            for (UUID idProduct : productsIds) {
                Optional<ProductDto> productDto = Optional.empty();
                for (ProductDto p : response.getProducts()) {
                    if (p.getId().equals(idProduct)) {
                        productDto = Optional.of(p);
                        break;
                    }
                }
                if (productDto.isPresent()) {
                    Optional<ProductDto> finalProductDto = productDto;
                    OrderProductDto orderProductDto = OrderProductDto.builder()
                            .id(UUID.randomUUID())
                            .product(productDto.get())
                            .quantityProduct(orderCustomerRequest.getProductsAndQuantities().stream()
                                    .filter(i -> i.getIdProduct().equals(finalProductDto.get().getId()))
                                    .map(i -> i.getQuantity()).findFirst().get()
                            )
                            .orderCustomerId(orderCustomerResponse.getId())
                            .build();
                    System.out.println(orderProductDto.getId());
                    OrderProduct orderProduct = orderProductRepository.save(mapper.map(orderProductDto, OrderProduct.class));

                    if (orderProduct != null) {
                        orderProducts.add(orderProduct);

                    }
                }
            }

            orderCustomerResponse.setOrderProducts(orderProducts);
            OrderCustomerDto ocDto = mapper.map(orderCustomerResponse, OrderCustomerDto.class);
            if (orderCustomerResponse.getCustomerShippingAddressId() != null) {
                ocDto.setCustomerShippingAddressId(orderCustomerResponse.getCustomerShippingAddressId());
            }
            return ocDto;
        } catch (Exception e) {
            Seq<String> errors = io.vavr.collection.List.of(e.getMessage(), e.getStackTrace().toString());
            throwAndLogValidationError(errors);
        }
        return null;
    }

    @Override
    public Optional<OrderCustomerDto> findById(UUID id) {
        Optional<OrderCustomer> orderCustomer = orderCustomerRepository.findById(id);
        return mapper.map(orderCustomer, new TypeToken<Optional<OrderCustomerDto>>() {
        }.getType());
    }

    @Override
    public OrderCustomerMongoDbDto deleteById(UUID id) {

        return deleteByAttribute(id, null);
    }

    @Override
    public OrderCustomerMongoDbDto deleteByOrderNumber(Long orderNumber) {
        return deleteByAttribute(null, orderNumber);
    }

    @Transactional
    private OrderCustomerMongoDbDto deleteByAttribute(UUID id, Long orderNumber) {
        try {
            Optional<OrderCustomer> orderCustomer = Optional.empty();
            if (id == null) {
                orderCustomer = orderCustomerRepository.findOrderByOrderNumber(orderNumber);
            } else {
                orderCustomer = orderCustomerRepository.findById(id);
            }
            if (orderCustomer.isPresent()) {
                List<OrderProduct> orderProducts = orderProductRepository.getOrderProductsByOrderId(orderCustomer.get().getId().toString());
                for (OrderProduct orderProduct : orderProducts) {
                    orderProductRepository.deleteById(orderProduct.getId());
                }
                orderCustomerRepository.deleteById(orderCustomer.get().getId());
                OrderCustomerMongoDbDto orderCustomerMongoDbDto = Transformation.orderCustomerToMongoDbDto(mapper.map(orderCustomer, OrderCustomerDto.class));
                orderCustomerMongoDbDto.setIdentifier(UUID.randomUUID());
                orderCustomerMongoDbDto.setMessage("product with id " + orderCustomerMongoDbDto.getId() + " deleted.");
                orderCustomerMongoDbDto.setTypeOfOperation(MongoDbConstants.DELETED);
                orderCustomerMongoDbDto.setDate(ZoneDateTimeUtils.nowUTCAsString());

                OrderCustomerMongoDb orderCustomerMongoDb = orderCustomerMongoDbRepository.insert(mapper.map(orderCustomerMongoDbDto, OrderCustomerMongoDb.class));
                return mapper.map(orderCustomerMongoDb, OrderCustomerMongoDbDto.class);
            }
        } catch (Exception e) {
            Seq<String> errors = io.vavr.collection.List.of(e.getMessage(), e.getStackTrace().toString());
            throwAndLogValidationError(errors);
        }
        return null;
    }

}
