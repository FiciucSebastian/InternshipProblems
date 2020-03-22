package generator;

import domain.Patient;
import domain.VisitPurpose;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class PatientsGenerator implements IPatientGenerator {

    @Override
    public List<Patient> patientsGenerator(){
        List<Patient> patients = firstPatientsGenerator();
        int length = patients.size();

        while (length < 100) {
            String firstName = GeneratorUtils.getRandomStringGenerator(5);
            String lastName = GeneratorUtils.getRandomStringGenerator(4);
            int age = GeneratorUtils.getRandomNumberInRange(0, 85);
            VisitPurpose visitPurpose = VisitPurpose.getRandomVisitPurpose();

            Patient patient = new Patient(firstName, lastName, age, visitPurpose);
            patients.add(patient);
            length++;
        }

        return patients;
    }


    // FixMe: To work in not controlling first 4 patients
    @Override
    public List<Patient> firstPatientsGenerator(){
        List<Patient> patients = new ArrayList<>();
        List<Pair<Integer,Integer>> ageCategory = GeneratorUtils.makingAgeCategoryList();
        int size = ageCategory.size();
        for (int index = 0 ; index < size ; index++){
            String firstName = GeneratorUtils.getRandomStringGenerator(5);
            String lastName = GeneratorUtils.getRandomStringGenerator(4);
            VisitPurpose visitPurpose;
            int age = GeneratorUtils.getRandomNumberInRange(ageCategory.get(index).getKey(), ageCategory.get(index).getValue());
            if(index >= VisitPurpose.values().length) {
                visitPurpose = VisitPurpose.getRandomVisitPurpose();
            }
            else {
                visitPurpose = VisitPurpose.values()[index];
            }

            Patient patient = new Patient(firstName, lastName, age, visitPurpose);
            patients.add(patient);
        }

        return patients;
    }
}
