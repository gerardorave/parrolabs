package co.parrolabs.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDtoResponse implements Serializable {

    List<OrderCustomerDto> orders;
}
