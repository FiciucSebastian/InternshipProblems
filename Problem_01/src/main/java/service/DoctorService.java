package service;

import domain.Doctor;
import generator.IDoctorGenerator;
import repository.IRepository;

import java.util.List;

import static utils.CollectionUtils.listFromIterable;

public class DoctorService {

    // Dependencies
    private final IRepository<Integer, Doctor> doctorRepository;
    private final IDoctorGenerator doctorsGenerator;

    public DoctorService(IRepository<Integer, Doctor> doctorRepository, IDoctorGenerator doctorsGenerator) {
        this.doctorRepository = doctorRepository;
        this.doctorsGenerator = doctorsGenerator;
    }

    //The getDoctors function return all doctors saved in repository (file/base)
    public List<Doctor> getDoctors() { return listFromIterable(doctorRepository.find()); }

    //The addDoctorsGenerated functions generates doctors with help of doctorsGenerator and add them in memory
    public void addDoctorsGenerated(int numberOfDoctorsToGenerate){
        List<Doctor> doctorsGenerated = doctorsGenerator.doctorsGenerator(numberOfDoctorsToGenerate);

        for (Doctor doctor : doctorsGenerated)
            doctorRepository.add(doctor);
    }
}
