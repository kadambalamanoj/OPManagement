package com.klef.project.bean;

import com.klef.project.models.Admin;
import com.klef.project.models.Doctor;
import com.klef.project.services.DoctorService;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "doctorBean", eager = true)
public class DoctorBean {
    @EJB
    private DoctorService doctorService;
    private int id;
    private String name;
    private String gender;
    private String specialization;
    private int experience;
    private String email;
    private String password;
    private String contactno;
    private String image;
    private Part imageFile;
    private List<Doctor> doctlist;

    public List<Doctor> getDoctlist() {
        return doctorService.viewalldoctors();
    }

    public void setDoctlist(List<Doctor> doctlist) {
        this.doctlist = doctlist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Part getImageFile() {
        return imageFile;
    }

    public void setImageFile(Part imageFile) {
        this.imageFile = imageFile;
    }

    public String addDoctor() 
    {
        Doctor doctor = new Doctor();
        doctor.setId(id);
        doctor.setName(name);
        doctor.setGender(gender);
        doctor.setPassword(password);
        doctor.setContactno(contactno);
        doctor.setSpecialization(specialization);
        doctor.setExperience(experience);
        doctor.setEmail(email);
        StringBuilder errorMessages = new StringBuilder();

	    boolean hasError = false;
	    
	    // Check if contact number already exists
	    if (doctorService.isContactNumberExists(contactno)) {
	        errorMessages.append("Contact number already exists. ");
	        hasError = true;
	    }

	    // Check if email already exists
	    if (doctorService.isEmailExists(email)) {
	        errorMessages.append("Email already exists. ");
	        hasError = true;
	    }
	    // If there are errors, display them
	    if (hasError) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessages.toString(), ""));
	        return null; // Stay on the same page
	    }
        // Handle file upload
        if (imageFile != null && imageFile.getSize() > 0) {
            String originalFileName = Paths.get(imageFile.getSubmittedFileName()).getFileName().toString();
            String baseName = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
            String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
            Path directory = Paths.get("D:/EP/Project/OP/src/main/webapp/images/");
            String newFileName = originalFileName;
            Path filePath = directory.resolve(newFileName);
            int counter = 1;

            while (Files.exists(filePath)) {
                newFileName = baseName + "_" + counter + extension;
                filePath = directory.resolve(newFileName);
                counter++;
            }

            try (InputStream input = imageFile.getInputStream()) {
                Files.copy(input, filePath);
                doctor.setImage("images/" + newFileName); // Save relative path
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        doctorService.adddoctor(doctor);
        return "adddoctor.jsf?faces-redirect=true";
    }
    public String update()
    {
  	  Doctor e = doctorService.viewdoctorbyid(id);
  	  if(e!=null)
  	  {
  		  //System.out.println("ID Found");
  		  Doctor doctor = new Doctor();
  		  doctor.setId(id);
  		  doctor.setName(name);
  		  doctor.setPassword(password);
  		  doctor.setContactno(contactno);
  		  doctor.setSpecialization(specialization);
  		  doctor.setExperience(experience);
  		  doctor.setEmail(email);
  		  doctor.setPassword(password);
  		 
  		  
  		if (imageFile != null && imageFile.getSize() > 0) {
            String originalFileName = Paths.get(imageFile.getSubmittedFileName()).getFileName().toString();
            String baseName = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
            String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
            Path directory = Paths.get("D:/EP/Project/OP/src/main/webapp/images/");
            String newFileName = originalFileName;
            Path filePath = directory.resolve(newFileName);
            int counter = 1;

            while (Files.exists(filePath)) {
                newFileName = baseName + "_" + counter + extension;
                filePath = directory.resolve(newFileName);
                counter++;
            }

            try (InputStream input = imageFile.getInputStream()) {
                Files.copy(input, filePath);
                doctor.setImage("images/" + newFileName); // Save relative path
            } catch (IOException ee)
            {
                ee.printStackTrace();
            }
        }
  		 System.out.println(doctor.toString());
  		  doctorService.updatedoctor(doctor);
  		  
  		  return "dupdatesuccess.jsf";
  	  }
  	  else
  	  {
  		  //System.out.println("ID Not Found");
  		  return "dupdatefail.jsf";
  	  }
    }
    public String delete(int did)
    {
      doctorService.deletedoctor(did);
      
      return "viewalldoctors.jsf";
    }
    public void validatedoctorlogin() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        
        Doctor doctor = doctorService.checkdoctorlogin(name, password);
        
        if (doctor != null) {
            HttpSession session = request.getSession();
            session.setAttribute("doctor", doctor); 
            
            // Redirect to the doctor home page
            externalContext.redirect("doctorhome.jsf");
        } else {
            externalContext.redirect("doctorloginfail.jsf");
        }
    }

}
