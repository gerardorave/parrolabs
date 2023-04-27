package co.parrolabs.repository.mongodb;


import co.parrolabs.model.mongodb.OrderProductMongoDb;
import co.parrolabs.model.mongodb.PaymentTypeMongoDb;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderProductMongoDbRepository extends MongoRepository<OrderProductMongoDb, String> {

}
