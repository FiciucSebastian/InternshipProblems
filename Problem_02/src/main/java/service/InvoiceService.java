package service;

import domain.Company;
import domain.Invoice;
import domain.Product;
import generator.IInvoiceGenerator;
import repository.IRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static utils.CollectionUtils.listFromIterable;
import static utils.CollectionUtils.streamFromIterable;

public class InvoiceService {
    // Dependencies
    private final IRepository<Integer, Invoice> invoiceRepository;
    private final IInvoiceGenerator invoicesGenerator;

    public InvoiceService(IRepository<Integer, Invoice> invoiceRepository, IInvoiceGenerator invoicesGenerator) {
        this.invoiceRepository = invoiceRepository;
        this.invoicesGenerator = invoicesGenerator;
    }

    public List<Invoice> getInvoices() { return listFromIterable(invoiceRepository.find()); }

    public void addInvoicesGenerated(int numberOfInvoicesToGenerate, List<Product> products, List<Company> companies){
        List<Invoice> invoicesGenerated = invoicesGenerator.invoicesGenerator(numberOfInvoicesToGenerate, products, companies);

        for (Invoice invoice : invoicesGenerated)
            invoiceRepository.add(invoice);
    }

    // Searches the first free identification number for an invoice
    public int getNewIdentificationNumber(){
        int newIdentificationNumber = 1;

        while (invoiceRepository.find(newIdentificationNumber) != null)
            newIdentificationNumber ++;

        return newIdentificationNumber;
    }

    // Add a duplicate invoice and setting the duplicate attribute with "D"
    public void addDuplicateInvoice(Invoice invoice){

        int newIdentificationNumber = getNewIdentificationNumber();

        Invoice duplicateInvoice = new Invoice(newIdentificationNumber, invoice.getSeller(), invoice.getProducts(), invoice.getTotalPrice(),
                invoice.getDueDate(), invoice.getPayDate());
        duplicateInvoice.setIsDuplicateOrNot("D");
        duplicateInvoice.setDaysRemaining(invoice.getDaysRemaining());

        invoiceRepository.add(duplicateInvoice);
    }

    // Filter unpaid invoices
    public List<Invoice> getUnpaidInvoices(){
        return streamFromIterable(invoiceRepository.find())
                .filter(invoice -> invoice.getPayDate() == null)
                .sorted(Comparator.comparing(Invoice::getDueDate))
                .collect(Collectors.toList());
    }

    // Filter paid invoices
    public List<Invoice> getPaidInvoices(){
        return streamFromIterable(invoiceRepository.find())
                .filter(invoice -> invoice.getPayDate() != null)
                .sorted(Comparator.comparing(Invoice::getPayDate).reversed())
                .collect(Collectors.toList());
    }

    // Sort unpaid and paid invoices in a requested order
    public List<Invoice> getSortedInvoices(){
        List<Invoice> unpaidInvoices = getUnpaidInvoices();
        List<Invoice> paidInvoices = getPaidInvoices();
        List<Invoice> sortedInvoices = new ArrayList<>();
        sortedInvoices.addAll(unpaidInvoices);
        sortedInvoices.addAll(paidInvoices);
        return sortedInvoices;
    }

    // Filter invoices with substring in the first string of company name
    public List<Invoice> getInvoicesWithSubstringInFirstStringOfCompany(String substring){
        return streamFromIterable(invoiceRepository.find())
                .filter(invoice -> invoice.getSeller().split(" ")[0].contains(substring))
                .collect(Collectors.toList());
    }

    // Filter invoices with substring in the second string of company name
    public List<Invoice> getInvoicesWithSubstringInSecondStringOfCompany(String substring){
        List<Invoice> allInvoices = getInvoices();
        List<Invoice> firstSubstringFilter = getInvoicesWithSubstringInFirstStringOfCompany(substring);
        List<Invoice> secondSubstringFilter = new ArrayList<>();

        for (Invoice invoice : allInvoices){
            if (!firstSubstringFilter.contains(invoice))
                if (invoice.getSeller().split(" ")[1].contains(substring))
                    secondSubstringFilter.add(invoice);
        }

        return secondSubstringFilter;
    }

