package c.lone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CartListDto {
    private long storeId;
    private String storeName;
    int cartTotal;
    private int deliveryTip;
    List<CartDto> cartDto;
}
