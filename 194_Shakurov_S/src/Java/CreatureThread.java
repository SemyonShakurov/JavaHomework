package Java;

public class CreatureThread extends Thread {
    // Время в сек. через которое нужно прекратить тянуть тележку
    public static final int TIME_TO_STOP = 25;

    private final Cart cart; // Тележка, которую толкает существо
    private final double coef; // Коэффициент, определяющий силу существа
    private final int angle; // Угол, под которым существо тянет тележку

    public CreatureThread(Cart cart, int angle) {
        this.angle = angle;
        coef = Math.random() * 9 + 1;
        this.cart = cart;
    }

    /**
     * Существо тянет тележку
     */
    @Override
    public void run() {
        double sumTime = 0; // Прошедшее время
        while (true) {
            cart.pullTheCart(this);
            int randomTime = (int) (Math.random() * 4000 + 1000); // Время отдыха
            sumTime += randomTime / 1000.0;
            if (sumTime >= TIME_TO_STOP) // Завершение, если пройдет больше 25 секунд
                return;
            try {
                sleep(randomTime); // отдых
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public double getCoef() {
        return coef;
    }

    public int getAngle() {
        return angle;
    }
}
