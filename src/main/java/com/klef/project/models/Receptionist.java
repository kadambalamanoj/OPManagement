package com.klef.project.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="receptionist_table")
public class Receptionist implements Serializable {
    @Id
    @Column(name="receptionist_id")
    private int id;
    
    @Column(name="receptionist_name", nullable=false, length=50)
    private String name;
    
    @Column(name="receptionist_gender", nullable=false, length=50)
    private String gender;
    
    @Column(name="receptionist_email", nullable=false, length=50, unique=true)
    private String email;
    
    @Column(name="receptionist_password", nullable=false, length=50)
    private String password;
    
    @Column(name="receptionist_contact", nullable=false, length=20, unique=true)
    private String contactno;
    
    @Column(name="receptionist_image", nullable=false, length=50)
    private String image;

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getContactno() { return contactno; }
    public void setContactno(String contactno) { this.contactno = contactno; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}
