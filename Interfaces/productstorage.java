package CarnivAPP.Interfaces;

import CarnivAPP.Products.Product;

public interface ProductStorage {

    void addProducts(Product product, int amount) throws Exception;

    void removeProducts(Product product, int amount) throws Exception;

    double calculateTotal();

    void setStringFormatter(StringFormatter stringFormatter);
 
}