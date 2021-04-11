/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import db.HibernateUtil;
import entiteti.Igra;
import entiteti.Pojam;
import entiteti.Reci;
import entiteti.Rezultat;
import entiteti.Tabela;
import entiteti.Takmicar;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
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
public class Geografija {
    
    private Pojam pojam;
    private Pojam ispis;
    private String slovo;

    public String getSlovo() {
        return slovo;
    }

    public void setSlovo(String slovo) {
        this.slovo = slovo;
    }

    
    
    public Pojam getIspis() {
        return ispis;
    }

    public void setIspis(Pojam ispis) {
        this.ispis = ispis;
    }

    public Pojam getPojam() {
        return pojam;
    }

    public void setPojam(Pojam pojam) {
        this.pojam = pojam;
    }
    
    public Geografija(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
        pojam = (Pojam) hs.getAttribute("pojam");
        ispis = (Pojam) hs.getAttribute("ispis");
        slovo = (String) hs.getAttribute("slovo");
    }
    
    public void radi(){
        System.out.println(pojam.getDrzava());
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
        hs.setAttribute("pojam", pojam);
        slovo = (String) hs.getAttribute("slovo");
        
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
       
        Criteria query = session.createCriteria(Pojam.class);
        Pojam igra = (Pojam) query.add(Restrictions.eq("slovo", slovo)).uniqueResult();
        session.getTransaction().commit();
        
        session.close();
        
        Takmicar tak = (Takmicar) hs.getAttribute("user");
        
        System.out.println(tak.getUsername());
        
        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
       
        query = session.createCriteria(Rezultat.class);
        Rezultat rez = (Rezultat) query.add(Restrictions.eq("username", tak.getUsername())).uniqueResult();
        session.getTransaction().commit();
        
        session.close();
        
        System.out.println(igra.getDrzava());
        System.out.println(tak.getUsername());
        
        int s = 0;
        if(igra!=null) {
            fc = FacesContext.getCurrentInstance();
            hs = (HttpSession) fc.getExternalContext().getSession(false);
            
            int pre = rez.getGeografija();
            
                if(pojam.getDrzava() == null ? igra.getDrzava() == null : pojam.getDrzava().equals(igra.getDrzava())){
                    s = rez.getGeografija();
                    s += 2;
                    rez.setGeografija(s);
                    pojam.setDrzava(null);
                    hs.setAttribute("pojam", pojam);
                    
                    sessionF = HibernateUtil.getSessionFactory();
                    session = sessionF.openSession();
                    session.beginTransaction();
                        //Save the employee in database
                    session.update(rez);
                    //session.update(pojam);
                    session.getTransaction().commit();
                    session.close();
                } else {
                    //supervisor
                    sessionF = HibernateUtil.getSessionFactory();
                    session = sessionF.openSession();
                    session.beginTransaction();
       
                    query = session.createCriteria(Pojam.class);
                    Pojam curr = (Pojam) query.add(Restrictions.eq("slovo", tak.getUsername())).uniqueResult();
                    session.getTransaction().commit();
                   
                    session.close();
                   
                    //if(curr==null) System.out.println("Curr prazan");
                    
                    curr.setDrzava(pojam.getDrzava());
                    
                    //System.out.println(curr.getDrzava());
                    hs.setAttribute("pojam", pojam);
                    
                    sessionF = HibernateUtil.getSessionFactory();
                    session = sessionF.openSession();
                    session.beginTransaction();
                        //Save the employee in database
                  
                    
                    session.update(curr);
                    
                    session.getTransaction().commit();
                    session.close();
                    
                }
                if(pojam.getGrad() == null ? igra.getGrad()== null : pojam.getGrad().equals(igra.getGrad())){
                    s = rez.getGeografija();
                    s += 2;
                    rez.setGeografija(s);
                    pojam.setGrad(null);
                    hs.setAttribute("pojam", pojam);
                    
                    sessionF = HibernateUtil.getSessionFactory();
                    session = sessionF.openSession();
                    session.beginTransaction();
                        //Save the employee in database
                    session.update(rez);
                    //session.update(pojam);
                    session.getTransaction().commit();
                    session.close();
                } else {
                    //supervisor
                    sessionF = HibernateUtil.getSessionFactory();
                    session = sessionF.openSession();
                    session.beginTransaction();
       
                    query = session.createCriteria(Pojam.class);
                    Pojam curr = (Pojam) query.add(Restrictions.eq("slovo", tak.getUsername())).uniqueResult();
                    session.getTransaction().commit();
                   
                    session.close();
                   
                    curr.setGrad(pojam.getGrad());
                    
                    hs.setAttribute("pojam", pojam);
                    
                    sessionF = HibernateUtil.getSessionFactory();
                    session = sessionF.openSession();
                    session.beginTransaction();
                        //Save the employee in database
                  
                    
                    session.update(curr);
                    session.getTransaction().commit();
                    session.close();
                    
                }
                //jezero
                 if(pojam.getJezero()== null ? igra.getJezero()== null : pojam.getJezero().equals(igra.getJezero())){
                    s = rez.getGeografija();
                    s += 2;
                    rez.setGeografija(s);
                    pojam.setJezero(null);
                    hs.setAttribute("pojam", pojam);
                    
                    sessionF = HibernateUtil.getSessionFactory();
                    session = sessionF.openSession();
                    session.beginTransaction();
                        //Save the employee in database
                    session.update(rez);
                    //session.update(pojam);
                    session.getTransaction().commit();
                    session.close();
                } else {
                    //supervisor
                    sessionF = HibernateUtil.getSessionFactory();
                    session = sessionF.openSession();
                    session.beginTransaction();
       
                    query = session.createCriteria(Pojam.class);
                    Pojam curr = (Pojam) query.add(Restrictions.eq("slovo", tak.getUsername())).uniqueResult();
                    session.getTransaction().commit();
                   
                    session.close();
                   
                    curr.setJezero(pojam.getJezero());
                    
                    hs.setAttribute("pojam", pojam);
                    
                    sessionF = HibernateUtil.getSessionFactory();
                    session = sessionF.openSession();
                    session.beginTransaction();
                        //Save the employee in database
                  
                    
                    session.update(curr);
                    session.getTransaction().commit();
                    session.close();
                    
                }
                 //planina
                  if(pojam.getPlanina()== null ? igra.getPlanina()== null : pojam.getPlanina().equals(igra.getPlanina())){
                    s = rez.getGeografija();
                    s += 2;
                    rez.setGeografija(s);
                    pojam.setPlanina(null);
                    hs.setAttribute("pojam", pojam);
                    
                    sessionF = HibernateUtil.getSessionFactory();
                    session = sessionF.openSession();
                    session.beginTransaction();
                        //Save the employee in database
                    session.update(rez);
                    //session.update(pojam);
                    session.getTransaction().commit();
                    session.close();
                } else {
                    //supervisor
                    sessionF = HibernateUtil.getSessionFactory();
                    session = sessionF.openSession();
                    session.beginTransaction();
       
                    query = session.createCriteria(Pojam.class);
                    Pojam curr = (Pojam) query.add(Restrictions.eq("slovo", tak.getUsername())).uniqueResult();
                    session.getTransaction().commit();
                   
                    session.close();
                   
                    curr.setPlanina(pojam.getPlanina());
                    
                    hs.setAttribute("pojam", pojam);
                    
                    sessionF = HibernateUtil.getSessionFactory();
                    session = sessionF.openSession();
                    session.beginTransaction();
                        //Save the employee in database
                  
                    
                    session.update(curr);
                    session.getTransaction().commit();
                    session.close();
                    
                }
               //reka
                if(pojam.getGrad() == null ? igra.getGrad()== null : pojam.getGrad().equals(igra.getGrad())){
                    s = rez.getGeografija();
                    s += 2;
                    rez.setGeografija(s);
                    pojam.setGrad(null);
                    hs.setAttribute("pojam", pojam);
                    
                    sessionF = HibernateUtil.getSessionFactory();
                    session = sessionF.openSession();
                    session.beginTransaction();
                        //Save the employee in database
                    session.update(rez);
                    //session.update(pojam);
                    session.getTransaction().commit();
                    session.close();
                } else {
                    //supervisor
                    sessionF = HibernateUtil.getSessionFactory();
                    session = sessionF.openSession();
                    session.beginTransaction();
       
                    query = session.createCriteria(Pojam.class);
                    Pojam curr = (Pojam) query.add(Restrictions.eq("slovo", tak.getUsername())).uniqueResult();
                    session.getTransaction().commit();
                   
                    session.close();
                   
                    curr.setGrad(pojam.getGrad());
                    
                    hs.setAttribute("pojam", pojam);
                    
                    sessionF = HibernateUtil.getSessionFactory();
                    session = sessionF.openSession();
                    session.beginTransaction();
                        //Save the employee in database
                  
                    
                    session.update(curr);
                    session.getTransaction().commit();
                    session.close();
                    
                }
               //reka
               
                if(pojam.getReka() == null ? igra.getReka()== null : pojam.getReka().equals(igra.getReka())){
                    s = rez.getGeografija();
                    s += 2;
                    rez.setGeografija(s);
                    pojam.setReka(null);
                    hs.setAttribute("pojam", pojam);
                    
                    sessionF = HibernateUtil.getSessionFactory();
                    session = sessionF.openSession();
                    session.beginTransaction();
                        //Save the employee in database
                    session.update(rez);
                    //session.update(pojam);
                    session.getTransaction().commit();
                    session.close();
                } else {
                    //supervisor
                    sessionF = HibernateUtil.getSessionFactory();
                    session = sessionF.openSession();
                    session.beginTransaction();
       
                    query = session.createCriteria(Pojam.class);
                    Pojam curr = (Pojam) query.add(Restrictions.eq("slovo", tak.getUsername())).uniqueResult();
                    session.getTransaction().commit();
                   
                    session.close();
                   
                    curr.setReka(pojam.getReka());
                    
                    hs.setAttribute("pojam", pojam);
                    
                    sessionF = HibernateUtil.getSessionFactory();
                    session = sessionF.openSession();
                    session.beginTransaction();
                        //Save the employee in database
                  
                    
                    session.update(curr);
                    session.getTransaction().commit();
                    session.close();
                    
                }
                
                //Zivo
                 if(pojam.getZivotinja()== null ? igra.getZivotinja()== null : pojam.getZivotinja().equals(igra.getZivotinja())){
                    s = rez.getGeografija();
                    s += 2;
                    rez.setGeografija(s);
                    pojam.setZivotinja(null);
                    hs.setAttribute("pojam", pojam);
                    
                    sessionF = HibernateUtil.getSessionFactory();
                    session = sessionF.openSession();
                    session.beginTransaction();
                        //Save the employee in database
                    session.update(rez);
                    //session.update(pojam);
                    session.getTransaction().commit();
                    session.close();
                } else {
                    //supervisor
                    sessionF = HibernateUtil.getSessionFactory();
                    session = sessionF.openSession();
                    session.beginTransaction();
       
                    query = session.createCriteria(Pojam.class);
                    Pojam curr = (Pojam) query.add(Restrictions.eq("slovo", tak.getUsername())).uniqueResult();
                    session.getTransaction().commit();
                   
                    session.close();
                   
                    curr.setZivotinja(pojam.getZivotinja());
                    
                    hs.setAttribute("pojam", pojam);
                    
                    sessionF = HibernateUtil.getSessionFactory();
                    session = sessionF.openSession();
                    session.beginTransaction();
                        //Save the employee in database
                  
                    
                    session.update(curr);
                    session.getTransaction().commit();
                    session.close();
                    
                }
                //biljka
                 if(pojam.getBiljka()== null ? igra.getBiljka()== null : pojam.getBiljka().equals(igra.getBiljka())){
                    s = rez.getGeografija();
                    s += 2;
                    rez.setGeografija(s);
                    pojam.setBiljka(null);
                    hs.setAttribute("pojam", pojam);
                    
                    sessionF = HibernateUtil.getSessionFactory();
                    session = sessionF.openSession();
                    session.beginTransaction();
                        //Save the employee in database
                    session.update(rez);
                    //session.update(pojam);
                    session.getTransaction().commit();
                    session.close();
                } else {
                    //supervisor
                    sessionF = HibernateUtil.getSessionFactory();
                    session = sessionF.openSession();
                    session.beginTransaction();
       
                    query = session.createCriteria(Pojam.class);
                    Pojam curr = (Pojam) query.add(Restrictions.eq("slovo", tak.getUsername())).uniqueResult();
                    session.getTransaction().commit();
                   
                    session.close();
                   
                    curr.setBiljka(pojam.getBiljka());
                    
                    hs.setAttribute("pojam", pojam);
                    
                    sessionF = HibernateUtil.getSessionFactory();
                    session = sessionF.openSession();
                    session.beginTransaction();
                        //Save the employee in database
                  
                    
                    session.update(curr);
                    session.getTransaction().commit();
                    session.close();
                    
                }
                 //Gru
                  if(pojam.getGrupa()== null ? igra.getGrupa()== null : pojam.getGrupa().equals(igra.getGrupa())){
                    s = rez.getGeografija();
                    s += 2;
                    rez.setGeografija(s);
                    pojam.setGrupa(null);
                    hs.setAttribute("pojam", pojam);
                    
                    sessionF = HibernateUtil.getSessionFactory();
                    session = sessionF.openSession();
                    session.beginTransaction();
                        //Save the employee in database
                    session.update(rez);
                    //session.update(pojam);
                    session.getTransaction().commit();
                    session.close();
                } else {
                    //supervisor
                    sessionF = HibernateUtil.getSessionFactory();
                    session = sessionF.openSession();
                    session.beginTransaction();
       
                    query = session.createCriteria(Pojam.class);
                    Pojam curr = (Pojam) query.add(Restrictions.eq("slovo", tak.getUsername())).uniqueResult();
                    session.getTransaction().commit();
                   
                    session.close();
                   
                    curr.setGrupa(pojam.getGrupa());
                    
                    hs.setAttribute("pojam", pojam);
                    
                    sessionF = HibernateUtil.getSessionFactory();
                    session = sessionF.openSession();
                    session.beginTransaction();
                        //Save the employee in database
                  
                    
                    session.update(curr);
                    session.getTransaction().commit();
                    session.close();
                    
                }
        
                  
                int posle = rez.getGeografija();
                
                Tabela tabela = new Tabela();
            
                tabela.setBodovi(posle-pre);
                Date date = new Date();
                java.sql.Date datum = new java.sql.Date(date.getTime());
                tabela.setDatum(datum);
                tabela.setIgra("geografija");
                tabela.setUsername(tak.getUsername());
            
                sessionF = HibernateUtil.getSessionFactory();
                session = sessionF.openSession();
                session.beginTransaction();
        
                    //Save the employee in database
                session.save(tabela);
        
                session.getTransaction().commit();
                session.close();
                
            }
        
        
       
    }
    
