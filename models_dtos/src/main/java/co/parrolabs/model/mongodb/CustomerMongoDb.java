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
@Document(collection = MongoDbConstants.Collections.CUSTOMER_COLLECTION)
@NoArgsConstructor
@AllArgsConstructor
public class CustomerMongoDb {

    @Id
    private UUID identifier;

    private String id;

    private String name;

    private String phone;

    private String email;

    private String primaryShippingAddress;

    //  MEssage the error message in case error is produced
    private String message;
    //  DELETED|ERROR
    private String typeOfOperation;

    private String date;

}