package hu.webuni.shippingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderItemDTO {

    private  String name;
    private Long price;
    private String category;
    private Long quantity;

}
