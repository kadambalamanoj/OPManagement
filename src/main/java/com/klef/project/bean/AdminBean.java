package com.klef.project.bean;

import java.io.IOException;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.klef.project.models.Admin;
import com.klef.project.services.AdminService;

@ManagedBean(name = "adminbean", eager = true)
public class AdminBean {
  
  @EJB(lookup="")
  private AdminService adminService;
  
  private String username;
  private String password;

  // Getters and setters for username and password

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  // Existing method
  public void validateadminlogin() throws IOException {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ExternalContext externalContext = facesContext.getExternalContext();
    
    HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
    HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
    
    Admin admin = adminService.checkadminlogin(username, password);
    
    if (admin != null) {
      HttpSession session = request.getSession();
      session.setAttribute("admin", admin); // admin session attribute
      
      response.sendRedirect("adminhomepic.jsf");
    } else {
      response.sendRedirect("adminloginfail.jsf");
    }
  }

  // Method to get the count of doctors
  public long getDoctorCount() {
    return adminService.doctcount();
  }
  public long getReceptionistCount()
  {
	  return adminService.reccount();
  }
  public long getPatientCount()
  {
	  return adminService.patcount();
  }
}
