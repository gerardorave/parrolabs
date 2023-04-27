package co.parrolabs.model.mongodb;

import co.parrolabs.util.MongoDbConstants;
import io.micronaut.data.annotation.MappedProperty;
import io.micronaut.data.annotation.TypeDef;
import io.micronaut.data.model.DataType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;


@Getter
@Setter
@Document(collection = MongoDbConstants.Collections.PRODUCT_COLLECTION)
@NoArgsConstructor
@AllArgsConstructor
public class ProductMongoDb {

    @Id
    private UUID identifier;

    private String id;

    private String name;


    private String description;

    private Double price;

    private Double weight;

    private String message;

    private String typeOfOperation;

    private String date;

}