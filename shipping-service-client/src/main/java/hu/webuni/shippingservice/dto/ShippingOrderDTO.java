package hu.webuni.shippingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ShippingOrderDTO {

    private String userName;
    private AddressDTO deliveryAddress;
    private AddressDTO pickUpAddress;
    private List<OrderItemDTO> orderItemDTOList;

}
