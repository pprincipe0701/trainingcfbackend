package com.incloud.hcp.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.incloud.hcp.domain.Participante;
import com.incloud.hcp.dto.*;
import com.incloud.hcp.service.AppProcesoLogService;
import com.incloud.hcp.service.HorarioService;
import com.incloud.hcp.service.NativeSQLRunner;
import com.incloud.hcp.service.ParticipanteService;
import com.incloud.hcp.service.notificacion.DemoNotificacion;
import com.incloud.hcp.service.notificacion.MailSetting;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
public class TestRest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    NativeSQLRunner runner_native_sql;

    @Autowired
    AppProcesoLogService appProcesoLogService;

    @Autowired
    ParticipanteService participanteService;

    @Autowired
    private DemoNotificacion demoNotificacion;

    @Autowired
    private HorarioService horarioService;

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

    @GetMapping(value = "/listaParticipantes", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Participante>> listaParticipantes() {
        List<Participante> lista = new ArrayList<Participante>();

        try {
            lista = this.participanteService.listaParticipantes();
            return Optional.ofNullable(lista)
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }


    @PostMapping(value = "/grabarParticipante", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Participante> grabarProcesoLog(@RequestBody ParticipanteDto dto) throws URISyntaxException {
        log.debug("Lista Grupo Articulos : {}");
        Participante participante = null;
        try {
            participante = this.participanteService.crearParticipante(dto);
            return Optional.ofNullable(participante)
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException("" + e.toString());
        }
    }

    @PostMapping(value = "/listaParticipanteMyBatis", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Participante>> listaParticipanteMyBatis(@RequestBody ParticipanteDto dto) throws URISyntaxException {
        log.debug("Lista Grupo Articulos : {}");
        log.info("listaParticipanteMyBatis_INPUT:: " + dto);
        List<Participante> lista = new ArrayList<Participante>();
        try {
            lista = this.participanteService.listaParticipantesByMyBatis(dto.getNombre(), dto.getApellido(), dto.getEdad());
            log.info("listaParticipanteMyBatis_OUTPUT:: " + lista);
            return Optional.ofNullable(lista)
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException("" + e.toString());
        }
    }

    @GetMapping(value = "/pruebaConsumoRest", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UsuarioDto>> pruebaConsumoRest() throws URISyntaxException {
        log.debug("pruebaConsumoRest : {}");
        log.info("pruebaConsumoRest_INPUT:: ");
        List<UsuarioDto> lista = new ArrayList<UsuarioDto>();
        try {

            String url = "https://gorest.co.in/public/v2/users";//prd

            //Obtener token
            // UsuarioDto dto = null;
            OkHttpClient httpclient = new OkHttpClient();
            Request request = new Request.Builder().url(url).get().addHeader("Content-Type", "application/json").build();
            Response response = httpclient.newCall(request).execute();
            //String resultToken = responseToken.body().string();

            ObjectMapper mapper = new ObjectMapper();

            //dto = mapperToken.readValue(resultToken, UsuarioDto.class);
            UsuarioDto[] usuarios = mapper.readValue(response.body().bytes(), UsuarioDto[].class);
            lista = Arrays.asList(usuarios);


            return Optional.ofNullable(lista)
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException("" + e.toString());
        }
    }

    @GetMapping(value = "/pruebaOperacionWs/{numero1}/{numero2}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageSapDto> pruebaOperacionWs(@PathVariable Integer numero1, @PathVariable Integer numero2) throws URISyntaxException {
        log.debug("pruebaOperacionWs : {}");
        log.info("pruebaOperacionWs_INPUT:: " + numero1 + "__ " + numero2);
        MessageSapDto msg = new MessageSapDto();
        try {

            String urlValue = "http://www.dneonline.com/calculator.asmx";
            String authBasic = "";

            //listaHana.get(0).set
            String bodyRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">";
            bodyRequest = bodyRequest + "<soapenv:Header/>";
            bodyRequest = bodyRequest + "<soapenv:Body>";
            bodyRequest = bodyRequest + "<tem:Divide>";
            bodyRequest = bodyRequest + "<tem:intA>" + numero1 + "</tem:intA>";
            bodyRequest = bodyRequest + "<tem:intB>" + numero2 + "</tem:intB>";
            bodyRequest = bodyRequest + "</tem:Divide>";
            bodyRequest = bodyRequest + "</soapenv:Body>";
            bodyRequest = bodyRequest + "</soapenv:Envelope>";



            OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build();
            MediaType mediaType = MediaType.parse("text/xml");

            okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, bodyRequest);

            Request request = new Request.Builder().url(urlValue).post(body).addHeader("content-type", "text/xml")
                    .addHeader("Authorization", authBasic).build();


            String strr = "";

            Pattern patterntag = Pattern.compile("<DivideResult.*?>(.*?)<\\/DivideResult>");

            Response response = client.newCall(request).execute();
            strr = response.body().string();

            Matcher match = patterntag.matcher(strr);
            int indiceNatural = 0;
            while (match.find()) {
                String strMsg = match.group(1);
                msg.setType("S");
                msg.setMessage("El Resultado es : " + strMsg);
            }


            return Optional.ofNullable(msg)
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException("" + e.toString());
        }
    }
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = {"/reporte/horario/{idCurso}"},
            method = RequestMethod.GET)
    public HttpEntity<byte[]> downloadReportePdf(@PathVariable("idCurso") Integer idCurso, HttpServletRequest request) throws Exception{

        byte[] excelContent = this.horarioService.obtenerByteReporte(idCurso);
        /*try {
            excelContent = toByteArray(input);
        } catch (Exception e) {

        }*/

        // prepare response
        //String[] mimes = doc.getContentStreamMimeType().split("/");
        log.info("downloadReportePdf:: " + excelContent.length);
        HttpHeaders header = new HttpHeaders();

        header.setContentType(org.springframework.http.MediaType.parseMediaType("application/octet-stream"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ReporteHorario.pdf");
        header.setContentLength(excelContent.length);

        return new HttpEntity<byte[]>(excelContent, header);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = {"/reporteExcel/horario/{idCurso}"},
            method = RequestMethod.GET)
    public HttpEntity<byte[]> downloadReporteExcel(@PathVariable("idCurso") Integer idCurso, HttpServletRequest request) throws Exception{

        byte[] excelContent = this.horarioService.obtenerByteReporteExcel(idCurso);
        /*try {
            excelContent = toByteArray(input);
        } catch (Exception e) {

        }*/

        // prepare response
        //String[] mimes = doc.getContentStreamMimeType().split("/");
        log.info("downloadReporteExcel:: " + excelContent.length);
        HttpHeaders header = new HttpHeaders();

        header.setContentType(org.springframework.http.MediaType.parseMediaType("application/octet-stream"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ReporteHorario.xlsx");
        header.setContentLength(excelContent.length);

        return new HttpEntity<byte[]>(excelContent, header);
    }

    @PostMapping(value = "/demoEnvioJavaMail", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageSapDto> listaParticipanteMyBatis(@RequestBody SendMailDto dto) throws URISyntaxException {
        //og.debug("Lista Grupo Articulos : {}");
        log.info("demoEnvioJavaMail_INPUT:: " + dto);
        MessageSapDto msg = new MessageSapDto();
        try {
            MailSetting mailSetting = new MailSetting();
            mailSetting.setEmailFrom("pprincipe.ti@gmail.com");
            mailSetting.setNameFrom("Pavel Principex");
            mailSetting.setUser("pprincipe.ti@gmail.com");
            mailSetting.setPassword("joakrqvkmvmomoig");
            mailSetting.setHost("smtp.gmail.com");
            mailSetting.setPort("587");

            demoNotificacion.enviar(mailSetting,dto.getEmail(), dto.getNombre());
            msg.setType("S");
            msg.setMessage("Se envio el correo correctamente");
            return Optional.ofNullable(msg)
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException("" + e.toString());
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
