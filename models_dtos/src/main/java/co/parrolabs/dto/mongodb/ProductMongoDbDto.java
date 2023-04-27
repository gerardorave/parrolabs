package co.parrolabs.dto.mongodb;

//import com.querydsl.core.annotations.QueryEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

//@QueryEntity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductMongoDbDto {

    @Id
    @JsonIgnore
    private UUID identifier;

    private String id;
    private String name;
    private String description;
    private Double price;
    private Double weight;
    private String message;
    @JsonIgnore
    private String typeOfOperation;

    private String date;

}