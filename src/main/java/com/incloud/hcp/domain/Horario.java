/*
 * Project home: https://github.com/jaxio/celerio-angular-quickstart
 * 
 * Source code generated by Celerio, an Open Source code generator by Jaxio.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Modificado por CARLOS BAZALAR
 * Email: cbazalarlarosa@gmail.com
 * Template pack-angular:src/main/java/domain/Entity.java.e.vm
 */
package com.incloud.hcp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.incloud.hcp.domain._framework.BaseDomain;
import com.incloud.hcp.domain._framework.Identifiable;
import com.incloud.hcp.domain._framework.IdentifiableHashBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;

import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Table(name = "horario")
//@Audited
//@AuditTable("_audi_HORARIO")
public class Horario extends BaseDomain implements Identifiable<Integer>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(Horario.class.getName());

    /***************************/
    /* Atributos de la Entidad */
    /***************************/

    // Raw attributes
    private Integer id;
    private Date fechaInicio;
    private String horaInicio;
    private String horaFin;
    private String frecuencia;
    private String modalidad;

    // Many to one
    private Curso curso;

    @Override
    public String entityClassName() {
        return Horario.class.getSimpleName();
    }

    // -- [id] ------------------------

    @Override
    @Column(name = "horario_id", precision = 10)
    @GeneratedValue(strategy = SEQUENCE, generator = "seq_horario")
    @Id
    @SequenceGenerator(name = "seq_horario", sequenceName = "seq_horario", allocationSize = 1)
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Horario id(Integer id) {
        setId(id);
        return this;
    }

    @Override
    @Transient
    public boolean isIdSet() {
        return id != null;
    }
    // -- [fechaInicio] ------------------------


    @Column(name = "fecha_inicio", nullable = false, length = 29)
    @Temporal(TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Horario fechaInicio(Date fechaInicio) {
        setFechaInicio(fechaInicio);
        return this;
    }
    // -- [horaInicio] ------------------------


    @Column(name = "hora_inicio", nullable = false, length = 5)
    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Horario horaInicio(String horaInicio) {
        setHoraInicio(horaInicio);
        return this;
    }
    // -- [horaFin] ------------------------


    @Column(name = "hora_fin", nullable = false, length = 5)
    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public Horario horaFin(String horaFin) {
        setHoraFin(horaFin);
        return this;
    }
    // -- [frecuencia] ------------------------


    @Column(name = "frecuencia", nullable = false, length = 20)
    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Horario frecuencia(String frecuencia) {
        setFrecuencia(frecuencia);
        return this;
    }
    // -- [modalidad] ------------------------


    @Column(name = "modalidad", nullable = false, length = 20)
    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public Horario modalidad(String modalidad) {
        setModalidad(modalidad);
        return this;
    }

    // -----------------------------------------------------------------
    // Many to One support
    // -----------------------------------------------------------------

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // many-to-one: Horario.curso ==> Curso.id
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -


    @JoinColumn(name = "curso_id", nullable = false)
    @ManyToOne
    public Curso getCurso() {
        return curso;
    }

    /**
     * Set the {@link #curso} without adding this Horario instance on the passed {@link #curso}
     */
    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Horario curso(Curso curso) {
        setCurso(curso);
        return this;
    }

    /**
     * Apply the default values.
     */
    public Horario withDefaults() {
        return this;
    }

    /**
     * Equals implementation using a business key.
     */
    @Override
    public boolean equals(Object other) {
        return this == other || (other instanceof Horario && hashCode() == other.hashCode());
    }

    private IdentifiableHashBuilder identifiableHashBuilder = new IdentifiableHashBuilder();

    @Override
    public int hashCode() {
        return identifiableHashBuilder.hash(log, this);
    }

    /**
     * Construct a readable string representation for this Horario instance.
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "Horario{" +
                "id=" + id +
                ", fechaInicio=" + fechaInicio +
                ", horaInicio='" + horaInicio + '\'' +
                ", horaFin='" + horaFin + '\'' +
                ", frecuencia='" + frecuencia + '\'' +
                ", modalidad='" + modalidad + '\'' +
                ", curso=" + curso +
                ", identifiableHashBuilder=" + identifiableHashBuilder +
                '}';
    }
}
