package domain;

public class CompanyStringSerializer implements ISerializer<Integer, Company, String> {

    @Override
    public String serialize(Company company) {
        return String.format("%s\t%s", company.getName(), company.getPhoneNumber());
    }

    @Override
    public Company deserialize(String line) {
        String[] parts = line.split("\\t");
        String name = parts[0];
        String phoneNumber = parts[1];

        return new Company(name, phoneNumber);
    }
}
