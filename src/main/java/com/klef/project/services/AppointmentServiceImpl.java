package com.klef.project.services;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import com.klef.project.models.Appointment;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class AppointmentServiceImpl implements AppointmentService {

    @Override
    public String addAppointment(Appointment appointment) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(appointment);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
            emf.close();
        }

        return "Appointment Registered Successfully";
    }

    @Override
    public List<Appointment> viewAllAppointments() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
        EntityManager em = emf.createEntityManager();

        Query qry = em.createQuery("select a from Appointment a");
        List<Appointment> appointmentList = qry.getResultList();

        em.close();
        emf.close();

        return appointmentList;
    }

    @Override
    public String updateAppointment(Appointment appointment) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Appointment a = em.find(Appointment.class, appointment.getId());
        if (a != null) {
            a.setDid(appointment.getDid());
            a.setPid(appointment.getPid());
            a.setDate(appointment.getDate());
            em.getTransaction().commit();
        } else {
            em.getTransaction().rollback();
        }

        em.close();
        emf.close();

        return "Appointment Updated Successfully";
    }

    @Override
    public Appointment viewAppointmentById(int appointmentId) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
        EntityManager em = emf.createEntityManager();

        Appointment appointment = em.find(Appointment.class, appointmentId);

        em.close();
        emf.close();

        return appointment;
    }

    @Override
    public String deleteAppointment(int appointmentId) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Appointment appointment = em.find(Appointment.class, appointmentId);
        if (appointment != null) {
            em.remove(appointment);
            em.getTransaction().commit();
        } else {
            em.getTransaction().rollback();
        }

        em.close();
        emf.close();

        return "Appointment Deleted Successfully";
    }
}
