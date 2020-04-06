package generator;

import domain.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductsGenerator implements IProductGenerator {

    @Override
    public List<Product> productsGenerator(int numberOfProductsToGenerate){
        List<Product> products = new ArrayList<>();
        int length = 0;

        while (length < numberOfProductsToGenerate){
            boolean productExists = false;

            int productNumber = GeneratorUtils.getRandomNumberInRange(0, 10000);
            String productName = GeneratorUtils.getRandomStringGenerator(5);

            for (Product product : products){
                if (product.getProductNumber() == productNumber)
                    productExists = true;
                if (product.getName().equals(productName))
                    productExists = true;
            }

            double price = GeneratorUtils.generateRandomDouble(0.1, 999.9);

            if (!productExists){
                Product product = new Product(productNumber, productName, price);
                products.add(product);
                length++;
            }
        }

        return products;
    }
}
