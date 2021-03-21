package Java;

import org.junit.jupiter.api.Test;

class CreatureThreadTest {

    @Test
    void run() {
        Cart cart = new Cart();
        System.out.println(cart);
        CreatureThread creature = new CreatureThread(cart, 0);
        creature.start();
        try {
            creature.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println(cart);
    }
}