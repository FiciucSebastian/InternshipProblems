package ui;

import admin.AdminProgram;
import domain.*;
import javafx.util.Pair;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class UI
{
    // Dependencies
    private final AdminProgram adminProgram;


    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public UI(AdminProgram adminProgram) {
        this.adminProgram = adminProgram;
    }

    public void start() throws IOException {
        System.out.println("How many doctors you want to be generated :\n");
        String stringNumberOfDoctorsToGenerate = reader.readLine();
        int numberOfDoctorsToGenerate;

        while(true){
            try {
                numberOfDoctorsToGenerate = Integer.parseInt(stringNumberOfDoctorsToGenerate);
                adminProgram.setupContext(numberOfDoctorsToGenerate);
                break;
            } catch (NumberFormatException e) {
                System.out.println("How many doctors you want to be generated :\n");
                stringNumberOfDoctorsToGenerate = reader.readLine();
            }
        }

        while (true){
            System.out.println("1. Showing all doctors\n2. Showing all patients\n" +
                    "3. Showing number of patients by age\n4. Showing number of patients and money for a doctor and " +
                    "showing number of patients that weren't treated\n5. Exit\n" +
                    "P.S: To modify the number of doctors generated change the program arguments with which number you want");

            String option = reader.readLine();

            if (option.equals("1")) {
                List<Doctor> doctors = adminProgram.getAvailableDoctors();

                for (Doctor doctor : doctors){
                    System.out.println(doctor.getFirstName() + " " + doctor.getLastName() +
                            " " + doctor.getAge() + " " + doctor.getIdentificationNumber() + "\n");
                }
            }

            else if (option.equals("2")){
                List<Patient> patients = adminProgram.getAvailablePatients();

                for (Patient patient : patients){
                    System.out.println(patient.getFirstName() + " " + patient.getLastName() +
                            " " + patient.getAge() + " " + patient.getVisitPurpose() + "\n");
                }
            }

            else if (option.equals("3")){
                List<Patient> children = adminProgram.getChildren();
                List<Patient> pupils = adminProgram.getPupils();
                List<Patient> students = adminProgram.getStudents();
                List<Patient> adults = adminProgram.getAdults();

                System.out.println("Children (0-1): " + children.size() + " patients\n");
                System.out.println("Pupil (1-7): " + pupils.size() + " patients\n");
                System.out.println("Student (7-18): " + students.size() + " patients\n");
                System.out.println("Adults (>18): " + adults.size() + " patients\n");
            }

            else if (option.equals("4")){
                int numberOfPatientsTreated = 0;
                List<Doctor> doctors = adminProgram.getAvailableDoctors();
                List<Patient> patients = adminProgram.getAvailablePatients();
                int size = patients.size();
                int remainingDoctorsInACabinet = 0;

                System.out.println("The list with doctor's work at final of the day:");

                for (Doctor doctor : doctors){
                    Pair<Integer, DailySummary> result =
                            adminProgram.numberOfPatients_TimeSpent_MoneyMadeForADoctor(numberOfPatientsTreated, remainingDoctorsInACabinet);
                    DailySummary numberOfPatients_TimeSpent_MoneyMade = result.getValue();

                        System.out.println(doctor.getFirstName() + ", " + doctor.getLastName() +
                                " - " + doctor.getIdentificationNumber() + ": " + numberOfPatients_TimeSpent_MoneyMade.getNumberOfPatients() +
                                " patients, " + numberOfPatients_TimeSpent_MoneyMade.getTime() + " minutes, " +
                                numberOfPatients_TimeSpent_MoneyMade.getMoney() + " RON");

                    numberOfPatientsTreated = result.getKey();
                    remainingDoctorsInACabinet++;
                }

                if(numberOfPatientsTreated < size){
                    System.out.println("The list with the untreated patients:");
                    for (int iterator = numberOfPatientsTreated ; iterator < size ; iterator++){
                        Patient patient = patients.get(iterator);
                        System.out.println(patient.getFirstName() + ", " + patient.getLastName() + ", " +
                                patient.getAge() + " years, " + patient.getVisitPurpose());
                    }
                }
            }

            else if (option.equals("5"))
                break;

            else
                System.out.println("Your input is not an option!!!");
        }
    }
}
