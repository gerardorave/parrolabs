package co.parrolabs.model;

import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;
import io.micronaut.data.annotation.TypeDef;
import io.micronaut.data.model.DataType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "PAYMENT_TYPE")
@Getter
@Setter
@MappedEntity
public class PaymentType {

    @Id
    @MappedProperty(type = DataType.BYTE_ARRAY)
    @TypeDef(type = DataType.UUID)
    private UUID id;

    @Column(name = "NAME", nullable = false)
    private String name;

}