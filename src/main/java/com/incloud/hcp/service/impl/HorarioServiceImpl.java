package com.incloud.hcp.service.impl;

import com.incloud.hcp.dto.HorarioDto;
import com.incloud.hcp.service.HorarioService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class HorarioServiceImpl implements HorarioService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final String RUTA_JASPER = "reportes/jasper/";
    private final String RUTA_JRXML = "reportes/jrxml/";
    private final String SUFIJO_JASPER = ".jasper";
    private final String SUFIJO_JRXML = ".jrxml";

    public byte[] obtenerByteReporte(Integer idCurso) throws Exception {

        //Obtener los horarios de un determiando curso desde la tabla Horarios de la BD hana

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("curso", "ABAP BASICO");
        parameters.put("costo", "100 USD");
        parameters.put("tituloReporte", "Horarios del curso Training ABAP");

        List<HorarioDto> lista = new ArrayList<>();
        HorarioDto dto2 = new HorarioDto();
        dto2.setFechaInicio("10 de Junio");
        dto2.setFrecuencia("Diario");
        dto2.setNombreCurso("002");
        dto2.setNombreProfesor("Pavel Principe");
        lista.add(dto2);

        HorarioDto dto3 = new HorarioDto();
        dto3.setFechaInicio("10 de Junio");
        dto3.setFrecuencia("Diario");
        dto3.setNombreCurso("003");
        dto3.setNombreProfesor("Pavel Principe");
        lista.add(dto3);

        HorarioDto dto4 = new HorarioDto();
        dto4.setFechaInicio("10 de Junio");
        dto4.setFrecuencia("Diario");
        dto4.setNombreCurso("004");
        dto4.setNombreProfesor("Pavel Principe");
        lista.add(dto4);

        HorarioDto dto5 = new HorarioDto();
        dto5.setFechaInicio("10 de Junio");
        dto5.setFrecuencia("Diario");
        dto5.setNombreCurso("005");
        dto5.setNombreProfesor("Pavel Principe");
        lista.add(dto5);

       //String jrxmlReporte = "reporteFactura";
        //String jasperNombreReporte = RUTA_JASPER + jrxmlReporte + SUFIJO_JASPER;
        String jasperNombreReporte = "reportes/jasper/reporteFactura.jasper";
        ClassPathResource reportResourceReporte = new ClassPathResource(jasperNombreReporte);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lista);
        //JasperReport report = JasperCompileManager.compileReport(filePath);
        InputStream inputStreamReporte = reportResourceReporte.getInputStream();

        JasperPrint print = JasperFillManager.fillReport(inputStreamReporte, parameters, dataSource);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(print, outputStream);
        byte[] reportContent = outputStream.toByteArray();

        return reportContent;
    }

    public byte[] obtenerByteReporteExcel(Integer idCurso) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Java Books");

        Object[][] bookData = {
                {"Head First Java", "Kathy Serria", 79},
                {"Effective Java", "Joshua Bloch", 36},
                {"Clean Code", "Robert martin", 42},
                {"Thinking in Java", "Bruce Eckel", 35},
        };

        int rowCount = 0;

        for (Object[] aBook : bookData) {
            Row row = sheet.createRow(++rowCount);

            int columnCount = 0;

            for (Object field : aBook) {
                Cell cell = row.createCell(++columnCount);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }

        }

        File fileAux = new File("ReporteHorario.xlsx");
        FileOutputStream outputStream = new FileOutputStream(fileAux);
        workbook.write(outputStream);

        InputStream finput = new FileInputStream(fileAux);
        byte[] bytes = toByteArray(finput);

        outputStream.close();
        workbook.close();

        return bytes;

    }

    public  byte[] toByteArray(InputStream in) throws IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int len;

        // read bytes from the input stream and store them in buffer
        while ((len = in.read(buffer)) != -1) {
            // write bytes from the buffer into output stream
            os.write(buffer, 0, len);
        }

        return os.toByteArray();
    }

}
