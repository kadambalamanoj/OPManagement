package com.klef.project.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.util.Date;
import java.util.List;


import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.klef.project.services.AppointmentService;
import com.klef.project.services.PatientService;
import com.klef.project.models.Appointment;

import com.klef.project.models.Appointment;
import com.klef.project.services.AppointmentService;
import com.klef.project.services.DoctorService;
import com.klef.project.services.PatientService;

@ManagedBean(name = "appointmentBean", eager = true)
public class AppointmentBean {
    @EJB
    private DoctorService doctorService;
    @EJB
    private PatientService patientService;
    @EJB
    private AppointmentService appointmentService;
    
    private int id;
    private int did;
    private int pid;
    private Date date;
    private List<Appointment> aplist;

    @PostConstruct
    public void init() {
        aplist = appointmentService.viewAllAppointments();
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String addAppointment() {
        StringBuilder errorMessages = new StringBuilder();
        boolean hasError = false;

        // Check if doctor ID exists
        if (!doctorService.isIdExists(did)) {
            errorMessages.append("Doctor ID ").append(did).append(" does not exist. ");
            hasError = true;
        }

        // Check if patient ID exists
        if (!patientService.isIdExists(pid)) {
            errorMessages.append("Patient ID ").append(pid).append(" does not exist. ");
            hasError = true;
        }

        // If there are errors, add error messages and stay on the same page
        if (hasError) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessages.toString(), ""));
            return null; // Stay on the same page
        }

        // If no errors, proceed to add the appointment
        Appointment appointment = new Appointment();
        appointment.setId(id);
        appointment.setPid(pid);
        appointment.setDid(did);
        appointment.setDate(date);

        appointmentService.addAppointment(appointment);
        return "scheduleAppointment1.jsf?faces-redirect=true";
    }

    public List<Appointment> getAplist() {
        return aplist;
    }

    public void setAplist(List<Appointment> aplist) {
        this.aplist = aplist;
    }

    public String delete(Integer id) {
        appointmentService.deleteAppointment(id);
        return "pviewscheduleappointment.jsf";
    }
    public String sendReminderEmail(int pid) {
        try {
            String patientEmail = patientService.getEmailById(pid);
            String patientName = patientService.getNameById(pid);

            // Setting up SMTP server properties
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            // Email authentication credentials
            String username =  "manojkadambala2005@gmail.com";
            String password = "wgkdfzapygmzqakm";

            // Creating a new session with an authenticator
            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            // Create a new email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(patientEmail));
            message.setSubject("Appointment Reminder");
            message.setText(String.format("Dear %s,\n\nThis is a reminder for your appointment scheduled for tomorrow.\n\nBest regards,\nYour Clinic", patientName));

            // Send the email
            Transport.send(message);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Reminder email sent successfully to " + patientName));
        } catch (MessagingException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error sending email: " + e.getMessage(), null));
        }

        return null; // Stay on the same page
    }
}
