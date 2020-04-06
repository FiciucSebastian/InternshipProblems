package generator;

import domain.Product;

import java.util.List;

public interface IProductGenerator {
    List<Product> productsGenerator(int numberOfProductsToGenerate);
}
