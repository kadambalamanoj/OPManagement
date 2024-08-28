package com.klef.project.bean;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.klef.project.models.Doctor;
import com.klef.project.models.Receptionist;
import com.klef.project.services.*;
@ManagedBean(name = "receptionistBean", eager = true)
public class ReceptionistBean 
{
	@EJB
    private ReceptionistService receptionistService;
    private int id;
    private String name;
    private String gender;
    private String email;
    private String password;
    private String contactno;
    private String image;
    private Part imageFile;
    private List<Receptionist> receptionistlist;

    public List<Receptionist> getRecelist() {
        return receptionistService.viewallreceptionists();
    }

    public void setDoctlist(List<Receptionist> receptionistlist) {
        this.receptionistlist = receptionistlist;
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

    public String addReceptionist() {
        Receptionist receptionist = new Receptionist();
        receptionist.setId(id);
        receptionist.setName(name);
        receptionist.setGender(gender);
        receptionist.setPassword(password);
        receptionist.setContactno(contactno);
        receptionist.setEmail(email);
        StringBuilder errorMessages = new StringBuilder();

	    boolean hasError = false;
	    
	    // Check if contact number already exists
	    if (receptionistService.isContactNumberExists(contactno)) {
	        errorMessages.append("Contact number already exists. ");
	        hasError = true;
	    }

	    // Check if email already exists
	    if (receptionistService.isEmailExists(email)) {
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
                receptionist.setImage("images/" + newFileName); // Save relative path
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        receptionistService.addreceptionist(receptionist);
        return "addreceptionist.jsf?faces-redirect=true";
    }
    public String update()
    {
  	  Receptionist e = receptionistService.viewreceptionistid(id);
  	  if(e!=null)
  	  {
  		  //System.out.println("ID Found");
  		  Receptionist receptionist = new Receptionist();
  		receptionist.setId(id);
  		receptionist.setName(name);
  		receptionist.setPassword(password);
  		receptionist.setContactno(contactno);
  		receptionist.setEmail(email);
  		receptionist.setPassword(password);
  		 
  		  
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
                receptionist.setImage("images/" + newFileName); // Save relative path
            } catch (IOException ee)
            {
                ee.printStackTrace();
            }
        }
  		 System.out.println(receptionist.toString());
  		  receptionistService.updatereceptionist(receptionist);
  		  
  		  return "rupdatesuccess.jsf";
  	  }
  	  else
  	  {
  		  //System.out.println("ID Not Found");
  		  return "rupdatefail.jsf";
  	  }
    }
    public String delete(int rid)
    {
      receptionistService.deletereceptionist(rid);
      
      return "viewallreceptionist.jsf";
    }

    public void validatereceptionistlogin() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        
        Receptionist receptionist = receptionistService.checkreceptionistlogin(name, password);
        
        if (receptionist != null) {
            HttpSession session = request.getSession();
            session.setAttribute("receptionist", receptionist); 
            
            // Redirect to the doctor home page
            externalContext.redirect("receptionisthomepic.jsf");
        } else {
            externalContext.redirect("receptionistloginfail.jsf");
        }
    }
	
}
