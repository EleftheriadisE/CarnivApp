package CarnivAPP.Products;
 
import CarnivAPP.Interfaces.StringFormatter;

public class Product {
    private String Name;
    private String Size;
    private double Weight;
    private double Price;
    private StringFormatter stringFormatter;

    public Product(final String Name, final String Size, final double Weight, final double Price) {
        this.Name = Name;
        this.Size = Size;
        this.Weight = Weight;
        this.Price = Price;
        this.stringFormatter = () -> "Το προϊόν: " + this.Name + " έχει τιμή " + this.Price + ".";
    }

    public String getName() {
        return Name;
    }
    
    public String getSize() {
        return Size;
    }
        
    public void setSize(final String Size) {
        this.Size = Size;
    }
    
    public double getWeight() {
        return Weight;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(final double Price) {
        this.Price = Price;
    }

    public void setStringFormatter(final StringFormatter stringFormatter) {
        this.stringFormatter = stringFormatter;
    }

    @Override
    public String toString() {
        return stringFormatter.formatToString();
    }
}