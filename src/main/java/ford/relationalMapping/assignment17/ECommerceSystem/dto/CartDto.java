package ford.relationalMapping.assignment17.ECommerceSystem.dto;

public class CartDto {
    private int totalItems;
    private double totalPrice;

    public CartDto(){

    }

    public CartDto(int totalItems, double totalPrice) {
        this.totalItems = totalItems;
        this.totalPrice = totalPrice;
    }
    public int getTotalItems() {
        return totalItems;
    }
    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
