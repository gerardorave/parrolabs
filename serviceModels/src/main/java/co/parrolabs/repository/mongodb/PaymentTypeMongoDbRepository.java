package co.parrolabs.repository.mongodb;


import co.parrolabs.model.mongodb.PaymentTypeMongoDb;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentTypeMongoDbRepository extends MongoRepository<PaymentTypeMongoDb, String> {

}
