package domain;

public class PatientStringSerializer implements ISerializer<Integer, Patient, String> {

    @Override
    public String serialize(Patient patient) {
        return String.format("%s\t%s\t%d\t%s", patient.getFirstName(), patient.getLastName(), patient.getAge(), patient.getVisitPurpose().toString());
    }

    @Override
    public Patient deserialize(String line) {
        String[] parts = line.split("\\t");
        String firstName = parts[0];
        String lastName = parts[1];
        int age = Integer.parseInt(parts[2]);
        VisitPurpose reason = VisitPurpose.valueOf(parts[3]);

        return new Patient(firstName, lastName, age, reason);
    }
}
