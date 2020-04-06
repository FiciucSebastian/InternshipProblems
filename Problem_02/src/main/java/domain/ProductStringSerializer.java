package domain;

public class ProductStringSerializer implements ISerializer<Integer, Product, String> {

    @Override
    public String serialize(Product product) {
        return String.format("%d\t%s\t%f", product.getProductNumber(), product.getName(), product.getPrice());
    }

    @Override
    public Product deserialize(String line) {
        String[] parts = line.split("\\t");
        int productNumber = Integer.parseInt(parts[0]);
        String productName = parts[1];
        double price = Double.parseDouble(parts[2]);

        return new Product(productNumber, productName, price);
    }
}
