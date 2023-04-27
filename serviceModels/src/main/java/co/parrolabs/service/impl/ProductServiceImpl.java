package co.parrolabs.service.impl;

import co.parrolabs.dto.CustomerDto;
import co.parrolabs.dto.OrderCustomerDto;
import co.parrolabs.dto.ProductDto;
import co.parrolabs.dto.mongodb.ProductMongoDbDto;
import co.parrolabs.dto.request.ProductRequest;
import co.parrolabs.error.ErrorMessages;
import co.parrolabs.error.GenericServiceException;
import co.parrolabs.httpclient.ClientHttp;
import co.parrolabs.model.OrderCustomer;
import co.parrolabs.model.Product;
import co.parrolabs.model.mongodb.ProductMongoDb;
import co.parrolabs.repository.jpa.ProductRepository;
import co.parrolabs.repository.mongodb.ProductMongoDbRepository;
import co.parrolabs.service.ProductService;
import co.parrolabs.util.*;
import com.mongodb.client.MongoCollection;
import io.vavr.collection.Seq;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    private ModelMapper mapper;

    private MongoOperations mongoOperations;

    private ProductMongoDbRepository productMongoDbRepository;

    private ClientHttp clientHttp;

    private void throwAndLogValidationError(Seq<String> errors) {
        log.error(ErrorMessages.ERROR_PRODUCT);
        errors.forEach(log::error);
        throw new GenericServiceException(ErrorMessages.ERROR_PRODUCT + errors.get(0));
    }

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              ModelMapper mapper,
                              MongoOperations mongoOperations,
                              ProductMongoDbRepository productMongoDbRepository,
                              ClientHttp clientHttp
    ) {
        this.productRepository = productRepository;
        this.mapper = mapper;
        this.mongoOperations = mongoOperations;
        this.productMongoDbRepository = productMongoDbRepository;
        this.clientHttp = clientHttp;
    }

    @Override
    public ProductDto getProductById(UUID id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.isPresent() ? mapper.map(productOptional.get(), ProductDto.class) : null;
    }

    @Override
    public ProductDto saveProduct(ProductRequest productRequest) {
        try {
            if (productRequest.getId() == null || "".equals(productRequest.getId())) {
                productRequest.setId(UUID.randomUUID());
            }
            Product product = productRepository.save(mapper.map(productRequest, Product.class));
            return mapper.map(product, ProductDto.class);
        } catch (Exception e) {
            Seq<String> errors = io.vavr.collection.List.of(e.getMessage());
            throwAndLogValidationError(errors);
        }
        return null;
    }

    @Override
    public List<ProductMongoDbDto> getDeletedProducts() {
        List<ProductMongoDb> products = mongoOperations.find(Query.query(Criteria.where("typeOfOperation").is(MongoDbConstants.DELETED)),
                ProductMongoDb.class);
        return mapper.map(products, new TypeToken<List<ProductMongoDbDto>>() {
        }.getType());
    }

    @Override
    public ProductDto updateProduct(ProductRequest productRequest) {
        try {
            //id can't be null
            Product product = productRepository.save(mapper.map(productRequest, Product.class));
            return mapper.map(product, ProductDto.class);
        } catch (Exception e) {
            Seq<String> errors = io.vavr.collection.List.of(e.getMessage());
            throwAndLogValidationError(errors);
        }
        return null;
    }

    @Override
    public ProductMongoDbDto deleteProduct(UUID id) {
        try {
            Optional<Product> optionalProduct = productRepository.findById(id);

            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();

                productRepository.delete(product);
                ProductDto productDto = mapper.map(product, ProductDto.class);
                ProductMongoDbDto productMongoDbDto = Transformation.productDtoToMongoDbDto(productDto);
                ProductMongoDb productMongoDb = mapper.map(productMongoDbDto, ProductMongoDb.class);
                productMongoDb.setIdentifier(UUID.randomUUID());
                productMongoDb.setMessage("product with id " + productMongoDb.getId() + " deleted.");
                productMongoDb.setTypeOfOperation(MongoDbConstants.DELETED);
                productMongoDb.setDate(ZoneDateTimeUtils.nowUTCAsString());
                productMongoDb = productMongoDbRepository.insert(productMongoDb);
                return mapper.map(productMongoDb, ProductMongoDbDto.class);
            }
        } catch (Exception e) {
            Seq<String> errors = io.vavr.collection.List.of(e.getMessage());
            throwAndLogValidationError(errors);
        }
        return null;
    }

}
