/*
 * Project home: https://github.com/jaxio/celerio-angular-quickstart
 * 
 * Source code generated by Celerio, an Open Source code generator by Jaxio.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Modificado por CARLOS BAZALAR
 * Email: cbazalarlarosa@gmail.com
 * Template pack-angular:src/main/java/domain/EntityMeta_.java.e.vm
 */
package com.incloud.hcp.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@StaticMetamodel(Horario.class)
public abstract class Horario_ {

    // Raw attributes
    public static volatile SingularAttribute<Horario, Integer> id;
    public static volatile SingularAttribute<Horario, Date> fechaInicio;
    public static volatile SingularAttribute<Horario, String> horaInicio;
    public static volatile SingularAttribute<Horario, String> horaFin;
    public static volatile SingularAttribute<Horario, String> frecuencia;
    public static volatile SingularAttribute<Horario, String> modalidad;

    // Many to one
    public static volatile SingularAttribute<Horario, Curso> curso;
}
