package domain;

public class Doctor extends IdentifiableEntity<Integer> {
    private final String firstName;
    private final String lastName;
    private final int age;

    public Doctor(String firstName, String lastName, int age, Integer identificationNumber) {
        super(identificationNumber);
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public Integer getIdentificationNumber(){
        return getId();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }
}
