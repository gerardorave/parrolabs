package co.parrolabs.dto;

import co.parrolabs.model.Customer;
import co.parrolabs.model.OrderProduct;
import co.parrolabs.model.PaymentType;
import co.parrolabs.model.ShippingAddress;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCustomerDto implements Serializable {

    private UUID id;

    private ZonedDateTime orderDate;

    private ZonedDateTime arrivalDate;

    private CustomerDto Customer;

    private PaymentTypeDto paymentType;

    private UUID customerShippingAddressId;

    private List<OrderProductDto> orderProducts;

}