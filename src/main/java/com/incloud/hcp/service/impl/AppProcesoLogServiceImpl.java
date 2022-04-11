package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.AppProcesoLog;
import com.incloud.hcp.repository.AppProcesoLogRepository;
import com.incloud.hcp.service.AppProcesoLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class AppProcesoLogServiceImpl implements AppProcesoLogService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AppProcesoLogRepository appProcesoLogRepository;

    /*public AppProcesoLog grabarAppProcesoLog(AppProcesoLogDto dto) throws Exception {
        log.error("grabarAppProcesoLog :: " + dto);
        log.debug("grabarAppProcesoLog :: debug :: " + dto);
        log.info("grabarAppProcesoLog :: info :: " + dto);
        AppProcesoLog app = new AppProcesoLog();
        app.setAncara("uno");
        app.setClaseProgramacion("dos");
        app.setDescripcionEstadoEjecucion("tres");
        app.setDuracionMs(Long.parseLong("4"));
        app.setEstadoEjecucion("6");
        app.setFechaFinEjecucion(DateUtils.obtenerFechaActual());
        app.setFechaInicioEjecucion(DateUtils.obtenerFechaActual());
        app.setLocation("siete");
        app.setMetodoProgramacion("ocho");
        app.setUsuario("nueve");
        AppProcesoLog out = appProcesoLogRepository.save(app);
        return out;
    }*/
    public List<AppProcesoLog> listaAppProcesoLog() {
        return this.appProcesoLogRepository.findAll();
    }

}
