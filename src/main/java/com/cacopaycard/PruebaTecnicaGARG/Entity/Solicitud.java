package com.cacopaycard.PruebaTecnicaGARG.Entity;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Gibran
 */
@Entity
@Table(name = "solicitud")
public class Solicitud {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_solicitud;
    
    @Column
    private Long cuenta;
    
    @Column
    private String nombre;
    
    @Column
    private String ap_paterno;
    
    @Column
    private String ap_materno;
    
    @Column
    private Date fecha_nacimiento;
    
    @Column
    private char genero;
    
    @Column
    private String curp;
    
    @Column
    private Long telefono;
    
    @Column
    private String correo;
    
    @Column
    private String profesion;

    public Solicitud() {
    }

    public Solicitud(Long id_solicitud, Long cuenta, String nombre, String ap_paterno, String ap_materno, Date fecha_nacimiento, char genero, String curp, Long telefono, String correo, String profesion) {
        this.id_solicitud = id_solicitud;
        this.cuenta = cuenta;
        this.nombre = nombre;
        this.ap_paterno = ap_paterno;
        this.ap_materno = ap_materno;
        this.fecha_nacimiento = fecha_nacimiento;
        this.genero = genero;
        this.curp = curp;
        this.telefono = telefono;
        this.correo = correo;
        this.profesion = profesion;
    }

    

    public Long getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(Long idSolicitud) {
        this.id_solicitud = idSolicitud;
    }

    public Long getCuenta() {
        return cuenta;
    }

    public void setCuenta(Long cuenta) {
        this.cuenta = cuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAp_paterno() {
        return ap_paterno;
    }

    public void setAp_paterno(String ap_paterno) {
        this.ap_paterno = ap_paterno;
    }

    public String getAp_materno() {
        return ap_materno;
    }

    public void setAp_materno(String ap_materno) {
        this.ap_materno = ap_materno;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public char getGenero() {
        return genero;
    }

    public void setGenero(char genero) {
        this.genero = genero;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }
    
}
