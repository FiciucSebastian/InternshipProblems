package domain;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

public class Patient extends IdentifiableEntity<Integer> implements Comparable<Patient> {

    static final AtomicInteger NEXT_ID = new AtomicInteger(0);

    private final String firstName;
    private final String lastName;
    private final int age;
    private final VisitPurpose visitPurpose;

    public Patient(String firstName, String lastName, int age, VisitPurpose visitPurpose) {
        super(NEXT_ID.getAndIncrement());
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.visitPurpose = visitPurpose;
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

    public VisitPurpose getVisitPurpose() {
        return visitPurpose;
    }


    @Override
    public int compareTo(Patient patient) {
        return Comparator.comparing(Patient::getVisitPurpose)
                .compare(this, patient);
    }
}
