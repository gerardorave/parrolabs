package co.parrolabs.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;
import io.micronaut.data.annotation.TypeDef;
import io.micronaut.data.model.DataType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ORDER_CUSTOMER")
@Getter
@Setter
@MappedEntity
@JsonIgnoreProperties(ignoreUnknown = true, value={"hibernateLazyInitializer", "handler"})
public class OrderCustomer {

        @Id
    @MappedProperty(type = DataType.BYTE_ARRAY)
    @TypeDef(type = DataType.UUID)
    private UUID id;


    @Column(name = "ORDER_DATE", nullable = false)
    @MappedProperty(type = DataType.DATE)
    @TypeDef(type = DataType.TIMESTAMP)
    private ZonedDateTime orderDate;

    @Column(name = "ARRIVAL_DATE")
    @MappedProperty(type = DataType.DATE)
    @TypeDef(type = DataType.TIMESTAMP)
    private ZonedDateTime arrivalDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Customer Customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PAYMENT_TYPE_ID", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private PaymentType paymentType;

    @Column(name = "CUSTOMER_SHIPPING_ADDRESS_ID")
    @MappedProperty(type = DataType.BYTE_ARRAY)
    @TypeDef(type = DataType.UUID)
    private UUID customerShippingAddressId;

    @OneToMany(mappedBy = "orderCustomer", cascade = CascadeType.ALL, fetch =
            FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    List<OrderProduct> orderProducts;

}