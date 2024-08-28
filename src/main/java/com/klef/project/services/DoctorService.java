package com.klef.project.services;

import com.klef.project.models.Admin;
import com.klef.project.models.Patient;

import com.klef.project.models.Doctor;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface DoctorService {
    public String adddoctor(Doctor doctor);
    public List<Doctor> viewalldoctors();
    public String updatedoctor(Doctor doctor);
    public Doctor viewdoctorbyid(int did);
    public String deletedoctor(int did);
    public Doctor checkdoctorlogin(String username,String password);
    public boolean isContactNumberExists(String contactno); // Add this method
    public boolean isEmailExists(String email);
    public boolean isIdExists(int did);
    public List<Patient> viewAssignedPatients(int doctorId);
    
}
