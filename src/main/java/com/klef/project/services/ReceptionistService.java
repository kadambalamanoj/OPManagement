package com.klef.project.services;

import java.util.List;

import com.klef.project.models.Doctor;
import com.klef.project.models.Receptionist;
import javax.ejb.Remote;
@Remote
public interface ReceptionistService {
	public String addreceptionist(Receptionist receptionist);
    public List<Receptionist> viewallreceptionists();
    public String updatereceptionist(Receptionist receptionist);
    public Receptionist viewreceptionistid(int rid);
    public String deletereceptionist(int rid);
    public Receptionist checkreceptionistlogin(String username,String password);
    public boolean isContactNumberExists(String contactno); // Add this method
    public boolean isEmailExists(String email);
}
