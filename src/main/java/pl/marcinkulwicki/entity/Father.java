package pl.marcinkulwicki.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "father")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
public class Father {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Control database
    @CreationTimestamp
    private Timestamp data_add;
    @UpdateTimestamp
    private Timestamp data_mod;
    @Version
    private Long idv;

    private String firstName;
    private String secondName;
    @Column(unique = true)
    private String pesel;
    private Date date;

    @OneToMany(mappedBy = "father")
    private List<Family> families;

    public Father() {
    }

    public Father(String firstName, String secondName, String pesel) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.pesel = pesel;
    }
    public Father(String firstName, String secondName, String pesel, Date date) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.pesel = pesel;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Father)) return false;
        Father father = (Father) o;
        return Objects.equals(getFirstName(), father.getFirstName()) &&
                Objects.equals(getSecondName(), father.getSecondName()) &&
                Objects.equals(getPesel(), father.getPesel()) &&
                Objects.equals(getDate(), father.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getSecondName(), getPesel(), getDate());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getData_add() {
        return data_add;
    }

    public void setData_add(Timestamp data_add) {
        this.data_add = data_add;
    }

    public Timestamp getData_mod() {
        return data_mod;
    }

    public void setData_mod(Timestamp data_mod) {
        this.data_mod = data_mod;
    }

    public Long getIdv() {
        return idv;
    }

    public void setIdv(Long idv) {
        this.idv = idv;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Family> getFamilies() {
        return families;
    }

    public void setFamilies(List<Family> families) {
        this.families = families;
    }
}