    public void sup(){
        
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
       
        Criteria query = session.createCriteria(Takmicar.class);
        Takmicar tak = (Takmicar) query.add(Restrictions.eq("status", "a")).uniqueResult();
        session.getTransaction().commit();
        
        session.close();
        
        if(tak!=null) {
        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
        
        query = session.createCriteria(Pojam.class);
        ispis = (Pojam) query.add(Restrictions.eq("slovo", tak.getUsername())).uniqueResult();
        session.getTransaction().commit();
        session.close();
        }
        //System.out.println(ispis.getDrzava());
    }
    
    public void brisi(){
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
       
        Criteria query = session.createCriteria(Takmicar.class);
        Takmicar tak = (Takmicar) query.add(Restrictions.eq("status", "a")).uniqueResult();
        session.getTransaction().commit();
        
        session.close();
        
        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
        
        query = session.createCriteria(Pojam.class);
        ispis = (Pojam) query.add(Restrictions.eq("slovo", tak.getUsername())).uniqueResult();
        session.getTransaction().commit();
        session.close();
    }
    
    public void potvrdiDrz(){
        System.out.println("POTVRDI");
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
       
        Criteria query = session.createCriteria(Takmicar.class);
        Takmicar tak = (Takmicar) query.add(Restrictions.eq("status", "a")).uniqueResult();
        session.getTransaction().commit();
        
        session.close();
        
        
        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
        
        query = session.createCriteria(Pojam.class);
        ispis = (Pojam) query.add(Restrictions.eq("slovo", tak.getUsername())).uniqueResult();
        session.getTransaction().commit();
        session.close();
        
        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
       
        Date date = new Date();
        java.sql.Date datum = new java.sql.Date(date.getTime());
        
        query = session.createCriteria(Tabela.class);
        Tabela rez = (Tabela) query.add(Restrictions.eq("username", tak.getUsername())).add(Restrictions.eq("datum", datum)).uniqueResult();
        session.getTransaction().commit();
        
        session.close();
        
        if(ispis.getDrzava()!=null) {
        int s = rez.getBodovi();
        s += 4;
        rez.setBodovi(s);
        ispis.setDrzava(null);
                        

        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
                            //Save the employee in database
        session.update(rez);
        session.update(ispis);
                        //session.update(pojam);
        session.getTransaction().commit();
        session.close();
        
        
        
        }
    }
    
