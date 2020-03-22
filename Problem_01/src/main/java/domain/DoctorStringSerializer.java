package domain;

public class DoctorStringSerializer implements ISerializer<Integer, Doctor, String> {

    @Override
    public String serialize(Doctor doctor) {
        return String.format("%s\t%s\t%d\t%d", doctor.getFirstName(), doctor.getLastName(), doctor.getAge(), doctor.getIdentificationNumber());
    }

    @Override
    public Doctor deserialize(String line) {
        String[] parts = line.split("\\t");
        String firstName = parts[0];
        String lastName = parts[1];
        int age = Integer.parseInt(parts[2]);
        int identificationNumber = Integer.parseInt(parts[3]);

        return new Doctor(firstName, lastName, age, identificationNumber);
    }
}
