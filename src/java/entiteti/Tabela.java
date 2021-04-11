/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import controler.Key;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 *
 * @author Djole
 */
@Entity
@IdClass(Key.class)
public class Tabela {
    
    @Id
    @Column(name="datum")
    private Date datum;
    
    @Id
    @Column(name="username")
    private String username;
    
    @Column(name="igra")
    private String igra;
    
    @Column(name="bodovi")
    private int bodovi;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getBodovi() {
        return bodovi;
    }

    public void setBodovi(int bodovi) {
        this.bodovi = bodovi;
    }
    
    
    

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getIgra() {
        return igra;
    }

    public void setIgra(String igra) {
        this.igra = igra;
    }
    
    
}
