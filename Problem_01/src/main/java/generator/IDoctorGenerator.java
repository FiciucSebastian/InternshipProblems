package generator;

import domain.Doctor;

import java.util.List;

public interface IDoctorGenerator {
    List<Doctor> doctorsGenerator(int numberOfDoctorsToGenerate);
}
