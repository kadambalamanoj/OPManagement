package com.klef.project.bean;
import com.klef.project.models.Admin;
import com.klef.project.models.Doctor;
import com.klef.project.models.Patient;
import com.klef.project.services.DoctorService;
import com.klef.project.services.PatientService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
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
@ManagedBean(name = "patientBean", eager = true)
public class PatientBean 
{
	   @EJB
	    private PatientService patientService;
	    private int id;
	    private String name;
	    private String gender;
	    private String email;
	    private int age;
	    private double height;
	    private double weight;
	    private String medications;
	    private String emergencyContact;
	    private String address;
	    private String password;
	    private String contactno;
	    private String image;
	    private Part imageFile;
	    private List<Patient> patilist;
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
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
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
		public double getHeight() {
			return height;
		}
		public void setHeight(double height) {
			this.height = height;
		}
		public double getWeight() {
			return weight;
		}
		public void setWeight(double weight) {
			this.weight = weight;
		}
		public String getMedications() {
			return medications;
		}
		public void setMedications(String medications) {
			this.medications = medications;
		}
		public String getEmergencyContact() {
			return emergencyContact;
		}
		public void setEmergencyContact(String emergencyContact) {
			this.emergencyContact = emergencyContact;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
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
		public List<Patient> getPatilist() {
			return patientService.viewallpatients();
		}
		public void setPatilist(List<Patient> patilist) {
			this.patilist = patilist;
		}
		public String addPatient() {
		    StringBuilder errorMessages = new StringBuilder();

		    boolean hasError = false;
		    
		    // Check if contact number already exists
		    if (patientService.isContactNumberExists(contactno)) {
		        errorMessages.append("Contact number already exists. ");
		        hasError = true;
		    }

		    // Check if email already exists
		    if (patientService.isEmailExists(email)) {
		        errorMessages.append("Email already exists. ");
		        hasError = true;
		    }

		    // If there are errors, display them
		    if (hasError) {
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessages.toString(), ""));
		        return null; // Stay on the same page
		    }

		    // If no errors, proceed with adding the patient
		    Patient patient = new Patient();
		    patient.setName(name);
		    patient.setAddress(address);
		    patient.setEmergencyContact(emergencyContact);
		    patient.setHeight(height);
		    patient.setWeight(weight);
		    patient.setMedications(medications);
		    patient.setGender(gender);
		    patient.setPassword(password);
		    patient.setContactno(contactno);
		    patient.setAge(age);
		    patient.setEmail(email);

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
		            patient.setImage("images/" + newFileName); // Save relative path
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }

		    patientService.addpatient(patient);
		    return "addpatient.jsf?faces-redirect=true";
		}


	    public String update()
	    {
	  	  Patient e = patientService.viewpatientbyid(id);
	  	  if(e!=null)
	  	  {
	  		  //System.out.println("ID Found");
	  		  Patient patient = new Patient();
                patient.setId(id);
		        patient.setName(name);
		        patient.setAddress(address);
		        patient.setEmergencyContact(emergencyContact);
		        patient.setHeight(height);
		        patient.setWeight(weight);
		        patient.setMedications(medications);
		        patient.setPassword(password);
		        patient.setContactno(contactno);
		        patient.setAge(age);
		        patient.setEmail(email);
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
	                patient.setImage("images/" + newFileName); // Save relative path
	            } catch (IOException ee)
	            {
	                ee.printStackTrace();
	            }
	        }
	  		 System.out.println(patient.toString());
	  		  patientService.updatepatient(patient);
	  		  
	  		  return "pupdatesuccess.jsf";
	  	  }
	  	  else
	  	  {
	  		  //System.out.println("ID Not Found");
	  		  return "pupdatefail.jsf";
	  	  }
	    }
	    public String delete(int pid)
	    {
	      patientService.deletepatient(pid);
	      
	      return "viewallpatients.jsf";
	    }
	    public void validatepatientlogin() throws IOException {
	        FacesContext facesContext = FacesContext.getCurrentInstance();
	        ExternalContext externalContext = facesContext.getExternalContext();
	        
	        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
	        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
	        
	        Patient patient = patientService.checkpatientlogin(name, password);
	        
	        if (patient != null) {
	            HttpSession session = request.getSession();
	            session.setAttribute("patient", patient); 
	            
	            // Redirect to the patient home page
	            externalContext.redirect("patienthome.jsf");
	        } else {
	            externalContext.redirect("patientloginfail.jsf");
	        }
	    }
}
