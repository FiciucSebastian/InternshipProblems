package generator;

import domain.Company;

import java.util.List;

public interface ICompanyGenerator {
    List<Company> companiesGenerator(int numberOfCompaniesToGenerate, List<String> stringsForCompany);
}
