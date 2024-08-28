package com.klef.project.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="patient_table")
public class Patient implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="patient_id")
    private int id;
    
    @Column(name="patient_name", nullable=false, length=50)
    private String name;
    
    @Column(name="patient_age", nullable=false)
    private double age;
    
    @Column(name="patient_gender", nullable=false, length=50)
    private String gender;
    
    @Column(name="patient_height",nullable=false)
    private double height;
    
    @Column(name="patient_weight",nullable=false)
    private double weight;
    
    @Column(name="patient_medications",nullable=false,length=30)
    private String medications;
    
    @Column(name="patient_contact", nullable=false, length=20, unique=true)
    private String contactno;
    
    @Column(name="patient_emergencyContact", nullable=false, length=20)
    private String emergencyContact;
    
    @Column(name="patient_address",nullable=false,length=90)
    private String address;
    
    
    @Column(name="patient_email", nullable=false, length=50, unique=true)
    private String email;
    
    @Column(name="patient_password", nullable=false, length=50)
    private String password;
    
    
    
    @Column(name="patient_image", nullable=false, length=50)
    private String image;

    @ManyToOne
    @JoinColumn(name = "doctor_id")  // This should match your database column
    private Doctor doctor;
    // Getters and Setters
    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
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

	public double getAge() {
		return age;
	}

	public void setAge(double age) {
		this.age = age;
	}
	
}
