package com.incloud.hcp.service.notificacion;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.StringWriter;
import java.util.Optional;

/**
 * Created by Administrador on 13/11/2017.
 */


public class NotificarMail {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected VelocityEngine velocityEngine = this.getVelocityEngine();
    public NotificarMail() {
    }

    VelocityEngine getVelocityEngine() {
        logger.debug("Inicialización de VelocityEngine");
        VelocityEngine velocity = new VelocityEngine();
        // COMENTARIO NAIX
        /*
        velocity.setProperty(Velocity.RESOURCE_LOADER, "classpath");
        velocity.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        */
        // BEGIN: CODIGO NAIX
        velocity.setProperty(Velocity.RESOURCE_LOADER, "class");
        velocity.setProperty("class.resource.loader.class", ClasspathResourceLoader.class.getName());
        // END: CODIGO NAIX
        velocity.setProperty("input.encoding", "UTF-8");
        velocity.init();
        return velocity;
    }

    protected String getContentFromTemplateAndContext(VelocityContext context, String template) {
        String content = Optional.ofNullable(template)
                .map(temp -> {
                    return getContentMail(context, temp);
                })
                .orElse("");
        content = content.replaceAll("\"", "'")
                .replaceAll(",", "&#44;")
                .replaceAll(">[\\n\\s]+<", "><")
                .replaceAll("(?m)^\\s+", "")
                .replaceAll("\\r\\n", " ");
        return content;
    }

    protected String getContentMail(VelocityContext context, String template) {
        logger.debug("Generando el contenido de la notificación");
        VelocityEngine velocity = this.velocityEngine;
        Template t = velocity.getTemplate(template);
        StringWriter w = new StringWriter();
        t.merge(context, w);
        return w.toString();
    }

    protected String generateCid(HtmlEmail htmlMail, String imagen) {

        return Optional.ofNullable(NotificarMail.class.getClassLoader().getResource(imagen))
                .map(url -> {
                    logger.debug("Obteniendo el path de la imagen ");
                    logger.debug("url inicial : " + url);
                    String aux = url + "";
                    aux = aux.replace("file:", "");
                    logger.debug("La ruta de la imagen es la siguiente " + aux);
                    return aux;
                })
                .map(p -> new File(p))
                .map(f -> {
                    try {
                        logger.debug("Colocando la imagen en el contenido HTML");
                        String cid = htmlMail.embed(f);
                        return cid;
                    } catch (EmailException ex) {
                        logger.error("Error al colocar la imagen en el contenido HTML", ex);
                        return null;
                    }
                }).orElse(null);

    }

    public void enviarCorreoSap(String paraCorreo, String asuntoCorreo, String bodyCorreo) {

        logger.error("Ingresando enviarCorreoSap OK FIN");
    }
}