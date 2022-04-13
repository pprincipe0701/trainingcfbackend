package com.incloud.hcp.service;

import com.incloud.hcp.domain.Participante;
import com.incloud.hcp.dto.ParticipanteDto;

import java.util.List;

public interface ParticipanteService {

 List<Participante> listaParticipantes();
 Participante crearParticipante(ParticipanteDto participante) throws  Exception;
}
