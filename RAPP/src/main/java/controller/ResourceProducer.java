/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Alexander
 */
@Stateless
public class ResourceProducer {
    @Produces @Default
    public ExternalContext getContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }
}
