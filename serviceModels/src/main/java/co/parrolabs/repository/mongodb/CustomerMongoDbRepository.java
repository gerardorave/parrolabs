package co.parrolabs.repository.mongodb;

import co.parrolabs.model.mongodb.CustomerMongoDb;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerMongoDbRepository extends MongoRepository<CustomerMongoDb, String> {

}
