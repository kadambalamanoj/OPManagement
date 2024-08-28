package com.klef.project.services;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.klef.project.models.Doctor;
import com.klef.project.models.Receptionist;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ReceptionistServiceImpl implements ReceptionistService
{

	@Override
	public String addreceptionist(Receptionist receptionist) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(receptionist);
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

        return "Receptionist Registered Successfully";
	}

	@Override
	public List<Receptionist> viewallreceptionists() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
		EntityManager em = emf.createEntityManager();
		
		Query qry = em.createQuery("select e from Receptionist e");
		// e is an alias of Employee Class
		List<Receptionist> rectlist = qry.getResultList();
		
	    em.close();
	    emf.close();
	    
	    return rectlist;
	}

	@Override
	public String updatereceptionist(Receptionist receptionist) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
  Receptionist e = em.find(Receptionist.class, receptionist.getId());
		
		e.setName(receptionist.getName());
		
		e.setEmail(receptionist.getEmail());
		e.setPassword(receptionist.getPassword());
		e.setContactno(receptionist.getContactno());
		e.setImage(receptionist.getImage());
		em.getTransaction().commit();
		em.close();
		emf.close();
		
		return "Receptionist Updated Successfully";
	}

	@Override
	public Receptionist viewreceptionistid(int rid) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
		EntityManager em = emf.createEntityManager();
		
		Receptionist e = em.find(Receptionist.class, rid);
		
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
	public String deletereceptionist(int rid) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		Receptionist r = em.find(Receptionist.class, rid);
		em.remove(r);
		em.getTransaction().commit();
		
		em.close();
		emf.close();
		
		return "Receptionist Deleted Successfully";
	}

	@Override
	public Receptionist checkreceptionistlogin(String username, String password) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
		EntityManager em = emf.createEntityManager();
		
		Query qry = em.createQuery("select a from Receptionist a where a.name=? and a.password=?  ");
		qry.setParameter(1, username);
		qry.setParameter(2, password);
		
        Receptionist receptionist = null;
        
        if(qry.getResultList().size()>0)
        {
        	receptionist = (Receptionist) qry.getSingleResult();
        }
		em.close();
		emf.close();
		
		return receptionist;
	}

	@Override
	public boolean isContactNumberExists(String contactno) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
        EntityManager em = emf.createEntityManager();

        Query query = em.createQuery("SELECT COUNT(p) FROM Receptionist p WHERE p.contactno = :contactno");
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

        Query query = em.createQuery("SELECT COUNT(p) FROM Receptionist p WHERE p.email = :email");
        query.setParameter("email", email);

        Long count = (Long) query.getSingleResult();

        em.close();
        emf.close();

        return count > 0;
	}

}
