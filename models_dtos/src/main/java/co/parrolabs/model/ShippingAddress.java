package co.parrolabs.model;

import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;
import io.micronaut.data.annotation.TypeDef;
import io.micronaut.data.model.DataType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "SHIPPING_ADDRESS")
@Getter
@Setter
@MappedEntity
public class ShippingAddress {

    @Id
    @MappedProperty(type = DataType.BYTE_ARRAY)
    @TypeDef(type = DataType.UUID)
    private UUID id;

    @Column(name = "NUMBER", nullable = false)
    private String number;

    @Column(name = "CITY", nullable = false)
    private String city;

    @Column(name = "ZIP_CODE", nullable = false)
    private String zip_code;

    @Column(name = "COUNTRY", nullable = false)
    private String country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", insertable = false, updatable = false)
    private Customer customer;

}