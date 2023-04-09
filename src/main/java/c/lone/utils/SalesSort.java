package c.lone.utils;

import c.lone.dto.CartDto;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SalesSort {
    public SalesSort(List<CartDto> menuList, String sort) {
        sort(menuList, sort);
    }
    /*
        return -1 일 경우 오름차순 정렬, return +1 일 경우 내림차순 정렬
     */

    private void sort(List<CartDto> menuList, String sort) {
        Collections.sort(menuList, new Comparator<CartDto>() {
            @Override
            public int compare(CartDto o1, CartDto o2) {
                // 가격 오름차순
                if ("price".equals(sort)) {
                    return o1.getTotalPrice() - o2.getTotalPrice();
                }

                // 가격 내림차순
                if ("priceR".equals(sort)) {
                    return o2.getTotalPrice() - o1.getTotalPrice();
                }

                // 기본 정렬 이름 오름차순
                else {
                    String name1 = o1.getFoodName();
                    String name2 = o2.getFoodName();

                    if (name1.compareTo(name2) == 0) {
                        String[] option1 = o1.getOptionName();
                        String[] option2 = o2.getOptionName();

                        /*
                            option1이 null인 경우 option2보다 앞에 위치해야 하므로, 'compare()' 메소드에서 '1'을 반환. 반대로 option2가 null인 경우 'option1'보다 뒤에 위치해야 하므로 '-1'을 반환
                            옵션이 있는 요소가 앞으로 가게 됨

                            1을 반환하면 첫번째 인자로 전달된 객체가 두번째 인자로 전달된 객체보다 큰 값으로 판단되어 두 객체가 위치를 바꾸어 첫번째 인자로 전달된 객체가 두번째 인자로 전달된 객체보다 뒤에 위치하게 된다.

                            -1을 반환하면 첫번째 인자로 전달된 객체가 두번째 인자로 전달된 객체보다 작은 값으로 판단되어 두 객체가 위치를 바꾸어 정렬될 때 첫번째 인자로 전달된 객체가 두번째 인자로 전달된 객체보다 앞에 위치하게 됨
                         */
                        if (option1 == null) {
                            return 1;
                        }
                        if (option2 == null) {
                            return -1;
                        }
                        return option1[0].compareTo(option2[0]);
                    }
                    if ("nameR".equals(sort)) {
                        return o2.getFoodName().compareTo(o1.getFoodName());
                    } else {
                        return o1.getFoodName().compareTo(o2.getFoodName());
                    }
                }
            }
        });
    }
}



















