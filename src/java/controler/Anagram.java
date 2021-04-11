/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import db.HibernateUtil;
import entiteti.Igra;
import entiteti.Reci;
import entiteti.Rezultat;
import entiteti.Tabela;
import entiteti.Takmicar;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
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
@Named("anagram")
public class Anagram {
    
    private int number;
    private Reci reci = new Reci();

    public Reci getReci() {
        return reci;
    }

    public void setReci(Reci reci) {
        this.reci = reci;
    }
 
    public Anagram(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
        number = (int) hs.getAttribute("broj");
    }
    
    public  int getNumber() {
        return number;
    }
 
    public void increment() {
        //System.out.println(number);
        number++;
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
        hs.setAttribute("broj", number);
        if(number==60){
            
            Tabela tabela = new Tabela();
            
            tabela.setBodovi(0);
            Date date = new Date();
            java.sql.Date datum = new java.sql.Date(date.getTime());
            tabela.setDatum(datum);
            
            Igra igra = (Igra) hs.getAttribute("igra");
            tabela.setIgra(igra.getIgra()); 
            
            Takmicar tak = (Takmicar) hs.getAttribute("user");
            tabela.setUsername(tak.getUsername());
            
            SessionFactory sessionF = HibernateUtil.getSessionFactory();
            Session session = sessionF.openSession();
            session.beginTransaction();
            
         
        
            Criteria query = session.createCriteria(Tabela.class);
            Tabela rez = (Tabela) query.add(Restrictions.eq("username", tak.getUsername())).add(Restrictions.eq("datum", datum)).uniqueResult();
            session.getTransaction().commit();
        
            session.close();
            
            if(rez==null){
            sessionF = HibernateUtil.getSessionFactory();
            session = sessionF.openSession();
            session.beginTransaction();
       
            //Save the employee in database
            session.save(tabela);
        
            session.getTransaction().commit();
            session.close();
            }
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("kraj.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(Anagram.class.getName()).log(Level.SEVERE, null, ex);
            }
            FacesContext.getCurrentInstance().responseComplete();
        }
        
    }

    public void setNumber(int number) {
        this.number = number;
    }
      
    
    
     private String odgovor = "";
     private String resenje = "";

    public String getResenje() {
        return resenje;
    }

    public void setResenje(String resenje) {
        this.resenje = resenje;
    }
     
     
     

    public  String getOdgovor() {
        return odgovor;
    }

    public void setOdgovor(String odgovor) {
        this.odgovor = odgovor;
    }

   
    
    public String kreni(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
        Reci reci = (Reci) hs.getAttribute("reci");
        Takmicar tak = (Takmicar) hs.getAttribute("user");
        resenje = reci.getOdgovor();
        if(odgovor == null ? resenje == null : odgovor.equals(resenje)) {
            SessionFactory sessionF = HibernateUtil.getSessionFactory();
            Session session = sessionF.openSession();
            session.beginTransaction();
            Criteria query = session.createCriteria(Rezultat.class);
            Rezultat rez = (Rezultat) query.add(Restrictions.eq("username", tak.getUsername())).uniqueResult();
            session.getTransaction().commit();
            session.close();
            int ana = rez.getAnagram();
            ana += 10;
            rez.setAnagram(ana);
            sessionF = HibernateUtil.getSessionFactory();
            session = sessionF.openSession();
            session.beginTransaction();
            //Save the employee in database
            session.update(rez);
            session.getTransaction().commit();
            session.close();
            
            Tabela tabela = new Tabela();
            
            tabela.setBodovi(10);
            Date date = new Date();
            java.sql.Date datum = new java.sql.Date(date.getTime());
            tabela.setDatum(datum);
            tabela.setIgra("anagram");
            tabela.setUsername(tak.getUsername());
            
            sessionF = HibernateUtil.getSessionFactory();
            session = sessionF.openSession();
            session.beginTransaction();
        
                    //Save the employee in database
            session.save(tabela);
        
            session.getTransaction().commit();
            session.close();
        } else {
            Tabela tabela = new Tabela();
            
            tabela.setBodovi(0);
            Date date = new Date();
            java.sql.Date datum = new java.sql.Date(date.getTime());
            tabela.setDatum(datum);
            tabela.setIgra("anagram");
            tabela.setUsername(tak.getUsername());
            
            SessionFactory sessionF = HibernateUtil.getSessionFactory();
            Session session = sessionF.openSession();
            session.beginTransaction();
        
                    //Save the employee in database
            session.save(tabela);
        
            session.getTransaction().commit();
            session.close();
        }
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("kraj.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(Anagram.class.getName()).log(Level.SEVERE, null, ex);
            }
             
            FacesContext.getCurrentInstance().responseComplete();
        return "kraj?faces-redirect=true";
         
    }
    
    public void dodajAna(){
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        
        //Save the employee in database
        session.save(reci);
        
        session.getTransaction().commit();
        session.close();
    }
    
}
