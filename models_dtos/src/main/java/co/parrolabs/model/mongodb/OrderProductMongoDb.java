package co.parrolabs.model.mongodb;

import co.parrolabs.util.MongoDbConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;


@Getter
@Setter
@Document(collection = MongoDbConstants.Collections.ORDER_PRODUCT_COLLECTION)
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductMongoDb {

    @Id
    private UUID identifier;

    private String productId;

    private String orderCustomerId;

    private String quantityProduct;

    private String message;

    private String typeOfOperation;

    private String date;

}