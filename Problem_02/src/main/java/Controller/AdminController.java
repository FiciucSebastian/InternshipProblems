package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import service.CompanyService;
import service.InvoiceService;
import service.ProductService;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminController {

    public static final String VIEW_NAME = "/userChoice.fxml";
    public static final String VIEW_TITLE = "Data Procedure";


    // Dependencies
    private CompanyService companyService;
    private ProductService productService;
    private InvoiceService invoiceService;
    private File companiesFile;
    private File productsFile;
    private File invoicesFile;

    // Content State
    List<String> strings = new ArrayList<>();
    private Stage adminStage;

    public AdminController(CompanyService companyService, ProductService productService, InvoiceService invoiceService,
                           File companiesFile, File productsFile, File invoicesFile) {
        this.companyService = companyService;
        this.productService = productService;
        this.invoiceService = invoiceService;
        this.companiesFile = companiesFile;
        this.productsFile = productsFile;
        this.invoicesFile = invoicesFile;
    }

    public void setAdminStage(Stage stage){
        adminStage = stage;
    }

    // Set the predefined string
    public void setStrings(){
        strings.add("Romanian");
        strings.add("European");
        strings.add("Water");
        strings.add("Electricity");
        strings.add("Food");
        strings.add("Incorporated");
    }

    public void emptyFiles() throws IOException {
        new FileOutputStream(companiesFile).close();
        new FileOutputStream(productsFile).close();
        new FileOutputStream(invoicesFile).close();
    }


    @FXML
    private TextField stringsTextFields;

    @FXML
    private TextField productsTextField;

    @FXML
    private TextField invoicesTextField;

    @FXML
    private TextField companiesTextField;

    @FXML
    void handleGenerateNewDataButton(ActionEvent event) throws IOException {
        setStrings();
        String string = stringsTextFields.getText();
        String[] stringsSeparated = string.split(" ");
        if (stringsSeparated.length == 8){
            strings.clear();
            strings.addAll(Arrays.asList(stringsSeparated));
        }

        Integer numberOfCompaniesGenerated;
        Integer numberOfProductsGenerated;
        Integer numberOfInvoicesGenerated;

        try{
            numberOfCompaniesGenerated = Integer.parseInt(companiesTextField.getText());
            numberOfProductsGenerated = Integer.parseInt(productsTextField.getText());
            numberOfInvoicesGenerated = Integer.parseInt(invoicesTextField.getText());
        }catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Numbers for generations must be integers!");
            alert.show();
            return;
        }

        emptyFiles();

        companyService.addCompaniesGenerated(numberOfCompaniesGenerated, strings);
        productService.addProductsGenerated(numberOfProductsGenerated);
        invoiceService.addInvoicesGenerated(numberOfInvoicesGenerated, productService.getProducts(), companyService.getCompanies());


        InvoiceController invoiceController = new InvoiceController(companyService, productService, invoiceService);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(InvoiceController.VIEW_NAME));
        loader.setController(invoiceController);

        Scene scene = null;
        Stage stage = new Stage();
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        stage.setScene(scene);
        stage.setTitle(InvoiceController.VIEW_TITLE);

        adminStage.hide();
        stage.show();
    }

    @FXML
    void handlePersistedDataButton(ActionEvent event) {
        InvoiceController invoiceController = new InvoiceController(companyService, productService, invoiceService);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(InvoiceController.VIEW_NAME));
        loader.setController(invoiceController);

        Scene scene = null;
        Stage stage = new Stage();
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        stage.setScene(scene);
        stage.setTitle(InvoiceController.VIEW_TITLE);

        adminStage.hide();
        stage.show();
    }

}
