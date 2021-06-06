package CarnivAPP;

import CarnivAPP.Interfaces.StringFormatter;

public class Order {
    private Basket basket;
    private String address;
    private StringFormatter stringFormatter;

    public Order(final Basket basket, final String address) {
        this.basket = basket;
        this.address = address;
        this.stringFormatter = () -> {
            final int basketSize = basket.getProducts().size();
            final String itemString = basketSize > 1 ? basketSize + " Ðñïúüíôá" : basketSize + " Ðñïúüí";
            return String.format("Ç ðáñáããåëßá ðåñéÝ÷åé %s êáé èá ðáñáäïèåß óôçí ïäü %s", itemString, address);
        };
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(final Basket basket) {
        this.basket = basket;
    }

    public void removeBasket() {
        this.basket = null;
    }

    public String getAddress() {
        return address;
    }

    public void changeAddress(final String address) {
        this.address = address;
    }

    public void setStringFormatter(final StringFormatter stringFormatter) {
        this.stringFormatter = stringFormatter;
    }

    @Override
    public String toString() {
        return this.stringFormatter.formatToString();
    }
} 