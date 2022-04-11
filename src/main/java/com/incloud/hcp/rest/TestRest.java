package com.incloud.hcp.rest;

import com.incloud.hcp.dto.DemoDto;
import com.incloud.hcp.service.AppProcesoLogService;
import com.incloud.hcp.service.NativeSQLRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
public class TestRest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    NativeSQLRunner runner_native_sql;

    @Autowired
    AppProcesoLogService appProcesoLogService;

    /*@Autowired
    AppProcesoLogMapper appProcesoLogMapper;*/


    @GetMapping(value = "/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<DemoDto> hello() {
        DemoDto dto = new DemoDto();
        dto.setAccion("saludo");
        dto.setValor("Hello!");
        try {
            return Optional.ofNullable(dto)
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

        //return "Hello!";
    }


    @GetMapping(value = "/test_native_sql", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<DemoDto> test_native_sql() {
        DemoDto dto = new DemoDto();
        dto.setAccion("ejecucion query");
        dto.setValor("Se ejecuto query JOB  Satifactoriamente!");
        try {
            runner_native_sql.startTest();
            return Optional.ofNullable(dto)
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }

    /*@ApiOperation(value = "Lista de log de procesos", produces = "application/json")
    @GetMapping(value = "/listaProcesos", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppProcesoLog>> listaProcesos() {
        List<AppProcesoLog> lista = new ArrayList<AppProcesoLog>();
        try {
            lista = this.appProcesoLogService.listaAppProcesoLog();
            return Optional.ofNullable(lista)
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {

            throw new RuntimeException(e.toString());
        }


    }*/

    /*@ApiOperation(value = "Grabar PROCESO LOG", produces = "application/json")
    @PostMapping(value = "/grabarProcesoLog", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity <AppProcesoLog> grabarProcesoLog(@RequestBody AppProcesoLogDto dto) throws URISyntaxException {
        log.debug("Lista Grupo Articulos : {}");
        AppProcesoLog log = null;
        try {
            log = this.appProcesoLogService.grabarAppProcesoLog(dto);
            return Optional.ofNullable(log)
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }*/

    /*@ApiOperation(value = "Lista de log de procesos pruena mapper", produces = "application/json")
    @GetMapping(value = "/listaProcesosMapper/{claseProgramacion}/{usuario}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppProcesoLog>> listaProcesosMapper(@PathVariable String claseProgramacion, @PathVariable String usuario) {
        List<AppProcesoLog> lista = new ArrayList<AppProcesoLog>();
        try {
            lista = this.appProcesoLogMapper.getAppProcesoLog(claseProgramacion, usuario);
            return Optional.ofNullable(lista)
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {

            throw new RuntimeException(e.toString());
        }


    }*/


}