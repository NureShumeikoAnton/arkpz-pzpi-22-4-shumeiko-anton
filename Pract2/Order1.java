// поганий приклад
public class Order1 {
    private double itemPrice;
    private int quantity;

    public Order1(double itemPrice, int quantity) {
        this.itemPrice = itemPrice;
        this.quantity = quantity;
    }

    public double calculateTotal() {
        double basePrice = itemPrice * quantity; // Тимчасова змінна
        double discount = 0;

        if (basePrice > 100) {
            discount = basePrice * 0.1;
        }

        double finalPrice = basePrice - discount; // Тимчасова змінна
        return finalPrice;
    }
}
