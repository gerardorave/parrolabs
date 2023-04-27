package co.parrolabs.repository.mongodb;

import co.parrolabs.model.mongodb.ProductMongoDb;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductMongoDbRepository extends MongoRepository<ProductMongoDb, String> {

}
