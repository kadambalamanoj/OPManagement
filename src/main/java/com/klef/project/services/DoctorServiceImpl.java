package com.klef.project.services;


import com.klef.project.models.Doctor;
import com.klef.project.models.Patient;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DoctorServiceImpl implements DoctorService {

    @Override
    public String adddoctor(Doctor doctor) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(doctor);
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

        return "Doctor Registered Successfully";
    }

	@Override
	public List<Doctor> viewalldoctors() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
		EntityManager em = emf.createEntityManager();
		
		Query qry = em.createQuery("select e from Doctor e");
		// e is an alias of Employee Class
		List<Doctor> doctlist = qry.getResultList();
		
	    em.close();
	    emf.close();
	    
	    return doctlist;
	}

	@Override
	public String updatedoctor(Doctor doctor) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		Doctor e = em.find(Doctor.class, doctor.getId());
		
		e.setName(doctor.getName());
		e.setSpecialization(doctor.getSpecialization());
		e.setExperience(doctor.getExperience());
		e.setEmail(doctor.getEmail());
		e.setPassword(doctor.getPassword());
		e.setContactno(doctor.getContactno());
		e.setImage(doctor.getImage());
		em.getTransaction().commit();
		em.close();
		emf.close();
		
		return "Doctor Updated Successfully";
	}

	@Override
	public Doctor viewdoctorbyid(int did) 
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
	EntityManager em = emf.createEntityManager();
	
	Doctor e = em.find(Doctor.class, did);
	
	if(e==null)
	{
		em.close();
		emf.close();
		return null;
	}
	
	em.close();
	emf.close();
	
	return e;
	}

	@Override
	public String deletedoctor(int did) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		Doctor d = em.find(Doctor.class, did);
		em.remove(d);
		em.getTransaction().commit();
		
		em.close();
		emf.close();
		
		return "Doctor Deleted Successfully";
	}

	@Override
	public Doctor checkdoctorlogin(String username, String password) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
		EntityManager em = emf.createEntityManager();
		
		// a is an alias of Admin Class
		Query qry = em.createQuery("select a from Doctor a where a.name=? and a.password=?  ");
		qry.setParameter(1, username);
		qry.setParameter(2, password);
		
        Doctor doctor = null;
        
        if(qry.getResultList().size()>0)
        {
        	doctor = (Doctor) qry.getSingleResult();
        }
		em.close();
		emf.close();
		
		return doctor;
	}

	@Override
	public boolean isContactNumberExists(String contactno) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
        EntityManager em = emf.createEntityManager();

        Query query = em.createQuery("SELECT COUNT(p) FROM Doctor p WHERE p.contactno = :contactno");
        query.setParameter("contactno", contactno);

        Long count = (Long) query.getSingleResult();

        em.close();
        emf.close();

        return count > 0;
	}

	@Override
	public boolean isEmailExists(String email) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
        EntityManager em = emf.createEntityManager();

        Query query = em.createQuery("SELECT COUNT(p) FROM Doctor p WHERE p.email = :email");
        query.setParameter("email", email);

        Long count = (Long) query.getSingleResult();

        em.close();
        emf.close();

        return count > 0;
	}

	@Override
	public List<Patient> viewAssignedPatients(int doctorId) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
        EntityManager em = emf.createEntityManager();

        List<Patient> patients = em.createQuery(
                "SELECT p FROM Patient p WHERE p.doctorId = :doctorId", Patient.class)
                .setParameter("doctorId", doctorId)
                .getResultList();

        em.close();
        emf.close();

        return patients;
    }

	@Override
	public boolean isIdExists(int did) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
        EntityManager em = emf.createEntityManager();

        Query query = em.createQuery("SELECT COUNT(p) FROM Doctor p WHERE p.id = :did");
        query.setParameter("did", did);

        Long count = (Long) query.getSingleResult();

        em.close();
        emf.close();

        return count > 0;
	}
	
	
}
