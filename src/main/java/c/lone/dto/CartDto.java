package c.lone.dto;

import lombok.Data;

import java.util.Arrays;
import java.util.Objects;

@Data
public class CartDto {
    private long foodId;
    private String foodName;
    private int foodPrice;
    private int amount;
    private int totalPrice;

    private String[] optionName;
    private int[] optionPrice;
    private long[] optionId;

    public void totalPriceCalc() {
        int temp_Price = 0;
        if (optionPrice != null) {
            for (int price : optionPrice) {
                temp_Price += price;
            }
        }
        totalPrice = (temp_Price + foodPrice) * amount;
    }
    /*
        hashCode()와 equals()는 기존 장바구니에 있는 메뉴 중에 현재 추가하려는 메뉴가 존재하는지 확인하는 메서드
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartDto cartDto = (CartDto) o;
        return foodId == cartDto.foodId && foodPrice == cartDto.foodPrice && amount == cartDto.amount && totalPrice == cartDto.totalPrice && foodName.equals(cartDto.foodName) && Arrays.equals(optionName, cartDto.optionName) && Arrays.equals(optionPrice, cartDto.optionPrice) && Arrays.equals(optionId, cartDto.optionId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(foodId, foodName, foodPrice, amount, totalPrice);
        result = 31 * result + Arrays.hashCode(optionName);
        result = 31 * result + Arrays.hashCode(optionPrice);
        result = 31 * result + Arrays.hashCode(optionId);
        return result;
    }
}
