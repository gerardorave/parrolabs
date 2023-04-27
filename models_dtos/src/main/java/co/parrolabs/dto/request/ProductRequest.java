package co.parrolabs.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private UUID id;

    @NotNull(message = "the customer product have a name")
    private String name;

    private String description;

    @NotNull(message = "the customer must have a price")
    private Double price;

    private Double weight;

}
