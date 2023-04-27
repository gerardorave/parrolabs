package co.parrolabs.dto.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    private UUID id;

    @NotNull(message = "the customer must have a name")
    @NotEmpty(message = "the customer must have a name")
    private String name;

    private String phone;

    @NotNull(message = "the customer must have an email")
    @NotEmpty(message = "the customer must have an email")
    private String email;

    private UUID primaryShippingAddress;


}