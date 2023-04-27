package co.parrolabs.repository.mongodb;

import co.parrolabs.model.mongodb.ShippingAddressMongoDb;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShippingAddressMongoDbRepository extends MongoRepository<ShippingAddressMongoDb, String> {

}
