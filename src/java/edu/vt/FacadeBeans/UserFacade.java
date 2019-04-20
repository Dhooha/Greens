/*
 * Created by Melanie Trammell on 2019.04.16  * 
 * Copyright © 2019 Melanie Trammell. All rights reserved. * 
 */
package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Melanie
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "GreensPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }
    
    
    /*
    ======================================================
    The following methods are added to the generated code.
    ======================================================
     */
    
    /**
     * @param username is the username attribute (column) value of the user
     * @return object reference of the User entity whose user name is username
     */
    public User findByUsername(String username) {
        if (em.createQuery("SELECT c FROM User c WHERE c.username = :username")
                .setParameter("username", username)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (User) (em.createQuery("SELECT c FROM User c WHERE c.username = :username")
                    .setParameter("username", username)
                    .getSingleResult());
        }
    }
    
}
