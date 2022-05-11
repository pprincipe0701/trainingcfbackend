package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.Participante;
import com.incloud.hcp.dto.ParticipanteDto;
import com.incloud.hcp.myibatis.mapper.ParticipanteMapper;
import com.incloud.hcp.repository.ParticipanteRepository;
import com.incloud.hcp.service.ParticipanteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ParticipanteServiceImpl implements ParticipanteService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ParticipanteRepository participanteRepository;

    @Autowired
    ParticipanteMapper participanteMapper;

    public List<Participante> listaParticipantes() {

        return participanteRepository.findAll();
    }

    public List<Participante>listaParticipantesByMyBatis(String nombre, String apellido, Integer edad) {
        return participanteMapper.listaParticipante(nombre,apellido,edad);
    }

    public Participante crearParticipante(ParticipanteDto participante) throws  Exception {
        log.info("crearParticipante_info :: " + participante);
        log.error("crearParticipante_error :: " + participante);
        Participante dbParticipante = new Participante();
        dbParticipante.setApellido(participante.getApellido());
        dbParticipante.setNombre(participante.getNombre());
        dbParticipante.setEdad(participante.getEdad());
        //=============
        dbParticipante.setDireccion("Unica");
        dbParticipante.setRol("P");
        dbParticipante.setSexo("M");
        Participante out = this.participanteRepository.save(dbParticipante);

        return out;
    }

}
