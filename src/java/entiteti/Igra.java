/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Djole
 */
@Entity
public class Igra {
    
    @Id
    @Column(name="datum")
    private Date datum;
    
    @Column(name="igra")
    private String igra;
    
    
    @Column(name="status")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
