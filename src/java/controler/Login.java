/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import db.HibernateUtil;
import entiteti.Admin;
import entiteti.Deset;
import entiteti.GostRez;
import entiteti.Pojam;
import entiteti.Supervisor;
import entiteti.Tabela;
import entiteti.Takmicar;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.mindrot.jbcrypt.BCrypt;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author jelic
 */

@javax.faces.bean.ManagedBean
@javax.faces.bean.ApplicationScoped
public class Login {
    private String username;
    private String password;
    private String poruka;
    private ArrayList<Takmicar> lista;
    
    private ArrayList<GostRez> tab;
    private ArrayList<GostRez> mesec;
    private ArrayList<Deset> deset;

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    
    
    public ArrayList<Deset> getDeset() {
        return deset;
    }

    public void setDeset(ArrayList<Deset> deset) {
        this.deset = deset;
    }

    

    public ArrayList<GostRez> getMesec() {
        return mesec;
    }

    public void setMesec(ArrayList<GostRez> mesec) {
        this.mesec = mesec;
    }
    

    public ArrayList<GostRez> getTab() {
        return tab;
    }

    public void setTab(ArrayList<GostRez> tab) {
        this.tab = tab;
    }
    
    public void ispis(){
        //System.out.println("OVDE SAM USAO");
        Date date = new Date();
        java.sql.Date datum = new java.sql.Date(date.getTime());
        tab = new ArrayList<>();
        LocalDate manje = LocalDate.now().minusDays(20);
        LocalDate prvi = LocalDate.now().withDayOfMonth(1);
        java.sql.Date pre = java.sql.Date.valueOf(manje);
        java.sql.Date poc = java.sql.Date.valueOf(prvi);
        //System.out.println(datum);
        //System.out.println(prvi);
        //System.out.println(pre);
            
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
       
        Criteria query = session.createCriteria(Tabela.class);
        tab =  (ArrayList<GostRez>) query.add(Restrictions.between("datum", pre , datum))
                            .setProjection(Projections.projectionList()
                            .add(Projections.property("username"), "username")
                            .add(Projections.sum("bodovi"), "bodovi")
                            .add(Projections.groupProperty("username"))).setResultTransformer(Transformers.aliasToBean(GostRez.class)).list();
        
        session.getTransaction().commit();
        
        session.close();
        
        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
       
        query = session.createCriteria(Tabela.class);
        mesec =  (ArrayList<GostRez>) query.add(Restrictions.ge("datum", poc))
                            .setProjection(Projections.projectionList()
                            .add(Projections.property("username"), "username")
                            .add(Projections.sum("bodovi"), "bodovi")
                            .add(Projections.groupProperty("username"))).setResultTransformer(Transformers.aliasToBean(GostRez.class)).list();
        
        session.getTransaction().commit();
        
        session.close();
        
        //System.out.println(tab.get(0).getBodovi());
         try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("faces/gost.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            FacesContext.getCurrentInstance().responseComplete();
    }
   

    public List<Takmicar> getLista() {
        return lista;
    }

    public void setLista(List<Takmicar> lista) {
        this.lista = (ArrayList<Takmicar>) lista;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void login(){
        
        
        
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        
        Criteria query = session.createCriteria(Takmicar.class);
        Takmicar user = (Takmicar) query.add(Restrictions.eq("username", username)).uniqueResult();
        
        
        session.getTransaction().commit();
        session.close();
        
        if(user!=null && !"c".equals(user.getStatus())) {
            boolean  flag = BCrypt.checkpw(password, user.getPassword());
            if(flag){
                poruka = "";
            if(!user.getJmbg().equals("1") && !user.getJmbg().equals("2")){
            user.setStatus("a");
            sessionF = HibernateUtil.getSessionFactory();
            session = sessionF.openSession();
            session.beginTransaction();
            //Save the employee in database
            session.update(user);
            session.getTransaction().commit();
            session.close();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
            hs.setAttribute("user", user);
            
            Date date = new Date();
            java.sql.Date datum = new java.sql.Date(date.getTime());
            
            sessionF = HibernateUtil.getSessionFactory();
            session = sessionF.openSession();
            session.beginTransaction();
       
            query = session.createCriteria(Tabela.class);
            deset =  (ArrayList<Deset>) query.add(Restrictions.eq("datum", datum))
                            .setProjection(Projections.projectionList()
                            .add(Projections.property("username"), "username")
                            .add(Projections.property("bodovi"), "bodovi")        
                            .add(Projections.groupProperty("username")))
                            .addOrder(Order.desc("bodovi"))
                            .setMaxResults(10).setResultTransformer(Transformers.aliasToBean(Deset.class)).list();
        
            session.getTransaction().commit();
        
            session.close();
            
            
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("faces/"
                        + "takmicar.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            FacesContext.getCurrentInstance().responseComplete();
            
            }
            
            
            
            if(user.getJmbg().equals("1")){
            
            user.setStatus("n");
            lista = new ArrayList<>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
            hs.setAttribute("admin", user);
            
            
            sessionF = HibernateUtil.getSessionFactory();
            session = sessionF.openSession();
            session.beginTransaction();
        
            query = session.createCriteria(Takmicar.class);
            lista = (ArrayList<Takmicar>) query.add(Restrictions.eq("status", "c")).list();
            session.getTransaction().commit();
            session.close();
            
            hs.setAttribute("lista", lista);
            
            try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("admin.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            FacesContext.getCurrentInstance().responseComplete();
            }
            
            if(user.getJmbg().equals("2")){
                
            user.setStatus("n");    
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
            hs.setAttribute("sup", user);
            hs.setAttribute("broj", 5);
            
            Pojam pojam = new Pojam();
            hs.setAttribute("pojam", pojam);
            try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("faces/super.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            FacesContext.getCurrentInstance().responseComplete();
                
            }
            
            
            } else poruka = "POGRESNA SIFRA";
            
            
            } else poruka="NEMA TOG KORISNIKA";
    }
    
     public String logout(){
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        
        Criteria query = session.createCriteria(Pojam.class);
        Pojam tabela = (Pojam) query.add(Restrictions.eq("slovo", username)).uniqueResult();
        
        if(tabela!=null) session.delete(tabela);
        
        session.getTransaction().commit();
        session.close();
        
        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
        
        query = session.createCriteria(Takmicar.class);
        Takmicar user = (Takmicar) query.add(Restrictions.eq("username", username)).uniqueResult();
        
        user.setStatus("n");
        
        session.update(user);
        
        session.getTransaction().commit();
        session.close();
         
        //System.out.println("controler.Login.logout()");
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession s = (HttpSession) fc.getExternalContext().getSession(false);
        s.invalidate();
        return "index?faces-redirect=true";
    }
    
}
    

    //A1hjsdh| - sifra

    /*
        else{
        
        
        if(admin!=null) {
            boolean  flag = BCrypt.checkpw(password, admin.getPassword());
            if(flag){
            lista = new ArrayList<>();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
            hs.setAttribute("admin", admin);
            
            sessionF = HibernateUtil.getSessionFactory();
            session = sessionF.openSession();
            session.beginTransaction();
        
            query = session.createCriteria(Takmicar.class);
            lista = (ArrayList<Takmicar>) query.add(Restrictions.eq("status", "c")).list();
            session.getTransaction().commit();
            session.close();
            
            hs.setAttribute("lista", lista);
            
            try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("faces/admin.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            FacesContext.getCurrentInstance().responseComplete();
        } }
        else{
        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
        
        query = session.createCriteria(Supervisor.class);
        Supervisor sup = (Supervisor) query.add(Restrictions.eq("username", username)).uniqueResult();
        session.getTransaction().commit();
        session.close();
        
        if(sup!=null) {
            boolean  flag = BCrypt.checkpw(password, sup.getPassword());
            if(flag) {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
            hs.setAttribute("sup", sup);
            Pojam pojam = new Pojam();
            hs.setAttribute("pojam", pojam);
            try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("faces/super.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            FacesContext.getCurrentInstance().responseComplete();
        } }
        
    }
        
    }
    }
    */
   
