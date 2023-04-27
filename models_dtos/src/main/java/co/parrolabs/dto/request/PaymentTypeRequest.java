package co.parrolabs.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTypeRequest {

    private UUID id;
    @NotNull(message = "the payment type must have a name")
    private String name;

}