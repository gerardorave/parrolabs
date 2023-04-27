package co.parrolabs.model;

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
@Table(name = "PRODUCT")
@Getter
@Setter
@MappedEntity
public class Product {

    @Id
    @MappedProperty(type = DataType.BYTE_ARRAY)
    @TypeDef(type = DataType.UUID)
    private UUID id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "PRICE", nullable = false)
    private Double price;

    @Column(name = "WEIGHT", nullable = false)
    private Double weight;

    @OneToMany(mappedBy = "product")
    List<OrderProduct> orderProducts;
}