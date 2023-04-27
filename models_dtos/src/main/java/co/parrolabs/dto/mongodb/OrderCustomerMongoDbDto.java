package co.parrolabs.dto.mongodb;

//import com.querydsl.core.annotations.QueryEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCustomerMongoDbDto {

    @Id
    private UUID identifier;

    private String id;

    private String customerShippingAddressId;

    private String orderDate;

    private String arrivalDate;

    private String paymentTypeId;

    private String customerId;

    private String message;
    @JsonIgnore
    private String typeOfOperation;

    private String date;

}