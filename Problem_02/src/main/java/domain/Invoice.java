package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Invoice extends IdentifiableEntity<Integer> {
    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private String seller;
    private String products;
    private double totalPrice;
    private LocalDateTime dueDate;
    private LocalDateTime payDate;
    private String isDuplicateOrNot;
    private long daysRemaining;

    public Invoice(Integer invoiceNumber, String seller, String products, double totalPrice, LocalDateTime dueDate, LocalDateTime payDate) {
        super(invoiceNumber);
        this.seller = seller;
        this.products = products;
        this.totalPrice = totalPrice;
        this.dueDate = dueDate;
        this.payDate = payDate;
    }

    public Invoice(Integer invoiceNumber, String seller, String products, double totalPrice, LocalDateTime dueDate) {
        super(invoiceNumber);
        this.seller = seller;
        this.products = products;
        this.totalPrice = totalPrice;
        this.dueDate = dueDate;
        this.payDate = null;
    }

    public Integer getInvoiceNumber(){
        return getId();
    }

    public String getSeller() {
        return seller;
    }

    public String getProducts() {
        return products;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public LocalDateTime getPayDate() {
        return payDate;
    }

    public String getIsDuplicateOrNot() { return isDuplicateOrNot; }

    public long getDaysRemaining() { return daysRemaining; }

    public void setIsDuplicateOrNot(String isDuplicateOrNot) {
        this.isDuplicateOrNot = isDuplicateOrNot;
    }

    public void setDaysRemaining(long daysRemaining) {
        this.daysRemaining = daysRemaining;
    }
}