    public void potvrdiGrad(){
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
       
        Criteria query = session.createCriteria(Takmicar.class);
        Takmicar tak = (Takmicar) query.add(Restrictions.eq("status", "a")).uniqueResult();
        session.getTransaction().commit();
        
        session.close();
        
        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
        
        query = session.createCriteria(Pojam.class);
        ispis = (Pojam) query.add(Restrictions.eq("slovo", tak.getUsername())).uniqueResult();
        session.getTransaction().commit();
        session.close();
        
        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
       
       Date date = new Date();
        java.sql.Date datum = new java.sql.Date(date.getTime());
        
        query = session.createCriteria(Tabela.class);
        Tabela rez = (Tabela) query.add(Restrictions.eq("username", tak.getUsername())).add(Restrictions.eq("datum", datum)).uniqueResult();
        session.getTransaction().commit();
        
        session.close();
        
        if(ispis.getDrzava()!=null) {
        int s = rez.getBodovi();
        s += 4;
        rez.setBodovi(s);
        ispis.setDrzava(null);
                        

        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
                            //Save the employee in database
        session.update(rez);
        session.update(ispis);
                        //session.update(pojam);
        session.getTransaction().commit();
        session.close();
        
        }
    }
    
    public void potvrdiJez(){
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
       
        Criteria query = session.createCriteria(Takmicar.class);
        Takmicar tak = (Takmicar) query.add(Restrictions.eq("status", "a")).uniqueResult();
        session.getTransaction().commit();
        
        session.close();
        
        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
        
        query = session.createCriteria(Pojam.class);
        ispis = (Pojam) query.add(Restrictions.eq("slovo", tak.getUsername())).uniqueResult();
        session.getTransaction().commit();
        session.close();
        
        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
       
        Date date = new Date();
        java.sql.Date datum = new java.sql.Date(date.getTime());
        
        query = session.createCriteria(Tabela.class);
        Tabela rez = (Tabela) query.add(Restrictions.eq("username", tak.getUsername())).add(Restrictions.eq("datum", datum)).uniqueResult();
        session.getTransaction().commit();
        
        session.close();
        
        if(ispis.getDrzava()!=null) {
        int s = rez.getBodovi();
        s += 4;
        rez.setBodovi(s);
        ispis.setDrzava(null);
                        

        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
                            //Save the employee in database
        session.update(rez);
        session.update(ispis);
                        //session.update(pojam);
        session.getTransaction().commit();
        session.close();
        
        }
    }
    
