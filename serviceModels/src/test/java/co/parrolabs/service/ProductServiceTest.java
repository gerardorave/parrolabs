package co.parrolabs.service;

import co.parrolabs.ApplicationConfig;
import co.parrolabs.dto.ProductDto;
import co.parrolabs.dto.request.ProductRequest;
import co.parrolabs.model.Product;
import co.parrolabs.builder.product.ProductBuilder;
import co.parrolabs.repository.jpa.ProductRepository;
import co.parrolabs.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = ApplicationConfig.class)
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private ProductRepository repository;
    @Mock
    private ModelMapper mapper;



    @Test
    public void saveProduct() {
        Product product = ProductBuilder.product();
        ProductDto productDto = ProductBuilder.productDto();
        Mockito.when(mapper.map(product, ProductDto.class))
                .thenReturn(productDto);
        ProductRequest request = ProductBuilder.productRequest(productDto);
        Mockito.when(mapper.map(request, Product.class))
                .thenReturn(product);
        Mockito.when(repository.save(Mockito.any(Product.class))).thenReturn(product);

        productService.saveProduct(request);

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        Mockito.verify(repository).save(productCaptor.capture());

        assertEquals(product.getName(), productCaptor.getValue().getName());
        assertEquals(product.getPrice(), productCaptor.getValue().getPrice());
    }
}
