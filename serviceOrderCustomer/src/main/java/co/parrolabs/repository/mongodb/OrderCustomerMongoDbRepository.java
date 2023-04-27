package co.parrolabs.repository.mongodb;

import co.parrolabs.model.mongodb.CustomerMongoDb;
import co.parrolabs.model.mongodb.OrderCustomerMongoDb;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface OrderCustomerMongoDbRepository extends MongoRepository<OrderCustomerMongoDb, String> {

}
