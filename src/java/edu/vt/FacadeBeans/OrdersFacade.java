/*
 * Created by Melanie Trammell on 2019.04.16  * 
 * Copyright © 2019 Melanie Trammell. All rights reserved. * 
 */
package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.Orders;
import java.util.List;
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
    
            /**
     * Find all surveys that belong to a User whose database primary key is dbPrimaryKey
     * 
     * @param dbPrimaryKey is the Primary Key of the user entity in the database
     * @return a list of object references of userQuestionnaires that belong to the user whose database Primary Key = dbPrimaryKey
     */
    public List<Orders> findOrdersbyUserId(Integer dbPrimaryKey) {
        /*
        The following @NamedQuery is defined in UserQuestionnaire.java entity class file:
        @NamedQuery(name = "UserQuestionnaire.findQuestionnairesByUserPrimaryKey", 
            query = "SELECT u FROM UserQuestionnaire u WHERE u.userId.id = :primaryKey")
        
        The following statement obtaines the results from the named database query.
         */
        List<Orders> orders = em.createNamedQuery("Orders.findOrdersByUserPrimaryKey")
                .setParameter("primaryKey", dbPrimaryKey)
                .getResultList();

        return orders;
    }
    
}
