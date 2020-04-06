package generator;

import domain.Company;
import domain.Invoice;
import domain.Product;

import java.util.List;

public interface IInvoiceGenerator {
    List<Invoice> invoicesGenerator(int numberOfInvoicesToGenerate, List<Product> products, List<Company> companies);
}