    public void potvrdiPlan(){
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
       
        Criteria query = session.createCriteria(Takmicar.class);
        Takmicar tak = (Takmicar) query.add(Restrictions.eq("status", "a")).uniqueResult();
        session.getTransaction().commit();
        
        session.close();
        
        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
        
        query = session.createCriteria(Pojam.class);
        ispis = (Pojam) query.add(Restrictions.eq("slovo", tak.getUsername())).uniqueResult();
        session.getTransaction().commit();
        session.close();
        
        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
       
        Date date = new Date();
        java.sql.Date datum = new java.sql.Date(date.getTime());
        
        query = session.createCriteria(Tabela.class);
        Tabela rez = (Tabela) query.add(Restrictions.eq("username", tak.getUsername())).add(Restrictions.eq("datum", datum)).uniqueResult();
        session.getTransaction().commit();
        
        session.close();
        
        if(ispis.getDrzava()!=null) {
        int s = rez.getBodovi();
        s += 4;
        rez.setBodovi(s);
        ispis.setDrzava(null);
                        

        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
                            //Save the employee in database
        session.update(rez);
        session.update(ispis);
                        //session.update(pojam);
        session.getTransaction().commit();
        session.close();
        
        }
    }
    
    public void potvrdiReka(){
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
       
        Criteria query = session.createCriteria(Takmicar.class);
        Takmicar tak = (Takmicar) query.add(Restrictions.eq("status", "a")).uniqueResult();
        session.getTransaction().commit();
        
        session.close();
        
        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
        
        query = session.createCriteria(Pojam.class);
        ispis = (Pojam) query.add(Restrictions.eq("slovo", tak.getUsername())).uniqueResult();
        session.getTransaction().commit();
        session.close();
        
        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
       
       Date date = new Date();
        java.sql.Date datum = new java.sql.Date(date.getTime());
        
        query = session.createCriteria(Tabela.class);
        Tabela rez = (Tabela) query.add(Restrictions.eq("username", tak.getUsername())).add(Restrictions.eq("datum", datum)).uniqueResult();
        session.getTransaction().commit();
        
        session.close();
        
        if(ispis.getDrzava()!=null) {
        int s = rez.getBodovi();
        s += 4;
        rez.setBodovi(s);
        ispis.setDrzava(null);
                        

        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
                            //Save the employee in database
        session.update(rez);
        session.update(ispis);
                        //session.update(pojam);
        session.getTransaction().commit();
        session.close();
        
        }
    }
    
