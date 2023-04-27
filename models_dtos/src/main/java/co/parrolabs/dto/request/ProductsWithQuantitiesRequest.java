package co.parrolabs.dto.request;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductsWithQuantitiesRequest {

    @NotNull(message = "the idProduct can't be null")
    private UUID idProduct;
    @NotNull(message = "quantity can't be null")
    @Min(value=1, message = "quantity must be greater than 0")
    @Max(value=100000000, message = "quantity must be lower than than 100000001")
    private Integer quantity;
}
