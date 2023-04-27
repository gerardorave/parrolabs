package co.parrolabs.builder.product;

import co.parrolabs.dto.ProductDto;
import co.parrolabs.dto.request.ProductRequest;
import co.parrolabs.model.Product;

import java.util.UUID;

public final class ProductBuilder {

    public static ProductDto productDto() {
        return ProductDto.builder()
                .id(UUID.randomUUID())
                .description("Product with description")
                .name("product name")
                .price(12.9)
                .weight(2.1)
                .build();
    }

    public static Product product() {
        Product p = new Product();
        p.setId(UUID.randomUUID());
        p.setDescription("Product with description");
        p.setName("product name");
        p.setPrice(12.9);
        p.setWeight(2.1);
        return p;
    }

    public static ProductRequest productRequest(ProductDto productDto) {
        return ProductRequest.builder()
                .id(productDto.getId())
                .description(productDto.getDescription())
                .name(productDto().getName())
                .price(productDto.getPrice())
                .weight(productDto().getWeight())
                .build();

    }
}
