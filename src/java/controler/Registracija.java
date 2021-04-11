/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import db.HibernateUtil;
import entiteti.Admin;
import entiteti.Supervisor;
import entiteti.Takmicar;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
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
import org.mindrot.jbcrypt.BCrypt;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Djole
 */
@ManagedBean
@ApplicationScoped
@Named("registracija")
public class Registracija implements Serializable{
    
    private Takmicar tak = new Takmicar();
    private File file; 
    private UploadedFile f;
    

    private String regpot;
    private String user;
    private String jmbg;
    
    private String pitanje;
    private String odgovor;
    private String baseodg;
    
    private String sifra;
    private String potvrda;
    
    private String is;

    public String getIs() {
        return is;
    }

    public void setIs(String is) {
        this.is = is;
    }
    
    

    public String getRegpot() {
        return regpot;
    }

    public void setRegpot(String regpot) {
        this.regpot = regpot;
    }
    
    

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getPotvrda() {
        return potvrda;
    }

    public void setPotvrda(String potvrda) {
        this.potvrda = potvrda;
    }
    
    

    public String getBaseodg() {
        return baseodg;
    }

    public void setBaseodg(String baseodg) {
        this.baseodg = baseodg;
    }
    
    

    public String getPitanje() {
        return pitanje;
    }

    public void setPitanje(String pitanje) {
        this.pitanje = pitanje;
    }

    public String getOdgovor() {
        return odgovor;
    }

    public void setOdgovor(String odgovor) {
        this.odgovor = odgovor;
    }
    
    

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }
    
    public UploadedFile getF() {
        return f;
    }

    public void setF(UploadedFile f) {
        this.f = f;
    }
    
    

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Takmicar getTak() {
        return tak;
    }

    public void setTak(Takmicar tak) {
        this.tak = tak;
    }
    
    public void registrovanje(){
        
        if(tak.getPassword() == null ? regpot != null : !tak.getPassword().equals(regpot)){
            is="NISTE POTVRDILI LOZINKU DOBRO";
            return;
        }
        
        tak.setStatus("c");
        
        String s = tak.getPassword();
        
        tak.setPassword(BCrypt.hashpw(s, BCrypt.gensalt(10)));
       
       // tak.setImage(f.getInputstream());
            
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        
        //Save the employee in database
        session.save(tak);
        
        session.getTransaction().commit();
        session.close();
     
        
       
    }
    
    public void jmbgs(){
        
        System.out.println("Usao ovde");
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        
        Criteria query = session.createCriteria(Takmicar.class);
        Takmicar tak = (Takmicar) query.add(Restrictions.eq("username", user)).uniqueResult();
        
        session.getTransaction().commit();
        session.close();
        
        if(tak!= null && tak.getJmbg().equals(jmbg)){
            pitanje = tak.getPitanje();
            baseodg = tak.getOdgovor();
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("faces/"
                        + "tajna.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            FacesContext.getCurrentInstance().responseComplete();
        }
    }

    public void pit(){
        
        if(baseodg.equals(odgovor)) {
            
             try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("promena.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            FacesContext.getCurrentInstance().responseComplete();
        }
        
        
    }
    
    public void promenio(){
        if(sifra.equals(potvrda)) {
            
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        
        System.out.println(user);
        
        Criteria query = session.createCriteria(Takmicar.class);
        Takmicar tak = (Takmicar) query.add(Restrictions.eq("username", user)).uniqueResult();
        
        
        session.getTransaction().commit();
        session.close();
        
                    tak.setPassword(BCrypt.hashpw(sifra, BCrypt.gensalt(10)));
       
                    // tak.setImage(f.getInputstream());
            
                    sessionF = HibernateUtil.getSessionFactory();
                    session = sessionF.openSession();
                    session.beginTransaction();
        
                    //Save the employee in database
                    session.update(tak);
        
                    session.getTransaction().commit();
                    session.close();
            
             try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(""
                        + "index.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            FacesContext.getCurrentInstance().responseComplete();
        }
    }
    
}
