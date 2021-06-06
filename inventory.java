package CarnivAPP;

import CarnivAPP.Exceptions.InventoryException;
import CarnivAPP.Interfaces.ProductStorage;
import CarnivAPP.Interfaces.StringFormatter;
import CarnivAPP.Products.Product;

import java.text.DecimalFormat;
import java.util.HashMap;

public class Inventory implements ProductStorage {
    private HashMap<Product, Integer> Product;
    private StringFormatter stringFormatter;

    public Inventory() {
        this.Product = new HashMap<>();
        this.stringFormatter = () -> {
            final int productsSize = this.Product.size();
            final String itemString = productsSize > 1 ? productsSize + " ðñïúüíôá" : productsSize + " ðñïúüí";
            final DecimalFormat total = new DecimalFormat("####0.0");
            return String.format(" Óôï áðüèåìá õðÜñ÷åé %s, óõíïëéêÞò ôéìÞò: %s", itemString, total.format(calculateTotal()));
        };
    }

    public void addProducts(final Product Product, final int Amount) throws InventoryException {
        if (Product != null) {
            final int currentAmount = this.Product.getOrDefault(Product, 0);
            this.Product.put(Product, currentAmount + Amount);
        } else {
            throw new InventoryException("Äåí ãßíåôáé íá ðñïóèÝóåéò NULL áíôéêåßìåíá óôá ðñïúüíôá");
        }
    }

    public void removeProducts(final Product Product, final int Amount) throws InventoryException {
        if (this.Product.get(Product) > Amount) {
            this.Product.replace(Product, this.Product.get(Product) - Amount);
        } else if (this.Product.get(Product) == Amount) {
            this.Product.replace(Product, 0);
        } else {
            throw new InventoryException(String.format("Äåí ãßíåôáé íá áöáéñåèïýí %d ðåñéðôþóåéò ôïõ ðñïúüíôïò" +
                    " êáèþò õðÜñ÷ïõí ìüíï %d ðåñéðôþóåéò", Amount, this.Product.get(Product)));
        }
    }

    public HashMap<Product, Integer> getProducts() {
        return Product;
    }

    public double calculateTotal() {
        return this.Product.entrySet().
                parallelStream().
                mapToDouble(product -> product.getKey().getPrice() * product.getValue()).
                sum();
    }

    public void setStringFormatter(final StringFormatter stringFormatter) {
        this.stringFormatter = stringFormatter;
    }

    @Override
    public String toString() {
        return this.stringFormatter.formatToString();
    }
}