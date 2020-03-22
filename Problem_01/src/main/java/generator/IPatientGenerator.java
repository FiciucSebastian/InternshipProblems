package generator;

import domain.Patient;

import java.util.List;

public interface IPatientGenerator {
    List<Patient> patientsGenerator();
    List<Patient> firstPatientsGenerator();
}
