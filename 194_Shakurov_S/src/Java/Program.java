package Java;

public class Program {
    public static void main(String[] args) {
        Cart cart = getCart(args);
        if (cart == null)
            return;
        CreatureThread swan = new CreatureThread(cart, 60);
        CreatureThread cancer = new CreatureThread(cart, 180);
        CreatureThread pike = new CreatureThread(cart, 300);
        startPullTheCart(cart, swan, cancer, pike);
        System.out.println("final: " + cart);
    }

    /**
     * Создание объекта Тележка
     * @param coordinates - аргументы командной строки с начальными координатами тележки
     * @return - объект тележки при успешном чтении арнументов, null - иначе
     */
    static Cart getCart(String[] coordinates) {
        if (coordinates.length == 0)
            return new Cart();
        else if (coordinates.length == 1) {
            double x;
            try {
                x = Double.parseDouble(coordinates[0]);
            } catch (NumberFormatException ex) {
                System.out.println("Incorrect input data");
                return null;
            }
            return new Cart(x, 0);
        } else if (coordinates.length == 2) {
            double x;
            double y;
            try {
                x = Double.parseDouble(coordinates[0]);
                y = Double.parseDouble(coordinates[1]);
            } catch (NumberFormatException ex) {
                System.out.println("Incorrect input data");
                return null;
            }
            return new Cart(x, y);
        }
        System.out.println("Incorrect input data");
        return null;
    }

    /**
     * Существа начинают тянуть тележку
     * @param cart - тележка
     * @param swan - лебедь (поток)
     * @param cancer - рак (поток)
     * @param pike - щука (поток)
     */
    static void startPullTheCart(Cart cart, CreatureThread swan,
                                 CreatureThread cancer,
                                 CreatureThread pike) {
        swan.start();
        cancer.start();
        pike.start();

        while (swan.isAlive() ||
                cancer.isAlive() ||
                pike.isAlive()) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.println(cart);
        }
    }
}
