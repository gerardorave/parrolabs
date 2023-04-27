package co.parrolabs.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto implements Serializable {

    private UUID id;

    private String name;

    private String phone;

    private String email;

    private UUID primaryShippingAddress;

}