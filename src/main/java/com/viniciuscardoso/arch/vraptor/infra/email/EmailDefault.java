package com.viniciuscardoso.arch.vraptor.infra.email;


import com.viniciuscardoso.arch.vraptor.domain.common.AbstractEntity;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import javax.servlet.ServletContext;
import java.io.File;

public abstract class EmailDefault<T extends AbstractEntity> {

    // <editor-fold desc="Atributos e Construtores">
    private HtmlEmail email;
    private ServletContext context;
    private String[] dest;

    public EmailDefault(String subject, ServletContext context) throws EmailException {
        this.context = context;
        this.email = new HtmlEmail();
        this.email.setHostName("mail.transformaresiduos.com.br");
        this.email.setSmtpPort(26);
        this.email.setAuthenticator(new DefaultAuthenticator("naoresponda@transformaresiduos.com.br", "admin102030"));
        this.email.setFrom("naoresponda@transformaresiduos.com.br", "Transforma GDR");
        this.email.setCharset("UTF-8");
        this.email.setSubject(subject);
    }
    // </editor-fold>

    // <editor-fold desc="Métodos Protegidos">
    protected HtmlEmail getEmailInstance() {
        return this.email;
    }

    protected String getTemplateEmailHeader() throws EmailException {
        String absPath = this.context.getRealPath("/img/email/topo.jpg");
        String topImage = this.email.embed(new File(absPath));

        StringBuilder s = new StringBuilder();
        s.append("<html>");
        s.append("<table align=\"center\" id=\"mail_cli_ctf\" width=\"510\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
        s.append("<tr>");
        s.append("<td colspan=\"3\">");
        s.append("<img src=\"cid:").append(topImage).append("\" />");
        s.append("</td>");
        s.append("</tr>");
        s.append("<tr>");
        s.append("<td colspan=\"3\" bgcolor=\"#eeede3\" height=\"20\"></td>");
        s.append("</tr>");
        s.append("<tr>");
        s.append("<td bgcolor=\"#eeede3\" width=\"20\"></td>");
        s.append("<td bgcolor=\"#eeede3\" width=\"470\">");
        s.append("<p style=\"font-size: 12px; font-family: arial, sans-serif; line-height: 20px;\">");
        return s.toString();
    }

    protected String getTemplateEmailFooter() throws EmailException {
        String absPath = this.context.getRealPath("/img/email/baixo.jpg");
        String bottomImage = this.email.embed(new File(absPath));

        StringBuilder s = new StringBuilder();
        s.append("</p>");
        s.append("</td>");
        s.append("<td bgcolor=\"#eeede3\" width=\"20\"></td>");
        s.append("</tr>");
        s.append("<tr>");
        s.append("<td colspan=\"3\" bgcolor=\"#eeede3\" height=\"20\"></td>");
        s.append("</tr>");
        s.append("<tr>");
        s.append("<td colspan=\"3\">");
        s.append("<img src=\"cid:").append(bottomImage).append("\" />");
        s.append("</td>");
        s.append("</tr>");
        s.append("</table>");
        s.append("</html>");
        return s.toString();
    }

    protected void adicionarDestinatarios(String[] dest) throws EmailException {
        this.email.addBcc(dest);
        this.dest = dest;
    }

    protected void sendEmail() throws EmailException {
        try {
            this.email.send();
        } catch (EmailException e) {
            throw new EmailException("Ocorreu um erro ao enviar o(s) e-mail(s). Causa: " + e.getMessage());
        }
    }

    protected void setSubject(String subject) {
        this.email.setSubject(subject);
    }
    // </editor-fold>

    // <editor-fold desc="Métodos Abstratos">
    public abstract void sendEmail(T entity, String[] recipients) throws EmailException;

    protected abstract String criarMensagemEmailAtualizacaoTexto(T entity) throws EmailException;

    protected abstract String criarMensagemEmailAtualizacaoHtml(T entity) throws EmailException;
    // </editor-fold>
}
