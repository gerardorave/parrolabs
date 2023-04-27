package co.parrolabs.dto.mongodb;

//import com.querydsl.core.annotations.QueryEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerMongoDbDto {

    @Id
    @JsonIgnore
    private UUID identifier;

    private String id;

    private String name;

    private String phone;

    private String email;

    private String primaryShippingAddress;

    //  MEssage the error message in case error is produced
    private String message;
    //  DELETED|ERROR
    @JsonIgnore
    private String typeOfOperation;

    private String date;

}