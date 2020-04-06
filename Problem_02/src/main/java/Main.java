import Controller.AdminController;
import Controller.InvoiceController;
import domain.*;
import generator.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.IRepository;
import repository.MainRepository;
import service.CompanyService;
import service.InvoiceService;
import service.ProductService;

import java.io.File;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        // Setup Data Sources
        File companiesFile = new File("Companies.txt");
        CompanyStringSerializer companyStringSerializer = new CompanyStringSerializer();
        IRepository<Integer, Company> companiesRepository = new MainRepository<>("Companies.txt",
                companyStringSerializer);

        File productsFile = new File("Products.txt");
        ProductStringSerializer productStringSerializer = new ProductStringSerializer();
        IRepository<Integer, Product> productsRepository = new MainRepository<>("Products.txt",
                productStringSerializer);

        File invoicesFile = new File("Invoices.txt");
        InvoiceStringSerializer invoicesStringSerializer = new InvoiceStringSerializer();
        IRepository<Integer, Invoice> invoicesRepository = new MainRepository<>("Invoices.txt",
                invoicesStringSerializer);

        // Setup Generators
        ICompanyGenerator companiesGenerator = new CompaniesGenerator();
        IProductGenerator productsGenerator = new ProductsGenerator();
        IInvoiceGenerator invoicesGenerator = new InvoicesGenerator();

        // Setup Services
        CompanyService companyService = new CompanyService(companiesRepository,companiesGenerator);
        ProductService productService = new ProductService(productsRepository,productsGenerator);
        InvoiceService invoiceService = new InvoiceService(invoicesRepository,invoicesGenerator);


        if (companiesFile.length() == 0 || productsFile.length() == 0 || invoicesFile.length() == 0){
            // Setup Invoice Controller
            InvoiceController invoiceController = new InvoiceController(companyService, productService, invoiceService);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(InvoiceController.VIEW_NAME));
            loader.setController(invoiceController);

            //If files are empty we generate the predefined number of generations
            invoiceController.generateDataIfFilesAreEmpty();

            // Showing Stage
            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
            primaryStage.setTitle(InvoiceController.VIEW_TITLE);
            primaryStage.show();
        }

        else {
            // Setup Admin Controller
            AdminController adminController = new AdminController(companyService, productService, invoiceService,
                    companiesFile, productsFile, invoicesFile);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(AdminController.VIEW_NAME));
            loader.setController(adminController);

            // Showing Stage
            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
            primaryStage.setTitle(AdminController.VIEW_TITLE);
            adminController.setAdminStage(primaryStage);
            primaryStage.show();
        }
    }
}
