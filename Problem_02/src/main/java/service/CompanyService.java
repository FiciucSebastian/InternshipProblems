package service;

import domain.Company;
import generator.ICompanyGenerator;
import repository.IRepository;

import java.util.List;

import static utils.CollectionUtils.listFromIterable;

public class CompanyService {

    // Dependencies
    private final IRepository<Integer, Company> companyRepository;
    private final ICompanyGenerator companiesGenerator;

    public CompanyService(IRepository<Integer, Company> companyRepository, ICompanyGenerator companiesGenerator) {
        this.companyRepository = companyRepository;
        this.companiesGenerator = companiesGenerator;
    }

    public List<Company> getCompanies() { return listFromIterable(companyRepository.find()); }

    public void addCompaniesGenerated(int numberOfCompaniesToGenerate, List<String> stringsForCompany){
        List<Company> companiesGenerated = companiesGenerator.companiesGenerator(numberOfCompaniesToGenerate, stringsForCompany);

        for (Company company : companiesGenerated)
            companyRepository.add(company);
    }
}