    // Filter invoices with substring in the third string of company name
    public List<Invoice> getInvoicesWithSubstringInThirdStringOfCompany(String substring){
        List<Invoice> allInvoices = getInvoices();
        List<Invoice> firstSubstringFilter = getInvoicesWithSubstringInFirstStringOfCompany(substring);
        List<Invoice> secondSubstringFilter = getInvoicesWithSubstringInSecondStringOfCompany(substring);
        List<Invoice> thirdSubstringFilter = new ArrayList<>();

        for (Invoice invoice : allInvoices){
            if (!firstSubstringFilter.contains(invoice) && !secondSubstringFilter.contains(invoice))
                if (invoice.getSeller().split(" ").length == 3)
                    if (invoice.getSeller().split(" ")[2].contains(substring))
                    thirdSubstringFilter.add(invoice);
        }

        return thirdSubstringFilter;
    }

    // Filter invoices with substring in the product name
    public List<Invoice> getInvoicesWithSubstringInProductsName(String substring){
        List<Invoice> allInvoices = getInvoices();
        List<Invoice> firstSubstringFilter = getInvoicesWithSubstringInFirstStringOfCompany(substring);
        List<Invoice> secondSubstringFilter = getInvoicesWithSubstringInSecondStringOfCompany(substring);
        List<Invoice> thirdSubstringFilter = getInvoicesWithSubstringInThirdStringOfCompany(substring);
        List<Invoice> fourthSubstringFilter = new ArrayList<>();

        for (Invoice invoice : allInvoices){
            if (!firstSubstringFilter.contains(invoice) && !secondSubstringFilter.contains(invoice) && !thirdSubstringFilter.contains(invoice))
                if (invoice.getProducts().contains(substring))
                    fourthSubstringFilter.add(invoice);
        }

        return fourthSubstringFilter;
    }

    // Order all filtered invoices with the substring given in the requested order
    public List<Invoice> getInvoicesWithSubstring(String substring){
        List<Invoice> firstSubstringFilter = getInvoicesWithSubstringInFirstStringOfCompany(substring);
        List<Invoice> secondSubstringFilter = getInvoicesWithSubstringInSecondStringOfCompany(substring);
        List<Invoice> thirdSubstringFilter = getInvoicesWithSubstringInThirdStringOfCompany(substring);
        List<Invoice> fourthSubstringFilter = getInvoicesWithSubstringInProductsName(substring);
        List<Invoice> finalSubstringFilter = new ArrayList<>();
        int index;

        for (index = 0; index < firstSubstringFilter.size(); index++){
            if (finalSubstringFilter.size() <= 10)
                finalSubstringFilter.add(firstSubstringFilter.get(index));
            else
                break;
        }

        for (index = 0; index < secondSubstringFilter.size(); index++){
            if (finalSubstringFilter.size() <= 10)
                finalSubstringFilter.add(secondSubstringFilter.get(index));
            else
                break;
        }

        for (index = 0; index < thirdSubstringFilter.size(); index++){
            if (finalSubstringFilter.size() <= 10)
                finalSubstringFilter.add(thirdSubstringFilter.get(index));
            else
                break;
        }

        for (index = 0; index < fourthSubstringFilter.size(); index++){
            if (finalSubstringFilter.size() <= 10)
                finalSubstringFilter.add(fourthSubstringFilter.get(index));
            else
                break;
        }

        return finalSubstringFilter;
    }

    // Pay an invoice by setting pay day with the current date
    public void payInvoice(Invoice invoice){
        invoiceRepository.remove(invoice.getInvoiceNumber());

        Invoice paidInvoice = new Invoice(invoice.getInvoiceNumber(), invoice.getSeller(), invoice.getProducts(), invoice.getTotalPrice(),
                invoice.getDueDate(), LocalDateTime.now());
        paidInvoice.setIsDuplicateOrNot(invoice.getIsDuplicateOrNot());
        paidInvoice.setDaysRemaining(0);
        invoiceRepository.add(paidInvoice);
    }

}
