package com.java_project.jpa_code.service;


import com.java_project.jpa_code.dto.Email;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * Servicio para enviar correos electrónicos.
 * Se utiliza mailtrap para simular el envío de correos electrónicos.
 * Se requiere configurar las credenciales de mailtrap en el archivo application.properties.
 */
@Service
@RequiredArgsConstructor
public class EmailService {

    @Autowired
    private Environment env;
    private Session session;

    // Mailtrap es un servicio que simula un servidor de correo
    // y permite probar y depurar el envío de correos electrónicos
    // sin enviar correos electrónicos reales a los destinatarios.

    /**
     * Inicializa la sesión de correo.
     */
    public void initSesion() {
        // Usuario y contraseña de Mailtrap
        final String username = env.getProperty("mailstrap.username");
        final String password =  env.getProperty("mailstrap.password");
        
        // Configuración de Mailtrap
        Properties props = new Properties();
        props.put("mail.smtp.auth",  env.getProperty("mailstrap.props_smtp.auth"));
        props.put("mail.smtp.starttls.enable", env.getProperty("mailstrap.props_mail_smtp_starttls_enable"));

        // Configuración del host y puerto de Mailtrap
        props.put("mail.smtp.host", env.getProperty("mailstrap.host"));
        props.put("mail.smtp.port", env.getProperty("mailstrap.props_mail_smtp_port"));

        // Crear un objeto de sesión
        session = Session.getInstance(props,
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    /**
     * Enviar un correo electrónico.
     * @param mail Correo electrónico a enviar.
     */
    public void sendMail(Email mail) {

        initSesion();
        try {
            // Crear un objeto de mensaje
            Message message = new MimeMessage(session);
            // Establecer el campo De
            message.setFrom(new InternetAddress(mail.getFrom()));
            // Establecer el campo Para
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(mail.getTo()));
            // Establecer el asunto del mensaje de correo
            message.setSubject(mail.getSubject());
            // Establecer el contenido del mensaje de correo
            message.setText(mail.getContent());
            // Enviar el mensaje de correo
            System.out.println("sendMail antes de Transport; " + mail.getContent());
            Transport.send(message);
            System.out.println("Email Message Sent Successfully");
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}