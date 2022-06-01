package com.incloud.hcp.service;

public interface HorarioService {

 byte[] obtenerByteReporte(Integer idCurso) throws  Exception;
 byte[] obtenerByteReporteExcel(Integer idCurso) throws  Exception;

}
