package co.parrolabs.builder.shippingaddress;

import co.parrolabs.dto.ProductDto;
import co.parrolabs.dto.ShippingAddressDto;
import co.parrolabs.dto.request.ProductRequest;
import co.parrolabs.model.Product;
import co.parrolabs.model.ShippingAddress;

import java.util.UUID;

public class ShippingAddressBuilder {


    public static ShippingAddressDto addressDto() {
        return ShippingAddressDto.builder()
                .id(UUID.randomUUID())
                .city("Cali")
                .country("Colombia")
                .number("Av 1223")
                .zip_code("700045")
                .build();
    }

    public static ShippingAddress address() {
        ShippingAddress a = new ShippingAddress();
        a.setId(UUID.randomUUID());
        a.setCity("Cali");
        a.setCountry("Colombia");
        a.setNumber("Av 1223");
        a.setZip_code("700045");
        return a;
    }

}
