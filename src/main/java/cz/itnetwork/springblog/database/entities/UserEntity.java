package cz.itnetwork.springblog.database.entities;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "uzivatele") //@Table - definuje se nad třídou, uvedeme název tabulky v databázi (když má být jiný než název třídy)
@Entity //anotace, která sděluje, že se jedná o třídu, která reprezentuje záznam (řádek) v tabulce databáze (entita)
public class UserEntity implements UserDetails { //UserDetail je rozhraní reprezentující uživatele, součást AuthenticationProvider pro poskytnutí ověření


    // atributy třídy uživatele
    @Id //označuje, že se jedná o unikátní identifikační číslo (primární klíč tabulky SQL)
    @GeneratedValue(strategy = GenerationType.IDENTITY) //způsob generování nových ID, hodnota IDENTITY - databáze bude využívat zabudovanou číselnou řadu (každé ID bude o jedno vyšší)
    private Long userId; // velké L pro long, na začátku, když posíláme data, může ID být NULL

    @Column(nullable = false, unique = true) //parametr unique zajišťuje, že nepůjde uložit 2 uživatele se stejným e-mailem
    private String email;

    @Column(nullable = false) //@Column - atribut představuje jeden sloupec v tabulce, parametr nullable - zda může mít sloupec hodnotu NULL, parametr type - datový typ sloupce (INT, VARCHAR, DATE atd.)
    private String password;

    @Column(nullable = false)
    private boolean admin;

    //gettery a settery pro atributy uživatele
    public long getUserId() {
        return userId;
        }

    public void setUserId(long userId) {
        this.userId = userId;
        }

    public String getEmail() {
        return email;
        }

    public void setEmail(String email) {
        this.email = email;
        }

    public String getPassword() {
        return password;
        }

    public void setPassword(String password) {
        this.password = password;
        }

    public boolean isAdmin() {
        return admin;
        }

    public void setAdmin(boolean admin) {
        this.admin = admin;
        }

    //metody pro rozhraní UserDetails
    @Override
    public String getUsername() {
       return email;
        }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //ve Spring Boot musí mít uživatel alespoň jedno oprávnění nebo roli
        //v této metodě se podíváme, zda je uživatel administrátor, nebo obyčejný uživatel
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + (admin ? "ADMIN" : "USER")); //ternární operátor
        return List.of(authority);
        }

    @Override
    public boolean isAccountNonExpired() {
        return true;
        }

    @Override
    public boolean isAccountNonLocked() {
        return true;
        }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
        }

    @Override
    public boolean isEnabled() {
        return true;
        }
    }
