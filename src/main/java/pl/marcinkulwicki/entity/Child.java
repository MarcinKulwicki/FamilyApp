package pl.marcinkulwicki.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.pl.PESEL;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Entity
@Table(name = "child")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
public class Child {


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
    private String sex;

    @ManyToOne
    private Family family;

    public Child() {
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }
}
