package co.parrolabs.httpclient.feign;



import co.parrolabs.dto.AdditionalInfoForOrderResponse;
import co.parrolabs.dto.request.AdditionalInfoForOrderRequest;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient( name = "clientFeign", url="${esb-service.url}" )
public interface ClientFeign
{

    @Headers( { "Content-Type: application/json" } )
    @PostMapping( value = "/product/additionalInfoForOrder" )
    AdditionalInfoForOrderResponse getAdditionalInfoForOrder(final  AdditionalInfoForOrderRequest additionalInfoForOrderRequest );


}