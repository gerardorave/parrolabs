package co.parrolabs.service;

import co.parrolabs.dto.AdditionalInfoForOrderResponse;
import co.parrolabs.dto.request.AdditionalInfoForOrderRequest;


public interface OrderService {

    AdditionalInfoForOrderResponse getAdditionalInfoForOrder(AdditionalInfoForOrderRequest additionalInfoForOrderRequest);

}
