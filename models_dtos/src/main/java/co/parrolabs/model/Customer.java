package co.parrolabs.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;
import io.micronaut.data.annotation.TypeDef;
import io.micronaut.data.model.DataType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "CUSTOMER")
@Getter
@Setter
@MappedEntity
public class Customer {

    @Id
    @MappedProperty(type = DataType.BYTE_ARRAY)
    @TypeDef(type = DataType.UUID)
    private UUID id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "PHONE", nullable = false)
    private String phone;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "PRIMARY_SHIPPING_ADDRESS", nullable = false)
    @MappedProperty(type = DataType.BYTE_ARRAY)
    @TypeDef(type = DataType.UUID)
    UUID primaryShippingAddress;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch =
            FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<ShippingAddress> shippingAddresses;

}