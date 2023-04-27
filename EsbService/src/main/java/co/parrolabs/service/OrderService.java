package co.parrolabs.service;

import co.parrolabs.dto.AdditionalInfoForOrderResponse;
import co.parrolabs.dto.request.AdditionalInfoForOrderRequest;
import reactor.core.publisher.Mono;


public interface OrderService {

    Mono<AdditionalInfoForOrderResponse> getAdditionalInfoForOrder(AdditionalInfoForOrderRequest additionalInfoForOrderRequest);

}
