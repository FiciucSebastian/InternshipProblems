package generator;

import domain.Company;
import domain.Invoice;
import domain.Product;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class InvoicesGenerator implements IInvoiceGenerator {

    @Override
    // Generate the number of invoices needed. First we generate invoices for every company and after it is all random
    public List<Invoice> invoicesGenerator(int numberOfInvoicesToGenerate, List<Product> products, List<Company> companies){
        // Content State
        List<Invoice> invoices = new ArrayList<>();
        List<Product> productsShuffled;
        List<Company> companiesShuffled = GeneratorUtils.shuffleList(companies);

        int length = 0;
        int percent = 10;
        int invoiceNumber;
        String seller;
        int numberOfProducts;
        double price;
        LocalDateTime dueDate;
        LocalDateTime payDate;


        while (length < companiesShuffled.size()){
            boolean invoiceExists = false;
            String duplicate = "-";
            String productsForInvoice = "";
            invoiceNumber = GeneratorUtils.getRandomNumberInRange(1, 10000);
            seller = companiesShuffled.get(length).getName();
            productsShuffled = GeneratorUtils.shuffleList(products);
            numberOfProducts = GeneratorUtils.getRandomNumberInRange(1, 3);
            
            if (numberOfProducts == 1) {
                productsForInvoice += productsShuffled.get(0).getName();
                price = productsShuffled.get(0).getPrice();
            }
            else if (numberOfProducts == 2){
                productsForInvoice += productsShuffled.get(0).getName() + " ";
                productsForInvoice += productsShuffled.get(1).getName();
                price = productsShuffled.get(0).getPrice() + productsShuffled.get(1).getPrice();
            }
            else {
                productsForInvoice += productsShuffled.get(0).getName() + " ";
                productsForInvoice += productsShuffled.get(1).getName() + " ";
                productsForInvoice += productsShuffled.get(2).getName();
                price = productsShuffled.get(0).getPrice() + productsShuffled.get(1).getPrice() + productsShuffled.get(2).getPrice();
            }

            dueDate = GeneratorUtils.generateRandomDate(LocalDateTime.now(), 6);

            for (Invoice invoice : invoices){
                if (invoice.getInvoiceNumber() == invoiceNumber)
                    invoiceExists = true;
            }

            if (length % percent != 0){
                if (!invoiceExists) {
                    payDate = dueDate;
                    while (payDate.equals(dueDate))
                        payDate = GeneratorUtils.generateRandomDate(LocalDateTime.now(), -5);
                    Invoice invoice = new Invoice(invoiceNumber, seller, productsForInvoice, price, dueDate, payDate);
                    invoice.setDaysRemaining(0);
                    invoice.setIsDuplicateOrNot(duplicate);
                    invoices.add(invoice);
                    length++;
                }
            }

            else {
                if (!invoiceExists) {
                Invoice invoice = new Invoice(invoiceNumber, seller, productsForInvoice, price, dueDate);
                invoice.setDaysRemaining(LocalDateTime.now().until(dueDate, ChronoUnit.DAYS));
                invoice.setIsDuplicateOrNot(duplicate);
                invoices.add(invoice);
                length++;
            }
            }

        }

        while (length < numberOfInvoicesToGenerate){
            boolean invoiceExists = false;
            String duplicate = "-";
            String productsForInvoice = "";
            invoiceNumber = GeneratorUtils.getRandomNumberInRange(1, 10000);
            companiesShuffled = GeneratorUtils.shuffleList(companies);
            seller = companiesShuffled.get(0).getName();
            productsShuffled = GeneratorUtils.shuffleList(products);
            numberOfProducts = GeneratorUtils.getRandomNumberInRange(1, 3);

            if (numberOfProducts == 1) {
                productsForInvoice += productsShuffled.get(0).getName();
                price = productsShuffled.get(0).getPrice();
            }
            else if (numberOfProducts == 2){
                productsForInvoice += productsShuffled.get(0).getName() + " ";
                productsForInvoice += productsShuffled.get(1).getName();
                price = productsShuffled.get(0).getPrice() + productsShuffled.get(1).getPrice();
            }
            else {
                productsForInvoice += productsShuffled.get(0).getName() + " ";
                productsForInvoice += productsShuffled.get(1).getName() + " ";
                productsForInvoice += productsShuffled.get(2).getName();
                price = productsShuffled.get(0).getPrice() + productsShuffled.get(1).getPrice() + productsShuffled.get(2).getPrice();
            }

            dueDate = GeneratorUtils.generateRandomDate(LocalDateTime.now(), 6);

            for (Invoice invoice : invoices){
                if (invoice.getInvoiceNumber() == invoiceNumber)
                    invoiceExists = true;
            }

            if (length % percent != 0){
                if (!invoiceExists) {
                    payDate = dueDate;
                    while (payDate.equals(dueDate))
                        payDate = GeneratorUtils.generateRandomDate(dueDate, -5);

                    for (Invoice invoice : invoices){
                        if (invoice.getSeller().equals(seller) && invoice.getProducts().equals(productsForInvoice))
                            if (invoice.getTotalPrice() == price)
                                if (invoice.getDueDate().isEqual(dueDate) && invoice.getPayDate().isEqual(payDate)) {
                                    duplicate = "D";
                                    break;
                                }
                    }

                    Invoice invoice = new Invoice(invoiceNumber, seller, productsForInvoice, price, dueDate, payDate);
                    invoice.setDaysRemaining(0);
                    invoice.setIsDuplicateOrNot(duplicate);
                    invoices.add(invoice);
                    length++;
                }
            }

            else {
                if (!invoiceExists) {
                    for (Invoice invoice : invoices){
                        if (invoice.getSeller().equals(seller) && invoice.getProducts().equals(productsForInvoice))
                            if (invoice.getTotalPrice() == price)
                                if (invoice.getDueDate().isEqual(dueDate)) {
                                    duplicate = "D";
                                    break;
                                }
                    }

                    Invoice invoice = new Invoice(invoiceNumber, seller, productsForInvoice, price, dueDate);
                    invoice.setIsDuplicateOrNot(duplicate);
                    invoice.setDaysRemaining(LocalDateTime.now().until(dueDate, ChronoUnit.DAYS));
                    invoices.add(invoice);
                    length++;
                }
            }

        }

        return invoices;
    }
}
