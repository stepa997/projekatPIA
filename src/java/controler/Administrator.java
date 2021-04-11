/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import db.HibernateUtil;
import entiteti.Igra;
import entiteti.Takmicar;
import java.util.ArrayList;
import java.util.Date;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Djole
 */

@ManagedBean
@ApplicationScoped
public class Administrator {
    
    private Date date= new Date();
    private java.sql.Date datum = new java.sql.Date(date.getTime());
    private String igra;
    private String por;

    public String getPor() {
        return por;
    }

    public void setPor(String por) {
        this.por = por;
    }
    
    

    public String getIgra() {
        return igra;
    }

    public void setIgra(String igra) {
        this.igra = igra;
    } 

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public java.sql.Date getDatum() {
        return datum;
    }

    public void setDatum(java.sql.Date datum) {
        this.datum = datum;
    }
    
    public void unesi() {
        datum = new java.sql.Date(date.getTime());
        //System.out.println(datum);
        //System.out.println(date);
        
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        
        Criteria query = session.createCriteria(Igra.class);
        Igra i = (Igra) query.add(Restrictions.eq("datum", datum)).uniqueResult();
        session.getTransaction().commit();
        session.close();
        
        
        
        if(i!=null && "t".equals(i.getStatus())){
            //nista ne radi, ne moze
            por = "NE MOZETE UNETI ZA OVAJ DAN";
        } else {
        Igra unesi = new Igra();
        unesi.setDatum(datum);
        unesi.setIgra(igra);
        unesi.setStatus("f");
        
        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
        
        //Save the employee in database
        if(i!=null) session.update(unesi);
        else session.save(unesi);
        
        session.getTransaction().commit();
        session.close();
        
        por = "USPESNO STE DODALI DAN I IGRU";
        }
    }
    
    public void prih(String username){
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        
        Criteria query = session.createCriteria(Takmicar.class);
        Takmicar user = (Takmicar) query.add(Restrictions.eq("username", username)).uniqueResult();
        session.getTransaction().commit();
        session.close();
        
        if(user!=null) {
        user.setStatus("n");
        
        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
        
        //Save the employee in database
        session.update(user);
        
        session.getTransaction().commit();
        session.close();
        }
            ArrayList<Takmicar> lista = new ArrayList<>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false); 
            
            sessionF = HibernateUtil.getSessionFactory();
            session = sessionF.openSession();
            session.beginTransaction();
        
            query = session.createCriteria(Takmicar.class);
            lista = (ArrayList<Takmicar>) query.add(Restrictions.eq("status", "c")).list();
            session.getTransaction().commit();
            session.close();
            
            hs.setAttribute("lista", lista);
    }
    
    public void odb(String username){
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        
        Criteria query = session.createCriteria(Takmicar.class);
        Takmicar user = (Takmicar) query.add(Restrictions.eq("username", username)).uniqueResult();
        session.getTransaction().commit();
        session.close();
        
        if(user!=null) {
        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
        
        //Save the employee in database
        session.delete(user);
        
        session.getTransaction().commit();
        session.close();
        }
    }
    
    
}
