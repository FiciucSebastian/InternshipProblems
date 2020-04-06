package domain;

public class Product extends IdentifiableEntity<Integer> {

    private String name;
    private double price;

    public Product(Integer productNumber, String name, double price) {
        super(productNumber);
        this.name = name;
        this.price = price;
    }

    public Integer getProductNumber(){
        return getId();
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
