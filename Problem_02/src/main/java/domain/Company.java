package domain;

import java.util.concurrent.atomic.AtomicInteger;

public class Company extends IdentifiableEntity<Integer>{
    static final AtomicInteger NEXT_ID = new AtomicInteger(0);

    private String name;
    private String phoneNumber;

    public Company(String name, String phoneNumber) {
        super(NEXT_ID.getAndIncrement());
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
