package com.klef.project.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.klef.project.models.Doctor;
import com.klef.project.models.Patient;
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class PatientServiceImpl implements PatientService{

	@Override
	public String addpatient(Patient patient) {
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
	    EntityManager em = emf.createEntityManager();

	    try {
	        em.getTransaction().begin();
	        em.persist(patient);
	        em.getTransaction().commit();
	        return "Patient Registered Successfully";
	    } catch (Exception e) {
	        e.printStackTrace();
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	        if (e.getMessage().contains("contactno")) {
	            return "Phone number already exists";
	        }
	        return "An error occurred while registering the patient";
	    } finally {
	        em.close();
	        emf.close();
	    }
	}

	@Override
	public List<Patient> viewallpatients() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
		EntityManager em = emf.createEntityManager();
		
		Query qry = em.createQuery("select e from Patient e");
		// e is an alias of Employee Class
		List<Patient> patilist = qry.getResultList();
		
	    em.close();
	    emf.close();
	    
	    return patilist;
	}

	@Override
	public String updatepatient(Patient patient) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		Patient e = em.find(Patient.class, patient.getId());
		
		e.setName(patient.getName());
		e.setEmail(patient.getEmail());
		e.setAddress(patient.getAddress());
		e.setPassword(patient.getPassword());
		e.setEmergencyContact(patient.getEmergencyContact());
		e.setHeight(patient.getHeight());
		e.setWeight(patient.getWeight());
		e.setMedications(patient.getMedications());
		e.setContactno(patient.getContactno());
		e.setImage(patient.getImage());
		e.setAge(patient.getAge());
		em.getTransaction().commit();
		em.close();
		emf.close();
		
		return "Patient Updated Successfully";
	}

	@Override
	public Patient viewpatientbyid(int pid) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
		EntityManager em = emf.createEntityManager();
		
		Patient e = em.find(Patient.class, pid);
		
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
	public String deletepatient(int pid) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		Patient p = em.find(Patient.class, pid);
		em.remove(p);
		em.getTransaction().commit();
		
		em.close();
		emf.close();
		
		return "Patient Deleted Successfully";
	}

	@Override
	public Patient checkpatientlogin(String username, String password) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
		EntityManager em = emf.createEntityManager();
		
		// a is an alias of Admin Class
		Query qry = em.createQuery("select a from Patient a where a.name=? and a.password=?  ");
		qry.setParameter(1, username);
		qry.setParameter(2, password);
		
        Patient patient = null;
        
        if(qry.getResultList().size()>0)
        {
        	patient = (Patient) qry.getSingleResult();
        }
		em.close();
		emf.close();
		
		return patient;
	}
	public boolean isContactNumberExists(String contactno) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
        EntityManager em = emf.createEntityManager();

        Query query = em.createQuery("SELECT COUNT(p) FROM Patient p WHERE p.contactno = :contactno");
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

        Query query = em.createQuery("SELECT COUNT(p) FROM Patient p WHERE p.email = :email");
        query.setParameter("email", email);

        Long count = (Long) query.getSingleResult();

        em.close();
        emf.close();

        return count > 0;
	}

	@Override
	public Doctor viewAssignedDoctor(int patientId) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
        EntityManager em = emf.createEntityManager();

        Patient patient = em.find(Patient.class, patientId);
        Doctor doctor = null;
        if (patient != null) {
            doctor = patient.getDoctor(); // Use the getter method to get the Doctor object
        }

        em.close();
        emf.close();

        return doctor;
    }
	public Patient getPatientById(int id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
        EntityManager em = emf.createEntityManager();
        Patient patient = em.find(Patient.class, id);
        em.close();
        emf.close();
        return patient;
    }

	@Override
	public boolean isIdExists(int pid) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
        EntityManager em = emf.createEntityManager();

        Query query = em.createQuery("SELECT COUNT(p) FROM Patient p WHERE p.id = :pid");
        query.setParameter("pid", pid);

        Long count = (Long) query.getSingleResult();

        em.close();
        emf.close();

        return count > 0;
	}
	 public String getEmailById(int pid) {
	        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
	        EntityManager em = emf.createEntityManager();
	        Patient patient = em.find(Patient.class, pid);
	        String email = patient != null ? patient.getEmail() : null;
	        em.close();
	        emf.close();
	        return email;
	    }
	 public String getNameById(int pid) {
	        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
	        EntityManager em = emf.createEntityManager();
	        Patient patient = em.find(Patient.class, pid);
	        String name = patient != null ? patient.getName() : null;
	        em.close();
	        emf.close();
	        return name;
	    }

}
