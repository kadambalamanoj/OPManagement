package com.klef.project.services;

import java.util.List;

import com.klef.project.models.Patient;
import com.klef.project.models.Doctor;
import javax.ejb.Remote;

@Remote
public interface PatientService {
    public String addpatient(Patient patient);
    public List<Patient> viewallpatients();
    public String updatepatient(Patient patient);
    public Patient viewpatientbyid(int pid);
    public String deletepatient(int pid);
    public Patient checkpatientlogin(String username, String password);
    public boolean isContactNumberExists(String contactno); // Add this method
    public boolean isEmailExists(String email);
    public boolean isIdExists(int pid);
    public Doctor viewAssignedDoctor(int patientId);
    Patient getPatientById(int id);
    String getEmailById(int pid); // New method
    String getNameById(int pid); // New method
}
