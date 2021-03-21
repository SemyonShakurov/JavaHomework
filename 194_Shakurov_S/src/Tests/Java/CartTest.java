package Java;

import org.junit.jupiter.api.Test;

class CartTest {

    @Test
    void pullTheCart() {
        Cart cart = new Cart(25.7, 30.17);
        System.out.println(cart);
        CreatureThread creature = new CreatureThread(cart, 20);

        cart.pullTheCart(creature);
        System.out.println(cart);
    }
}