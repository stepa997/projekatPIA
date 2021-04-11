/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import db.HibernateUtil;
import entiteti.Reci;
import entiteti.Rezultat;
import entiteti.Tabela;
import entiteti.Takmicar;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
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
@RequestScoped
public class MojBroj {
    
    private String rezulat;
    private int[] nums;
    private int iter;
    private boolean proba;
    private boolean b;
    private boolean z;
    private boolean[] br;
    private int zag;
    private boolean zatvori;

    public boolean isZatvori() {
        return zatvori;
    }

    public void setZatvori(boolean zatvori) {
        this.zatvori = zatvori;
    }
    
    
    
    public int getZag() {
        return zag;
    }

    public void setZag(int zag) {
        this.zag = zag;
    }
    
    

    public boolean[] getBr() {
        return br;
    }

    public void setBr(boolean[] br) {
        this.br = br;
    }
     
    
    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }

    public boolean isZ() {
        return z;
    }

    public void setZ(boolean z) {
        this.z = z;
    }
    
    
    
    public boolean isProba() {
        return proba;
    }

    public void setProba(boolean proba) {
        this.proba = proba;
    }

    
    
    public void nezn(){
        proba=true;
    }

    public String getRezulat() {
        return rezulat;
    }

    public void setRezulat(String rezulat) {
        this.rezulat = rezulat;
    }

    public int getIter() {
        return iter;
    }

    public void setIter(int iter) {
        this.iter = iter;
    }
    
    public MojBroj(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
        iter = (int) hs.getAttribute("iter");
        nums = (int[]) hs.getAttribute("nums");
        br = (boolean[]) hs.getAttribute("br");
        zag = (int) hs.getAttribute("zag");
    }

    public int[] getNums() {
        return nums;
    }

    public void setNums(int[] nums) {
        this.nums = nums;
    }
    
    
    
     public void increment() {
        z=true;
        if(iter<4) {
        nums[iter++] = (int) Math.floor(Math.random() * 9)+1;
        //System.out.println(iter);
        //System.out.println(nums[iter-1]);
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
        hs.setAttribute("iter", iter);
        hs.setAttribute("nums", nums);
        }
        else{
            if(iter<5){
                int[] niz = {10, 15, 20};
                nums[iter++] = niz[(int) Math.floor(Math.random() * 2)];
                //System.out.println(nums[iter-1]);
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
                hs.setAttribute("iter", iter);
                hs.setAttribute("nums", nums);
            } else if(iter<6){
                int[] niz = {25, 50, 75, 100};
                nums[iter++] = niz[(int) Math.floor(Math.random() * 3)];
                //System.out.println(nums[iter-1]);
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
                hs.setAttribute("iter", iter);
                hs.setAttribute("nums", nums);
            }
            else if(iter==6){
                nums[iter++] = (int) Math.floor(Math.random() * 9) + 1;
                //System.out.println(nums[iter-1]);
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
                hs.setAttribute("iter", iter);
                hs.setAttribute("nums", nums);
            }
        }
    }
     
    public void dodaj(int broj){
        for(int i=0; i<br.length; i++){
            if(broj==nums[i] && br[i]==false) {br[i]=true;
                //System.out.println(i);
            break;}
        }
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
        zag = (int) hs.getAttribute("zag");
        if(zag>0){
            zatvori = false;
        }
        else zatvori = true;
        hs.setAttribute("br", br);
        hs.setAttribute("zag", zag);
        System.out.println(zag);
        rezulat+=broj;
        b=true;
        z=false;
        //System.out.println(rezulat);
    }
    
    public void dodajs(String br){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
        zag = (int) hs.getAttribute("zag");
        if("(".equals(br)) {zag++; zatvori=false;}
        else zatvori = true;
        
        if(")".equals(br)) {zag--; 
        if(zag>0) zatvori=false;  
        else zatvori=true; 
        b=true;
        z=false;
        System.out.println(zag);
        hs.setAttribute("zag", zag);
        rezulat+=br;
        }
        else{
        System.out.println(zag);
        hs.setAttribute("zag", zag);
        rezulat+=br;
        b=false;
        z=true;
        }
        //System.out.println(rezulat);
    }
     
    public void racun() throws ScriptException{

        int rez = (int) new ScriptEngineManager().getEngineByName("JavaScript").eval(rezulat);
        System.out.println(rez);
        if(rez == nums[6]){
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
            Takmicar tak = (Takmicar) hs.getAttribute("user");
            
            SessionFactory sessionF = HibernateUtil.getSessionFactory();
            Session session = sessionF.openSession();
            session.beginTransaction();
            Criteria query = session.createCriteria(Rezultat.class);
            Rezultat rezultat = (Rezultat) query.add(Restrictions.eq("username", tak.getUsername())).uniqueResult();
            session.getTransaction().commit();
            session.close();
            
            int ana = rezultat.getAnagram();
            ana += 10;
            
            rezultat.setMojbroj(ana);
            sessionF = HibernateUtil.getSessionFactory();
            session = sessionF.openSession();
            session.beginTransaction();
            
            //Save the employee in database
            session.update(rezultat);
            session.getTransaction().commit();
            session.close();
            
            Tabela tabela = new Tabela();
            
            tabela.setBodovi(10);
            Date date = new Date();
            java.sql.Date datum = new java.sql.Date(date.getTime());
            tabela.setDatum(datum);
            tabela.setIgra("mojbroj");
            tabela.setUsername(tak.getUsername());
            
            sessionF = HibernateUtil.getSessionFactory();
            session = sessionF.openSession();
            session.beginTransaction();
        
                    //Save the employee in database
            session.save(tabela);
        
            session.getTransaction().commit();
            session.close();
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("kraj.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(Anagram.class.getName()).log(Level.SEVERE, null, ex);
            }
            } else{
            
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
            Takmicar tak = (Takmicar) hs.getAttribute("user");
            
            SessionFactory sessionF = HibernateUtil.getSessionFactory();
            Session session = sessionF.openSession();
            session.beginTransaction();
            Criteria query = session.createCriteria(Rezultat.class);
            Rezultat rezultat = (Rezultat) query.add(Restrictions.eq("username", tak.getUsername())).uniqueResult();
            session.getTransaction().commit();
            session.close();
            
            Tabela tabela = new Tabela();
            
            tabela.setBodovi(0);
            Date date = new Date();
            java.sql.Date datum = new java.sql.Date(date.getTime());
            tabela.setDatum(datum);
            tabela.setIgra("mojbroj");
            tabela.setUsername(tak.getUsername());
            
            sessionF = HibernateUtil.getSessionFactory();
            session = sessionF.openSession();
            session.beginTransaction();
        
            //Save the employee in database
            session.save(tabela);
        
            session.getTransaction().commit();
            session.close();
            
                
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("kraj.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(Anagram.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            FacesContext.getCurrentInstance().responseComplete();
        
        
    }
            
}
