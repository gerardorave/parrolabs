package co.parrolabs.httpclient.webfluxwebclient.model;

import co.parrolabs.dto.CustomerDto;
import co.parrolabs.dto.OrderCustomerDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerWithOrderDto {
    private CustomerDto customerDto;
    private OrderCustomerDto orderCustomerDto;
}