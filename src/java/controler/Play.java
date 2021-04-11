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
import entiteti.Supervisor;
import entiteti.Tabela;
import entiteti.Takmicar;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.criterion.Restrictions;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Djole
 */

@ManagedBean
@ApplicationScoped
public class Play{
    
    private String pitanje = "";
    private String odgovor = "";
    private boolean flag = false;

    private String username;
    private String password;
    private String novi;
    private String potvrda; 

    public String getPotvrda() {
        return potvrda;
    }

    public void setPotvrda(String potvrda) {
        this.potvrda = potvrda;
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

    public String getNovi() {
        return novi;
    }

    public void setNovi(String novi) {
        this.novi = novi;
    }
    
    public void promena(){
        System.out.println("promena");
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        
        Criteria query = session.createCriteria(Takmicar.class);
        Takmicar user = (Takmicar) query.add(Restrictions.eq("username", username)).uniqueResult();
        
        
        session.getTransaction().commit();
        session.close();
        
        if(user!=null){
            boolean  flag = BCrypt.checkpw(password, user.getPassword());
            if(flag){
                if(novi.equals(potvrda)){
                   
                    user.setPassword(BCrypt.hashpw(novi, BCrypt.gensalt(10)));
       
                    // tak.setImage(f.getInputstream());
            
                    sessionF = HibernateUtil.getSessionFactory();
                    session = sessionF.openSession();
                    session.beginTransaction();
        
                    //Save the employee in database
                    session.update(user);
        
                    session.getTransaction().commit();
                    session.close();
                    
                  
                    try {
                        FacesContext.getCurrentInstance().getExternalContext().redirect("faces/index.xhtml");
                    } catch (IOException ex) {
                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    FacesContext.getCurrentInstance().responseComplete();

                }
            }
        }
    }
    
    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    
    public String getPitanje() {
        return this.pitanje;
    }

    public  void setPitanje(String pitanje) {
        this.pitanje = pitanje;
    }

    public  String getOdgovor() {
        return odgovor;
    }

    public void setOdgovor(String odgovor) {
        this.odgovor = odgovor;
    }
        
        public void igraj(){
              
        Date date = new Date();
        java.sql.Date datum = new java.sql.Date(date.getTime());
        
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
        Takmicar tak = (Takmicar) hs.getAttribute("user");
        
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
       
        Criteria query = session.createCriteria(Tabela.class);
        Tabela tabela = (Tabela) query.add(Restrictions.eq("datum", datum)).add(Restrictions.eq("username", tak.getUsername())).uniqueResult();
        session.getTransaction().commit();
        
        session.close();
        
        
        if(tabela!=null ) try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("kraj.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        else {
            
        sessionF = HibernateUtil.getSessionFactory();
        session = sessionF.openSession();
        session.beginTransaction();
       
        query = session.createCriteria(Igra.class);
        Igra igra = (Igra) query.add(Restrictions.eq("datum", datum)).uniqueResult();
        session.getTransaction().commit();
        
        session.close();
        
        if(igra!=null) {
            igra.setStatus("t");
            
            sessionF = HibernateUtil.getSessionFactory();
            session = sessionF.openSession();
            session.beginTransaction();
            //Save the employee in database
            session.update(igra);
            session.getTransaction().commit();
            session.close();
            
            fc = FacesContext.getCurrentInstance();
            hs = (HttpSession) fc.getExternalContext().getSession(false);
            hs.setAttribute("igra", igra);
            hs.setAttribute("broj", 0);
            hs.setAttribute("iter", 0);
            
                if( "anagram".equals(igra.getIgra())) {
                try {;
                    sessionF = HibernateUtil.getSessionFactory();
                    session = sessionF.openSession();
                    session.beginTransaction();
       
                    int id = 1;
        
                    query = session.createCriteria(Reci.class);
                    List pom = query.list();
                    System.out.println(pom.size());
                    id = (int) Math.floor(Math.random()*pom.size()+1);
                    Reci reci = (Reci) query.add(Restrictions.eq("id", id)).uniqueResult();
                    session.getTransaction().commit();
        
                    session.close();
                    
                    if(reci!=null) {
                        hs.setAttribute("reci", reci);
                        
                        setPitanje(reci.getPitanje());
                        setOdgovor(reci.getOdgovor());
                        System.out.println(pitanje);
                        
                        FacesContext.getCurrentInstance().getExternalContext().redirect("anagram.xhtml");
                        FacesContext.getCurrentInstance().responseComplete();
                    }
                    
               
                } catch (IOException ex) {
                    Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                }
        }  if( "mojbroj".equals(igra.getIgra())) try {
            igra.setStatus("t");
            int[] nums = new int[7];
            hs.setAttribute("nums", nums);
            boolean[] br = new boolean[6];
            for(int i=0; i<6; i++) br[i]=false;
            int zag = 0;
            hs.setAttribute("zag", zag);
            hs.setAttribute("br", br);
            FacesContext.getCurrentInstance().getExternalContext().redirect("mojbroj.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        if( "geografija".equals(igra.getIgra())) try {
            
            
            String[] niz = {"a", "b", "d", "j", "k", "m", "p", "s", "t", "u", "v"};
            
            String slovo = niz[(int) Math.floor(Math.random() * niz.length)];
            
            hs.setAttribute("slovo", slovo);
            
            igra.setStatus("t");
            Pojam pojam = new Pojam();
            
            sessionF = HibernateUtil.getSessionFactory();
            session = sessionF.openSession();
            session.beginTransaction();
        
            query = session.createCriteria(Takmicar.class);
            tak = (Takmicar) query.add(Restrictions.eq("status", "a")).uniqueResult();
            
            session.getTransaction().commit();
            session.close();
            
            pojam.setSlovo(tak.getUsername());
            
            
           // System.out.println(pojam.getDrzava());
            
            hs.setAttribute("pojam", pojam);
            
            sessionF = HibernateUtil.getSessionFactory();
            session = sessionF.openSession();
            session.beginTransaction();
            //Save the employee in database
            session.save(pojam);
            session.getTransaction().commit();
            session.close();
            
            
            
            FacesContext.getCurrentInstance().getExternalContext().redirect("geografija.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
            }
        
            }
        
            
        }
        }

}