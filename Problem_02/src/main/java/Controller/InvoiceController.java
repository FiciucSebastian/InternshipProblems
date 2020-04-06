package Controller;

import domain.Invoice;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.CompanyService;
import service.InvoiceService;
import service.ProductService;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;

public class InvoiceController {
    public static final String VIEW_NAME = "/invoices.fxml";
    public static final String VIEW_TITLE = "Invoices";

    // Dependencies
    private CompanyService companyService;
    private ProductService productService;
    private InvoiceService invoiceService;

    // Content State
    List<String> strings = new ArrayList<>();

    public InvoiceController(CompanyService companyService, ProductService productService, InvoiceService invoiceService) {
        this.companyService = companyService;
        this.productService = productService;
        this.invoiceService = invoiceService;
    }

    public void setStrings(){
        strings.add("Romanian");
        strings.add("European");
        strings.add("Water");
        strings.add("Electricity");
        strings.add("Food");
        strings.add("Incorporated");
    }

    @FXML
    private TableView<Invoice> orderedInvoicesTableView;

    @FXML
    private TableColumn<Invoice, Integer> invoiceNumberColumn;

    @FXML
    private TableColumn<Invoice, String> sellerColumn;

    @FXML
    private TableColumn<Invoice, String> productsColumn;

    @FXML
    private TableColumn<Invoice, Double> totalColumn;

    @FXML
    private TableColumn<Invoice, LocalDateTime> dueDateColumn;

    @FXML
    private TableColumn<Invoice, LocalDateTime> payDateColumn;

    @FXML
    private TableColumn<Invoice, String> duplicateColumn;

    @FXML
    private TableView<Invoice> filteredInvoicesTableView;

    @FXML
    private TableColumn<Invoice, String> filteredSellerColumn;

    @FXML
    private TableColumn<Invoice, String> filteredProductsColumn;

    @FXML
    private TableColumn<Invoice, LocalDateTime> filteredDueDateColumn;

    @FXML
    private TableColumn<Invoice, LocalDateTime> filteredPayDataColumn;

    @FXML
    private TextField filterText;

    @FXML
    public void initialize(){
        invoiceNumberColumn.setCellValueFactory(new PropertyValueFactory<>("invoiceNumber"));
        sellerColumn.setCellValueFactory(new PropertyValueFactory<>("seller"));
        productsColumn.setCellValueFactory(new PropertyValueFactory<>("products"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        payDateColumn.setCellValueFactory(new PropertyValueFactory<>("payDate"));
        duplicateColumn.setCellValueFactory(new PropertyValueFactory<>("isDuplicateOrNot"));
        loadOrderedInvoices();

        // Set double click to show the days remaining until due date for an unpaid invoice
        orderedInvoicesTableView.setRowFactory(tv -> {
            TableRow<Invoice> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Invoice rowData = row.getItem();
                    Alert alert = null;
                    if (rowData.getPayDate() == null)
                        alert = new Alert(Alert.AlertType.INFORMATION,
                                " Days remaining until due date : " + rowData.getDaysRemaining());
                    else
                        alert = new Alert(Alert.AlertType.INFORMATION, "The invoice is paid!");
                    alert.show();
                }
            });
            return row;
        });
    }

    // Load the table with ordered invoices
    private void loadOrderedInvoices() {
        orderedInvoicesTableView.getItems().setAll(invoiceService.getSortedInvoices());
    }

    // Set the select invoice method
    private Invoice getSelectedInvoice() {
        return orderedInvoicesTableView.getSelectionModel().getSelectedItem();
    }

    // Load the table with filtered invoices
    private void loadFilteredInvoices(String substring) {
        filteredInvoicesTableView.getItems().setAll(invoiceService.getInvoicesWithSubstring(substring));
    }

    // Generate new data if files are empty
    public void generateDataIfFilesAreEmpty(){
        setStrings();
        companyService.addCompaniesGenerated(24, strings);
        productService.addProductsGenerated(48);
        invoiceService.addInvoicesGenerated(50, productService.getProducts(), companyService.getCompanies());
    }


    @FXML
    void handleDuplicateInvoiceButton(ActionEvent event) {
        Invoice invoice = getSelectedInvoice();
        invoiceService.addDuplicateInvoice(invoice);

        loadOrderedInvoices();
    }

    @FXML
    void handleFilterButton(ActionEvent event) {
        filteredSellerColumn.setCellValueFactory(new PropertyValueFactory<>("seller"));
        filteredProductsColumn.setCellValueFactory(new PropertyValueFactory<>("products"));
        filteredDueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        filteredPayDataColumn.setCellValueFactory(new PropertyValueFactory<>("payDate"));

        String substring = filterText.getText();

        if (substring.length() < 3){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Substring must contain at least 3 characters!");
            alert.show();
        }
        else
            loadFilteredInvoices(substring);
    }

    @FXML
    void handlePayInvoiceButton(ActionEvent event) {
        Invoice invoice = getSelectedInvoice();

        if (invoice.getPayDate() != null){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Invoice is already paid!");
            alert.show();
        }

        else
            invoiceService.payInvoice(invoice);

        loadOrderedInvoices();
    }


}
