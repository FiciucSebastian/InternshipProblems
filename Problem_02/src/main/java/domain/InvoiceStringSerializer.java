package domain;

import java.time.LocalDateTime;

public class InvoiceStringSerializer implements ISerializer<Integer, Invoice, String> {

    @Override
    public String serialize(Invoice invoice) {
        String dueDateString = Invoice.FORMATTER.format(invoice.getDueDate());
        if (invoice.getPayDate() == null)
            return String.format("%d\t%s\t%s\t%f\t%s\t%s\t%d", invoice.getInvoiceNumber(), invoice.getSeller(), invoice.getProducts(),
                    invoice.getTotalPrice(), dueDateString, invoice.getIsDuplicateOrNot(), invoice.getDaysRemaining());
        else{
            String payDateString = Invoice.FORMATTER.format(invoice.getPayDate());
            return String.format("%d\t%s\t%s\t%f\t%s\t%s\t%s\t%d", invoice.getInvoiceNumber(), invoice.getSeller(), invoice.getProducts(),
                    invoice.getTotalPrice(), dueDateString, payDateString, invoice.getIsDuplicateOrNot(), invoice.getDaysRemaining());
        }
    }

    @Override
    public Invoice deserialize(String line) {
        String[] parts = line.split("\\t");
        int invoiceNumber = Integer.parseInt(parts[0]);
        String seller = parts[1];
        String products = parts[2];
        float totalPrice = Float.parseFloat(parts[3]);
        LocalDateTime dueDate = LocalDateTime.parse(parts[4], Invoice.FORMATTER);

        if (parts.length < 8) {
            Invoice invoice = new Invoice(invoiceNumber, seller, products, totalPrice, dueDate);
            invoice.setIsDuplicateOrNot(parts[5]);
            invoice.setDaysRemaining(Integer.parseInt(parts[6]));
            return invoice;
        }
        else {
            LocalDateTime payDate = LocalDateTime.parse(parts[5], Invoice.FORMATTER);
            Invoice invoice = new Invoice(invoiceNumber, seller, products, totalPrice, dueDate, payDate);
            invoice.setIsDuplicateOrNot(parts[6]);
            invoice.setDaysRemaining(Integer.parseInt(parts[7]));
            return invoice;
        }
    }
}
