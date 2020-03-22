import admin.AdminProgram;
import domain.Doctor;
import domain.DoctorStringSerializer;
import domain.Patient;
import domain.PatientStringSerializer;
import generator.DoctorsGenerator;
import generator.IDoctorGenerator;
import generator.IPatientGenerator;
import generator.PatientsGenerator;
import repository.IRepository;
import repository.MainRepository;
import service.DoctorService;
import service.PatientService;
import ui.UI;

import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {
        // Setup Data Sources
        DoctorStringSerializer doctorStringSerializer = new DoctorStringSerializer();
        IRepository<Integer, Doctor> doctorRepository = new MainRepository<>("Doctors.txt",
                doctorStringSerializer);
        doctorRepository.empty();

        PatientStringSerializer patientStringSerializer = new PatientStringSerializer();
        IRepository<Integer, Patient> patientRepository = new MainRepository<>("Patients.txt",
                patientStringSerializer);
        patientRepository.empty();

        // Setup Generators
        IDoctorGenerator doctorsGenerator = new DoctorsGenerator();
        IPatientGenerator patientsGenerator = new PatientsGenerator();

        // Setup Services
        DoctorService doctorService = new DoctorService(doctorRepository,doctorsGenerator);
        PatientService patientService = new PatientService(patientRepository,patientsGenerator);

        // Setup Program
        AdminProgram adminProgram = new AdminProgram(doctorService, patientService);

        // Start UI
        UI ui = new UI(adminProgram);

        ui.start();
    }
}