    public void potvrdiZiv(){
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
       
        Criteria query = session.createCriteria(Takmicar.class);
        Takmicar tak = (Takmicar) query.add(Restrictions.eq("status", "a")).uniqueResult();
        session.getTransaction().commit();
        
        session.close();
        
        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
        
        query = session.createCriteria(Pojam.class);
        ispis = (Pojam) query.add(Restrictions.eq("slovo", tak.getUsername())).uniqueResult();
        session.getTransaction().commit();
        session.close();
        
        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
       
        Date date = new Date();
        java.sql.Date datum = new java.sql.Date(date.getTime());
        
        query = session.createCriteria(Tabela.class);
        Tabela rez = (Tabela) query.add(Restrictions.eq("username", tak.getUsername())).add(Restrictions.eq("datum", datum)).uniqueResult();
        session.getTransaction().commit();
        
        session.close();
        
        if(ispis.getDrzava()!=null) {
        int s = rez.getBodovi();
        s += 4;
        rez.setBodovi(s);
        ispis.setDrzava(null);
                        

        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
                            //Save the employee in database
        session.update(rez);
        session.update(ispis);
                        //session.update(pojam);
        session.getTransaction().commit();
        session.close();
        
        }
    }
    
    public void potvrdiBi(){
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
       
        Criteria query = session.createCriteria(Takmicar.class);
        Takmicar tak = (Takmicar) query.add(Restrictions.eq("status", "a")).uniqueResult();
        session.getTransaction().commit();
        
        session.close();
        
        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
        
        query = session.createCriteria(Pojam.class);
        ispis = (Pojam) query.add(Restrictions.eq("slovo", tak.getUsername())).uniqueResult();
        session.getTransaction().commit();
        session.close();
        
        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
       
        Date date = new Date();
        java.sql.Date datum = new java.sql.Date(date.getTime());
        
        query = session.createCriteria(Tabela.class);
        Tabela rez = (Tabela) query.add(Restrictions.eq("username", tak.getUsername())).add(Restrictions.eq("datum", datum)).uniqueResult();
        session.getTransaction().commit();
        
        session.close();
        
        if(ispis.getDrzava()!=null) {
        int s = rez.getBodovi();
        s += 4;
        rez.setBodovi(s);
        ispis.setDrzava(null);
                        

        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
                            //Save the employee in database
        session.update(rez);
        session.update(ispis);
                        //session.update(pojam);
        session.getTransaction().commit();
        session.close();
        
        }
    }
    
