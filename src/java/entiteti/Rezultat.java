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
public class Rezultat {
    
    
    @Id
    @Column(name="username")
    private String username;
    
    @Column(name="anagram")
    private int anagram;
    
    @Column(name="mojbroj")
    private int mojbroj;
    
    @Column(name="geografija")
    private int geografija;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAnagram() {
        return anagram;
    }

    public void setAnagram(int anagram) {
        this.anagram = anagram;
    }

    public int getMojbroj() {
        return mojbroj;
    }

    public void setMojbroj(int mojbroj) {
        this.mojbroj = mojbroj;
    }

    public int getGeografija() {
        return geografija;
    }

    public void setGeografija(int geografija) {
        this.geografija = geografija;
    }
    
    
    
}
