package CarnivAPP;

import CarnivAPP.Exceptions.BasketException;
import CarnivAPP.Interfaces.ProductStorage;
import CarnivAPP.Interfaces.StringFormatter;
import CarnivAPP.Products.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static CarnivAPP.Extras.Utils.iterateSimultaneously;

public class Basket implements ProductStorage {
    private HashMap<Product, Integer> products;
    private StringFormatter stringFormatter;

    public Basket() {
        this.products = new HashMap<>();
        this.stringFormatter = () -> {
            final int basketSize = this.products.size();
            final String itemString = basketSize > 1 ? basketSize + "products" : basketSize + " product.";
            return String.format("To kalathi exei %s", itemString);
        };
    }

    public void addProducts(final Product product, final int Amount) throws BasketException {
        if (product != null) {
            final int currentAmount = this.products.getOrDefault(product, 0);
            this.products.put(product, currentAmount + Amount);
        } else {
            throw new BasketException("Den mporeis na prostheseis kena antikeimena sto kalathi");
        }
    } 

    public void removeProducts(final Product product, final int Amount) throws BasketException {
        if (this.products.get(product) > Amount) {
            this.products.replace(product, this.products.get(product) - Amount);
        } else if (this.products.get(product) == Amount) {
            this.products.replace(product, 0);
        } else {
            throw new BasketException(String.format("Den ginetai na ginei remove %d instances apo products afou yparxoyn mono %d instances", Amount, this.products.get(product)));
        }
    }

    public HashMap<Product, Integer> getProducts() {
        return products;
    }

    public double calculateTotal() {
        return this.products.entrySet().
                parallelStream().
                mapToDouble(product -> product.getKey().getPrice() * product.getValue()).
                sum();
    }

    public void setStringFormatter(final StringFormatter stringFormatter) {
        this.stringFormatter = stringFormatter;
    }

    public ArrayList<String> toDBFormat() {
       
        final ArrayList<String> result = new ArrayList<>();
        final String Names = this.products.entrySet().
                parallelStream().
                map(p -> p.getKey().getName()).
                collect(Collectors.joining(","));
        final String Amounts = this.products.entrySet().
                parallelStream().
                map(p -> p.getValue().toString()).
                collect(Collectors.joining(","));
        result.add(Names);
        result.add(Amounts);

        return result;
    }

    @Deprecated
    public void restoreFromDB(final String productsName, final String productsAmount) {
        final List<String> Names = Arrays.asList(productsName.split(","));
        final List<String> Amounts = Arrays.asList(productsAmount.split(","));

        iterateSimultaneously(Names, Amounts, (String Name, String Amount) -> {
            try {
                addProducts(new Product(Name, 0.150, 0.8), Integer.parseInt(Amount));
            } catch (BasketException ex) {
                ex.printStackTrace();
            }
        });
    }

    @Override
    public String toString() {
        return stringFormatter.formatToString();
    }		
}