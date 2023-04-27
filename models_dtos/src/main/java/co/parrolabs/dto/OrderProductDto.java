package co.parrolabs.dto;

import co.parrolabs.model.OrderCustomer;
import co.parrolabs.model.Product;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDto implements Serializable {

    private UUID id;

    UUID orderCustomerId;

    ProductDto product;

    Integer quantityProduct;

}