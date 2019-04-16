/*
 * Created by Melanie Trammell on 2019.04.16  * 
 * Copyright © 2019 Melanie Trammell. All rights reserved. * 
 */
package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.Orders;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Melanie
 */
@Stateless
public class OrdersFacade extends AbstractFacade<Orders> {

    @PersistenceContext(unitName = "GreensPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrdersFacade() {
        super(Orders.class);
    }
    
}
