package service;

import domain.Patient;
import generator.IPatientGenerator;
import repository.IRepository;

import java.util.List;
import java.util.stream.Collectors;

import static utils.CollectionUtils.listFromIterable;
import static utils.CollectionUtils.streamFromIterable;

public class PatientService {

    // Dependencies
    private final IRepository<Integer, Patient> patientRepository;
    private final IPatientGenerator patientsGenerator;

    public PatientService(IRepository<Integer, Patient> patientRepository, IPatientGenerator patientsGenerator) {
        this.patientRepository = patientRepository;
        this.patientsGenerator = patientsGenerator;
    }

    //The getDoctors function return all doctors saved in repository (file/base)
    public List<Patient> getPatients() {
        return listFromIterable(patientRepository.find());
    }

    //The addPatientsGenerated functions generates patients with help of patientsGenerator and add them in memory
    public void addPatientsGenerated(){
        List<Patient> patientsGenerated = patientsGenerator.patientsGenerator();

        for (Patient patient : patientsGenerated)
            patientRepository.add(patient);
    }

    //The getChildren function return all patients which have the age between 0 and 1 (filter function is used)
    public List<Patient> getChildren() {
        return streamFromIterable(patientRepository.find())
                .filter(patient -> patient.getAge() >= 0)
                .filter(patient -> patient.getAge() <= 1)
                .collect(Collectors.toList());
    }

    //The getPupils function return all patients which have the age between 1 and 7 (filter function is used)
    public List<Patient> getPupils() {
        return streamFromIterable(patientRepository.find())
                .filter(patient -> patient.getAge() > 1)
                .filter(patient -> patient.getAge() <= 7)
                .collect(Collectors.toList());
    }

    //The getStudents function return all patients which have the age between 7 and 18 (filter function is used)
    public List<Patient> getStudents() {
        return streamFromIterable(patientRepository.find())
                .filter(patient -> patient.getAge() > 7).
                        filter(patient -> patient.getAge() <= 18)
                .collect(Collectors.toList());
    }

    //The getAdults function return all patients which have the age over 18 (filter function is used)
    public List<Patient> getAdults() {
        return streamFromIterable(patientRepository.find())
                .filter(patient -> patient.getAge() > 18)
                .collect(Collectors.toList());
    }

}
