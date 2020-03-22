package admin;

import domain.Doctor;
import domain.Patient;
import domain.DailySummary;
import javafx.util.Pair;
import service.DoctorService;
import service.PatientService;


import java.util.ArrayList;
import java.util.List;

public class AdminProgram {

    // Dependencies
    private final DoctorService doctorService;
    private final PatientService patientService;

    // Content State
    private int numberOfCabinets = 4;
    private int timeOfWorkingInTotal = 780;
    private List<Doctor> doctors = new ArrayList<>();
    private List<Patient> patients = new ArrayList<>();
    private List<Patient> children = new ArrayList<>();
    private List<Patient> pupils = new ArrayList<>();
    private List<Patient> students = new ArrayList<>();
    private List<Patient> adults = new ArrayList<>();

    // Logic State
    private List<Integer> cabinetsWithNumberOfDoctors = new ArrayList<>();
    private List<Integer> numberOfTimeSpentByDoctorsPerCabinet = new ArrayList<>();
    private int doctorsPerCabinet;

    private int timeOfWorkingForADoctor;
    private int remainingDoctors;


    public AdminProgram(DoctorService doctorService, PatientService patientService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    public void setupContext(int numberOfDoctorsToGenerate) {
        doctorService.addDoctorsGenerated(numberOfDoctorsToGenerate);
        patientService.addPatientsGenerated();
        doctors = doctorService.getDoctors();
        patients = patientService.getPatients();
        children = patientService.getChildren();
        pupils = patientService.getPupils();
        students = patientService.getStudents();
        adults = patientService.getAdults();
    }

    public List<Doctor> getAvailableDoctors() {
        return doctors;
    }

    public List<Patient> getAvailablePatients(){
        return patients;
    }

    public List<Patient> getChildren(){
        return children;
    }

    public List<Patient> getPupils(){
        return pupils;
    }

    public List<Patient> getStudents(){
        return students;
    }

    public List<Patient> getAdults(){
        return adults;
    }

    //The getDoctorsPerCabinet function returns a list (with length equals numbersOfCabinets) with how many doctors are per cabinet
    public List<Integer> getDoctorsPerCabinet(){
        int index;
        int numberOfDoctors;
        List<Integer> copyCabinetsWithNumberOfDoctors = new ArrayList<>();

        doctors = doctorService.getDoctors();
        numberOfDoctors = doctors.size();
        doctorsPerCabinet = numberOfDoctors/numberOfCabinets;
        remainingDoctors = numberOfDoctors % numberOfCabinets;

        for (index = 0 ; index < numberOfCabinets ; index++){
            copyCabinetsWithNumberOfDoctors.add(doctorsPerCabinet);
            if (remainingDoctors != 0){
                copyCabinetsWithNumberOfDoctors.set(index, doctorsPerCabinet+1);
                remainingDoctors --;
            }
        }

        return copyCabinetsWithNumberOfDoctors;
    }

    //The calculateWorkingTinePerDoctor function returns a list (with length equals numbersOfCabinets) with how many hours doctors work per cabinet
    public List<Integer> calculateWorkingTimePerDoctor(){
        int index;
        List<Integer> copyNumberOfTimeSpentByDoctorsPerCabinet = new ArrayList<>();

        cabinetsWithNumberOfDoctors = getDoctorsPerCabinet();

        for (index = 0 ; index < numberOfCabinets ; index++){
            doctorsPerCabinet = cabinetsWithNumberOfDoctors.get(index);
            timeOfWorkingForADoctor = timeOfWorkingInTotal/doctorsPerCabinet;
            copyNumberOfTimeSpentByDoctorsPerCabinet.add(timeOfWorkingForADoctor);
        }

        return copyNumberOfTimeSpentByDoctorsPerCabinet;
    }

    //The numberOfPatients_TimeSpent_MoneyMadeForADoctor function returns a DailySummary for a doctor and an integer to keep track of patients treated
    public Pair<Integer, DailySummary> numberOfPatients_TimeSpent_MoneyMadeForADoctor(int numberOfPatientsTreated, int numberOfDoctorsWhichFinishedWork){
        int moneyMade = 0;
        int numberOfPatients = 0;
        int timeSpent = 0;
        int indexForPatients;
        int indexForDoctors = 0;
        int remainingDoctorsInACabinet;

        numberOfTimeSpentByDoctorsPerCabinet = calculateWorkingTimePerDoctor();
        cabinetsWithNumberOfDoctors = getDoctorsPerCabinet();

        while (indexForDoctors < numberOfCabinets){
            while (numberOfDoctorsWhichFinishedWork > 0){
                if (cabinetsWithNumberOfDoctors.get(indexForDoctors) == 0)
                    indexForDoctors++;
                else {
                    remainingDoctorsInACabinet = cabinetsWithNumberOfDoctors.get(indexForDoctors) - 1;
                    cabinetsWithNumberOfDoctors.set(indexForDoctors, remainingDoctorsInACabinet);
                    numberOfDoctorsWhichFinishedWork--;
                }
            }

            if (cabinetsWithNumberOfDoctors.get(indexForDoctors) == 0)
                indexForDoctors++;
            else{
                timeOfWorkingForADoctor = numberOfTimeSpentByDoctorsPerCabinet.get(indexForDoctors);
                remainingDoctorsInACabinet = cabinetsWithNumberOfDoctors.get(indexForDoctors) - 1;
                cabinetsWithNumberOfDoctors.set(indexForDoctors, remainingDoctorsInACabinet);
                break;
            }
        }

        patients = patientService.getPatients();
        int totalNumberOfPatients = patients.size();
        for (indexForPatients = numberOfPatientsTreated ; indexForPatients < totalNumberOfPatients ; indexForPatients++){
            if (timeOfWorkingForADoctor - (patients.get(indexForPatients).getVisitPurpose().getTime() + timeSpent) < 0) {
                DailySummary triplet = new DailySummary(numberOfPatients, timeSpent, moneyMade);
                return new Pair<>(indexForPatients,triplet);
            }
            moneyMade = moneyMade + patients.get(indexForPatients).getVisitPurpose().getMoney();
            numberOfPatients ++;
            timeSpent = timeSpent + patients.get(indexForPatients).getVisitPurpose().getTime();
        }

        DailySummary triplet = new DailySummary(numberOfPatients, timeSpent, moneyMade);
        return new Pair<>(indexForPatients,triplet);
    }

}
