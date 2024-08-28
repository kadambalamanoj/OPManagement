package com.klef.project.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="doctor_table")
public class Doctor implements Serializable {
    @Id
    @Column(name="doctor_id")
    private int id;
    
    @Column(name="doctor_name", nullable=false, length=50)
    private String name;
    
    @Column(name="doctor_gender", nullable=false, length=50)
    private String gender;
    
    @Column(name="doctor_specialization", nullable=false, length=50)
    private String specialization;
    
    @Column(name="doctor_experience", nullable=false, length=50)
    private int experience;
    
    @Column(name="doctor_email", nullable=false, length=50, unique=true)
    private String email;
    
    @Column(name="doctor_password", nullable=false, length=50)
    private String password;
    
    @Column(name="doctor_contact", nullable=false, length=20, unique=true)
    private String contactno;
    
    @Column(name="doctor_image", nullable=false, length=50)
    private String image;

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public int getExperience() { return experience; }
    public void setExperience(int experience) { this.experience = experience; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getContactno() { return contactno; }
    public void setContactno(String contactno) { this.contactno = contactno; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}
