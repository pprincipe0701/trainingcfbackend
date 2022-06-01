package com.incloud.hcp.service.notificacion;


import org.apache.commons.mail.EmailException;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Optional;

@Component
public class DemoNotificacion extends NotificarMail {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String TEMPLATE1 = "com/incloud/hcp/templates/portal/TemplateDemoNotificacion.html";
    private final String LOGO_VENDOR = "com/incloud/hcp/templates/img/vendor_logo.png";
    private final String LOGO_PARAMONGA = "com/incloud/hcp/templates/img/sm_logo.png";

    // BEGIN: CODIGO NAIX
   /* @Autowired
    private EmailNotificacionGeneral emailNotificacionGeneral;*/
    // END: CODIGO NAIX

    @Value("${cp.portal.url}")
    private  String urlPortal;

    private final String ASUNTO = "CAPACITACION - AIPSA JAVA MAIL";

    public void enviar(MailSetting mailSetting,

                       String emailDestinatario,
                       String nombreDestinatario
                       ) throws IOException {


        logger.error("RegistroSolicitudProveedorAprobadoNotificacion_emailDestinatario :  " + emailDestinatario);
        logger.error("RegistroSolicitudProveedorAprobadoNotificacion_nombreDestinatario :  " + nombreDestinatario);

        Charset.forName("UTF-8").newEncoder();

        String TEMPLATE = TEMPLATE1;

        String nombreAlumno = "Pavel Principe";
        nombreAlumno = "";


        if (Optional.ofNullable(nombreAlumno).isPresent())
            nombreAlumno = nombreAlumno.replaceAll("(\\r\\n|\\n\\r|\\r|\\n)", "<br />");
        else
            nombreAlumno = "";

        //String razonSocial = "";
        //razonSocial = pre.getRazonSocial();


        if (Optional.ofNullable(nombreDestinatario).isPresent())
            nombreDestinatario = nombreDestinatario.replaceAll("(\\r\\n|\\n\\r|\\r|\\n)", "<br />");
        else
            nombreDestinatario = "";

       /* if (Optional.ofNullable(concepto).isPresent())
            concepto = concepto.replaceAll("(\\r\\n|\\n\\r|\\r|\\n)", "<br />");
        else
            concepto = "";*/

        // BEGIN: CODIGO NAIX
        Mail mail = new Mail();
        VelocityContext context = new VelocityContext();
        //context.put("usuarioPublicacionName", realName);
        //context.put("codigoFactura", facFactura.getTipoFactura() + "-" + facFactura.getSerieFactura() + "-" + facFactura.getNumeroFactura());
        context.put("usuarioNotificacion", nombreDestinatario);
        context.put("nombreAlumno", nombreAlumno);
        context.put("url",this.urlPortal);
        logger.error("RegistroSolicitudProveedorAprobadoNotificacion_mailSetting :  " + mailSetting.getPassword());
        logger.error("RegistroSolicitudProveedorAprobadoNotificacion_mailSetting2 :  " + mailSetting.getUser());
        Optional.ofNullable(generateCid(mail.getHtmlMail(), LOGO_VENDOR))
                .ifPresent(cid -> context.put("vendor_logo", cid));

        Optional.ofNullable(generateCid(mail.getHtmlMail(), LOGO_PARAMONGA))
                .ifPresent(cid -> context.put("sm_logo", cid));

        String content = this.getContentFromTemplateAndContext(context, TEMPLATE);
        //this.emailNotificacionGeneral.enviarCorreoSap(emailDestinatario, ASUNTO, content);
        // END: CODIGO NAIX
        mail.setMailSetting(mailSetting);
        try {
            //String asunto = "Licitación Nro. " + licitacion.getNroLicitacionString()+"- SF";

            mail.enviar(emailDestinatario, null, ASUNTO, content);


        } catch (EmailException ex) {
            logger.error("Error al enviar notificacion", ex);
        }

    }

}
