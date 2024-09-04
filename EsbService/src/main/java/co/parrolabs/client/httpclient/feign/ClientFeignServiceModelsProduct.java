package co.parrolabs.client.httpclient.feign;



import co.parrolabs.dto.AdditionalInfoForOrderResponse;
import co.parrolabs.dto.ProductDto;
import co.parrolabs.dto.mongodb.ProductMongoDbDto;
import co.parrolabs.dto.request.AdditionalInfoForOrderRequest;
import co.parrolabs.dto.request.ProductRequest;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;


@FeignClient( name = "clientFeignServiceModelsProduct", url="${service-models.url}" )
public interface ClientFeignServiceModelsProduct
{
    /*********************************Product controller ****************************************/
    @Headers( { "Content-Type: application/json" } )
    @PostMapping( value = "/product/additionalInfoForOrder" )
    AdditionalInfoForOrderResponse getAdditionalInfoForOrder(final  AdditionalInfoForOrderRequest additionalInfoForOrderRequest );

    @GetMapping(value = "/product/{id}")
    ProductDto getProductById(@PathVariable("id") final UUID idProduct);

    @PostMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
    ProductDto saveProduct(@RequestBody ProductRequest productRequest);

    @GetMapping(value = "/product/deletedProducts", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ProductMongoDbDto> getDeletedProducts();


    @DeleteMapping(value = "/product/deleteProduct/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ProductMongoDbDto deleteProduct(@PathVariable("id") UUID id);
    @PutMapping(value = "/product/updateProduct", produces = MediaType.APPLICATION_JSON_VALUE)
    ProductDto updateProduct(@RequestBody ProductRequest productRequest);

}