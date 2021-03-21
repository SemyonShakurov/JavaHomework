package Java;

public class Cart {
    private double x; // Координата x тележки
    private double y; // Координата y тележки

    public Cart() {
        x = 0.0;
        y = 0.0;
    }

    public Cart(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Толкает тележку
     * @param creatureThread - Существо (Поток), которое толкает тележку
     */
    public synchronized void pullTheCart(CreatureThread creatureThread) {
        x += creatureThread.getCoef() *
                Math.cos(Math.PI / 180 * creatureThread.getAngle());
        y += creatureThread.getCoef() *
                Math.sin(Math.PI / 180 * creatureThread.getAngle());
    }

    @Override
    public String toString() {
        String str = "Cart is in position (%.2f;%.2f)";
        return String.format(str, x, y);
    }
}