    public void potvrdiGru(){
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
       
        Criteria query = session.createCriteria(Takmicar.class);
        Takmicar tak = (Takmicar) query.add(Restrictions.eq("status", "a")).uniqueResult();
        session.getTransaction().commit();
        
        session.close();
        
        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
        
        query = session.createCriteria(Pojam.class);
        ispis = (Pojam) query.add(Restrictions.eq("slovo", tak.getUsername())).uniqueResult();
        session.getTransaction().commit();
        session.close();
        
        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
       
        Date date = new Date();
        java.sql.Date datum = new java.sql.Date(date.getTime());
        
        query = session.createCriteria(Tabela.class);
        Tabela rez = (Tabela) query.add(Restrictions.eq("username", tak.getUsername())).add(Restrictions.eq("datum", datum)).uniqueResult();
        session.getTransaction().commit();
        
        session.close();
        
        if(ispis.getDrzava()!=null) {
        int s = rez.getBodovi();
        s += 4;
        rez.setBodovi(s);
        ispis.setDrzava(null);
                        

        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
                            //Save the employee in database
        session.update(rez);
        session.update(ispis);
                        //session.update(pojam);
        session.getTransaction().commit();
        session.close();
        
        }
    }
    
    public void odbDrz(){
        
        brisi();
        ispis.setDrzava(null);
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
                            //Save the employee in database
        session.update(ispis);
                        //session.update(pojam);
        session.getTransaction().commit();
        session.close();
    }
    
