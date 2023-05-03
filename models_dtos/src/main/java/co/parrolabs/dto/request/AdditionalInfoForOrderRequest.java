package co.parrolabs.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalInfoForOrderRequest {
    @NotNull(message = "productsIds can't be null")
    @NotEmpty(message = " productsIds can't be empty")
    private List<UUID> productsIds;

    private UUID shippingAddressId;
    @NotNull(message="customerId address Id can't be null")
    private UUID customerId;
    @NotNull(message="paymentTypeId address Id can't be null")
    private UUID paymentTypeId;
}
