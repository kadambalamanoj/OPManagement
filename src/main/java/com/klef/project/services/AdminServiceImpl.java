package com.klef.project.services;

import javax.persistence.EntityManagerFactory;

import com.klef.project.models.Admin;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class AdminServiceImpl  implements AdminService
{

	@Override
	public Admin checkadminlogin(String username, String password) 
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
		EntityManager em = emf.createEntityManager();
		
		// a is an alias of Admin Class
		Query qry = em.createQuery("select a from Admin a where a.username=? and a.password=?  ");
		qry.setParameter(1, username);
		qry.setParameter(2, password);
		
        Admin admin = null;
        
        if(qry.getResultList().size()>0)
        {
        	admin = (Admin) qry.getSingleResult();
        }
		em.close();
		emf.close();
		
		return admin;
	}

	@Override
	public long doctcount() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
		EntityManager em = emf.createEntityManager();
		 Query qry=em.createQuery("select count(*) from Doctor");
		 List list=qry.getResultList();
		 long count =(long)list.get(0);
		return count;
	}

	@Override
	public long reccount() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
		EntityManager em = emf.createEntityManager();
		 Query qry=em.createQuery("select count(*) from Receptionist");
		 List list=qry.getResultList();
		 long count =(long)list.get(0);
		return count;
	}

	@Override
	public long patcount() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("OP");
		EntityManager em = emf.createEntityManager();
		 Query qry=em.createQuery("select count(*) from Patient");
		 List list=qry.getResultList();
		 long count =(long)list.get(0);
		return count;
	}

}
