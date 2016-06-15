package controller;

import java.io.Serializable;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@ManagedBean
@SessionScoped
public class LanguageBean implements Serializable {
    @Inject 
    private InvoiceSession session;
    
    private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
    
    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public void setLanguage(String language) {
        this.locale = new Locale(language);
        session.setupDates(this.locale);
    }
}
