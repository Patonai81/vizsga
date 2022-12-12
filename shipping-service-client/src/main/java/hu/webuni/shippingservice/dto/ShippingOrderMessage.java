package hu.webuni.shippingservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ShippingOrderMessage {

    private String orderStatus;
    private String externalId;


}
