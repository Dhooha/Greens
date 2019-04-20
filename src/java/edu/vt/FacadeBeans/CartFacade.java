/*
 * Created by Melanie Trammell on 2019.04.16  * 
 * Copyright © 2019 Melanie Trammell. All rights reserved. * 
 */
package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.Cart;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Melanie
 */
@Stateless
public class CartFacade extends AbstractFacade<Cart> {

    @PersistenceContext(unitName = "GreensPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CartFacade() {
        super(Cart.class);
    }
    
    /**
     * Find all carts that belong to a User whose database primary key is dbPrimaryKey
     * 
     * @param dbPrimaryKey is the Primary Key of the user entity in the database
     * @return a list of object references of Carts that belong to the user whose database Primary Key = dbPrimaryKey
     */
    public List<Cart> findCartsbyUserId(Integer dbPrimaryKey) {
        /*
        The following @NamedQuery is defined in Cart.java entity class file:
        @NamedQuery(name = "Cart.findCartsByUserPrimaryKey", 
            query = "SELECT c FROM Cart c WHERE c.userId.id = :primaryKey")
        
        The following statement obtaines the results from the named database query.
         */
        List<Cart> carts = em.createNamedQuery("Cart.findCartsByUserPrimaryKey")
                .setParameter("primaryKey", dbPrimaryKey)
                .getResultList();

        return carts;
    }
    
}
