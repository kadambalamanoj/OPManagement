package com.klef.project.services;

import java.util.List;
import javax.ejb.Remote;
import com.klef.project.models.Appointment;

@Remote
public interface AppointmentService {
    public String addAppointment(Appointment appointment);
    public List<Appointment> viewAllAppointments();
    public String updateAppointment(Appointment appointment);
    public Appointment viewAppointmentById(int appointmentId);
    public String deleteAppointment(int appointmentId);
}
