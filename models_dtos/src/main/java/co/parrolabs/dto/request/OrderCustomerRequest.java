package co.parrolabs.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.util.Pair;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCustomerRequest {

    private UUID id;

    @NotNull(message = "the order customer must have an order date")
    private String orderDate;

    private String arrivalDate;

    @NotNull(message = "the order customer must have a name")
    private UUID customerId;

    @NotNull(message = "the order customer must have a payment type id")
    private UUID paymentTypeId;

    private UUID shippingAddressId;

    @NotNull(message = "the order customer must have at least 1 product associated")
    @NotEmpty(message = "the order has include at least 1 product")
    @Valid
    private List<ProductsWithQuantitiesRequest> productsAndQuantities;

}