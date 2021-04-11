/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Djole
 */

@Entity
public class Pojam {
    
    @Id
    @Column(name="slovo")
    private String slovo;
    
    @Column(name="drzava")
    private String drzava;
    
    @Column(name="grad")
    private String grad;
    
    @Column(name="jezero")
    private String jezero;
    
    @Column(name="planina")
    private String planina;
    
    @Column(name="reka")
    private String reka;
    
    @Column(name="zivotinja")
    private String zivotinja;
    
    @Column(name="biljka")
    private String biljka;
    
    @Column(name="grupa")
    private String grupa;
    
    

    public String getSlovo() {
        return slovo;
    }

    public void setSlovo(String slovo) {
        this.slovo = slovo;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getJezero() {
        return jezero;
    }

    public void setJezero(String jezero) {
        this.jezero = jezero;
    }

    public String getPlanina() {
        return planina;
    }

    public void setPlanina(String planina) {
        this.planina = planina;
    }

    public String getReka() {
        return reka;
    }

    public void setReka(String reka) {
        this.reka = reka;
    }

    public String getZivotinja() {
        return zivotinja;
    }

    public void setZivotinja(String zivotinja) {
        this.zivotinja = zivotinja;
    }

    public String getBiljka() {
        return biljka;
    }

    public void setBiljka(String biljka) {
        this.biljka = biljka;
    }

    public String getGrupa() {
        return grupa;
    }

    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }
    
    
    
}