    public void odbGrad(){
        
        
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
       
        Criteria query = session.createCriteria(Takmicar.class);
        Takmicar tak = (Takmicar) query.add(Restrictions.eq("status", "a")).uniqueResult();
        session.getTransaction().commit();
        
        session.close();
        
        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
        
        query = session.createCriteria(Pojam.class);
        ispis = (Pojam) query.add(Restrictions.eq("slovo", tak.getUsername())).uniqueResult();
        session.getTransaction().commit();
        session.close();
        ispis.setGrad(null);
        
        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
                            //Save the employee in database
        session.update(ispis);
                        //session.update(pojam);
        session.getTransaction().commit();
        session.close();
    }
    
    public void odbJez(){
        
        brisi();
        ispis.setJezero(null);
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
                            //Save the employee in database
        session.update(ispis);
                        //session.update(pojam);
        session.getTransaction().commit();
        session.close();
    }
    
    public void odbPlan(){
        
        brisi();
        ispis.setPlanina(null);
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
                            //Save the employee in database
        session.update(ispis);
                        //session.update(pojam);
        session.getTransaction().commit();
        session.close();
    }
    
    public void odbReka(){
        
        brisi();
        ispis.setReka(null);
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
                            //Save the employee in database
        session.update(ispis);
                        //session.update(pojam);
        session.getTransaction().commit();
        session.close();
    }
    
    public void odbZiv(){
        
        brisi();
        ispis.setZivotinja(null);
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
                            //Save the employee in database
        session.update(ispis);
                        //session.update(pojam);
        session.getTransaction().commit();
        session.close();
    }
    
    
    public void odbBi(){
        
        brisi();
        ispis.setBiljka(null);
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
                            //Save the employee in database
        session.update(ispis);
                        //session.update(pojam);
        session.getTransaction().commit();
        session.close();
    }
    
    public void odbGru(){
        
        brisi();
        ispis.setGrupa(null);
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
                            //Save the employee in database
        session.update(ispis);
                        //session.update(pojam);
        session.getTransaction().commit();
        session.close();
    }
}
