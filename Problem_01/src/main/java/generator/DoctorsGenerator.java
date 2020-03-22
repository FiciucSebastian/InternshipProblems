package generator;

import domain.Doctor;

import java.util.ArrayList;
import java.util.List;

public class DoctorsGenerator implements IDoctorGenerator {

    @Override
    public List<Doctor> doctorsGenerator(int numberOfDoctorsToGenerate){
        List<Doctor> doctors = new ArrayList<>();
        int length = 0;

        if(numberOfDoctorsToGenerate < 8)
            numberOfDoctorsToGenerate = 8;

        while (length < numberOfDoctorsToGenerate) {
            String firstName = GeneratorUtils.getRandomStringGenerator(3);
            String lastName = GeneratorUtils.getRandomStringGenerator(2);
            int age = GeneratorUtils.getRandomNumberInRange(30, 65);
            int identificationNumber = GeneratorUtils.getRandomNumberInRange(1000, 9999);

            boolean doctorExists = false;

            for (Doctor doctor : doctors){
                if (doctor.getIdentificationNumber() == identificationNumber)
                    doctorExists = true;
            }

            if (doctorExists == false){
                Doctor doctor = new Doctor(firstName, lastName, age, identificationNumber);
                doctors.add(doctor);
                length++;
            }
        }

        return doctors;
    }
}
