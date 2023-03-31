package c.lone.controller;

import c.lone.dto.CartDto;
import c.lone.dto.CartListDto;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CartApiController {

    //장바구니 추가
    @PostMapping("/api/cart")
    public CartListDto addCart(CartDto cartDto, long storeId, String storeName, int deliveryTip, HttpSession session) {

        //기존 Session에서 저장된 장바구니 목록을 가져옴
        CartListDto cartListDto = (CartListDto) session.getAttribute("cartList");

        cartDto.totalPriceCalc();
        //Session에 저장된 장바구니 목록이 없을 시
        if (cartListDto == null) {
            List<CartDto> newCart = new ArrayList<>();
            newCart.add(cartDto);
            new CartListDto(storeId, storeName, cartDto.getTotalPrice(), deliveryTip, newCart);
        } else {
            //저장된 장바구니 목록이 있을시
            List<CartDto>
        }
    }

    //장바구니 전체 삭제
    @DeleteMapping("/api/cart")
    public void deleteAllCart(HttpSession session) {
        session.removeAttribute("cartList");
    }


    //상세페이지에 접근했을 시 장바구니 목록
    @GetMapping("/api/cart")
    public CartListDto cartList(HttpSession session) {
        CartListDto cartList = (CartListDto) session.getAttribute("cartList");
        if (cartList != null) {
            return cartList;
        }
        return null;
    }

    //장바구니 1개 삭제
    @DeleteMapping("/api/cart/{index}")
    public CartListDto deleteOneCart(@PathVariable int index, HttpSession session) {
        CartListDto cartList = (CartListDto) session.getAttribute("cartList");
        if (cartList == null) {
            return null;
        }
        int cartTotal = cartList.getCartTotal();
        List<CartDto> cart = cartList.getCartDto();
        int removeCartPrice = cart.get(index).getTotalPrice();

        cart.remove(index);

        if (cart.size() == 0) {
            session.removeAttribute("cartList");
            return null;
        }

        cartTotal -= removeCartPrice;
        cartList.setCartTotal(cartTotal);
        return cartList;
    }







































}
