/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dto.PersonDto;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import service.PersonService;

/**
 * Controller class for login.
 *
 * @author Alexander
 */
@Named
@RequestScoped
public class LoginBean {

    private static final Logger LOGGER = Logger
            .getLogger(LoginBean.class.getName());

    @Inject
    private ExternalContext context;

    @Inject
    private PersonService personService;

    @Inject
    private InvoiceSession invoiceSession;

    private String username;
    private String password;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean isUser() {
        return context.isUserInRole("user");
    }

    public String login() throws IOException {
        try {
            ((HttpServletRequest) this.context.getRequest())
                    .login(this.username, this.password);
        } catch (ServletException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return "/auth/loginError?faces-redirect=true";
        }

        // Get person
        PersonDto person = this.personService.getPerson(username);
        this.invoiceSession.setPerson(person);

        return "/index?faces-redirect=true";
    }

    public String getPrincipalName() {
        return context.getUserPrincipal().getName();
    }

    public String logout() {
        try {
            ((HttpServletRequest) context.getRequest()).logout();

        } catch (ServletException ex) {
            Logger.getLogger(LoginBean.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "/index?faces-redirect=true";
    }
}
