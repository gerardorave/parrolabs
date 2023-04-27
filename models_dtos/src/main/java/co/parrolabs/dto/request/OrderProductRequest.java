package co.parrolabs.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductRequest {

    private UUID id;

    @NotNull(message = "the order product must have a order customer id")
    UUID orderCustomerId;

    @NotNull(message = "the order product must have a order product id")
    UUID productId;

    @NotNull(message = "the order product must have at least quantity 1")
    Integer quantityProduct;
}