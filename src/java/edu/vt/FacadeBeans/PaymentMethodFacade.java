/*
 * Created by Joshua Detwiler, Melanie Trammell on 2019.05.04  * 
 * Copyright © 2019 Joshua Detwiler, Melanie Trammell. All rights reserved. * 
 */
package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.PaymentMethod;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PaymentMethodFacade extends AbstractFacade<PaymentMethod> {

    @PersistenceContext(unitName = "GreensPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PaymentMethodFacade() {
        super(PaymentMethod.class);
    }
    
    /*
    ====================================================
    The following method is added to the generated code.
    ====================================================
     */
    /**
     * @param primaryKey is the Primary Key of the User entity in a table row in the database.
     * @return a list of payment methods associated with the User whose primary key is primaryKey
     */
    public List<PaymentMethod> findPaymentMethodsByUserPrimaryKey(Integer primaryKey) {

        return (List<PaymentMethod>) em.createNamedQuery("PaymentMethod.findPaymentMethodsByUserPrimaryKey")
                .setParameter("primaryKey", primaryKey)
                .getResultList();
    }

    /* The following methods are inherited from the parent AbstractFacade class:
    
        create
        edit
        find
        findAll
        remove
     */
    
    
}
