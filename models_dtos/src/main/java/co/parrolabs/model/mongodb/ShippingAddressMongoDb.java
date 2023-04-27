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
@Document(collection = MongoDbConstants.Collections.SHIPPING_ADDRESS_COLLECTION)
@NoArgsConstructor
@AllArgsConstructor
public class ShippingAddressMongoDb {

    @Id
    private UUID identifier;

    private String id;

    private String number;

    private String city;

    private String zipCode;

    private String country;

    private String customerId;

    private String message;

    private String typeOfOperation;

    private String date;
}