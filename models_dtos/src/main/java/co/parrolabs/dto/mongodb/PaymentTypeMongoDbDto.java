package co.parrolabs.dto.mongodb;

//import com.querydsl.core.annotations.QueryEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTypeMongoDbDto {

    @Id
    @JsonIgnore
    private UUID identifier;

    private String id;

    private String name;

    private String message;
    @JsonIgnore
    private String typeOfOperation;

    private String date;

}