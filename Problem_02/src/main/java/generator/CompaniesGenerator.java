package generator;

import domain.Company;

import java.util.ArrayList;
import java.util.List;

public class CompaniesGenerator implements ICompanyGenerator {

    @Override
    public List<Company> companiesGenerator(int numberOfCompaniesToGenerate, List<String> stringsForCompany){
        List<Company> companies = new ArrayList<>();
        int length = 0;
        List<String> copyStringsForCompany;
        int companyNameLength;

        while (length < numberOfCompaniesToGenerate) {
            boolean companyExists = false;
            copyStringsForCompany = GeneratorUtils.shuffleList(stringsForCompany);
            companyNameLength = GeneratorUtils.getRandomNumberInRange(2, 3);
            String companyName;
            int phoneDigit;
            String phoneNumber = "";

            if (companyNameLength == 2)
                companyName = copyStringsForCompany.get(0) + " " + copyStringsForCompany.get(1);
            else
                companyName = copyStringsForCompany.get(0) + " " + copyStringsForCompany.get(1) + " " + copyStringsForCompany.get(2);

            for (Company company : companies){
                if (company.getName().equals(companyName))
                    companyExists = true;
            }

            for (int index = 0; index < 13; index++){
                phoneDigit = GeneratorUtils.getRandomNumberInRange(0,9);
                phoneNumber += String.valueOf(phoneDigit);
            }
            
            phoneDigit = GeneratorUtils.getRandomNumberInRange(0,9);
            phoneNumber += String.valueOf(phoneDigit);

            if (!companyExists){
                Company company = new Company(companyName, phoneNumber);
                companies.add(company);
                length++;
            }
        }

        return companies;
    }
}
