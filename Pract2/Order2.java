// гарний приклад
public class Order2 {
    private double itemPrice;
    private int quantity;

    public Order2(double itemPrice, int quantity) {
        this.itemPrice = itemPrice;
        this.quantity = quantity;
    }

    public double calculateTotal() {
        return getBasePrice() - getDiscount();
    }

    private double getBasePrice() {
        return itemPrice * quantity;
    }

    private double getDiscount() {
        return getBasePrice() > 100 ? getBasePrice() * 0.1 : 0;
    }
}
