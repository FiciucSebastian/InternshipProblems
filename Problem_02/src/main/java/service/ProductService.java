package service;

import domain.Product;
import generator.IProductGenerator;
import repository.IRepository;

import java.util.List;

import static utils.CollectionUtils.listFromIterable;

public class ProductService {
    // Dependencies
    private final IRepository<Integer, Product> productRepository;
    private final IProductGenerator productsGenerator;

    public ProductService(IRepository<Integer, Product> productRepository, IProductGenerator productsGenerator) {
        this.productRepository = productRepository;
        this.productsGenerator = productsGenerator;
    }

    public List<Product> getProducts() { return listFromIterable(productRepository.find()); }

    public void addProductsGenerated(int numberOfProductsToGenerate){
        List<Product> productsGenerated = productsGenerator.productsGenerator(numberOfProductsToGenerate);

        for (Product product : productsGenerated)
            productRepository.add(product);
    }
}
