package co.parrolabs.model.mongodb;


import co.parrolabs.util.MongoDbConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;


@Getter
@Setter
@Document(collection = MongoDbConstants.Collections.ORDER_CUSTOMER_COLLECTION)
@NoArgsConstructor
@AllArgsConstructor
public class OrderCustomerMongoDb {

    @Id
    private UUID identifier;

    private String id;

    private String customerShippingAddressId;

    private String orderDate;

    private String arrivalDate;

    private String paymentTypeId;

    private String customerId;

    private String message;

    private String typeOfOperation;

    private String date;

}