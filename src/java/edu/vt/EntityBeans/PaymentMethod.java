/*
 * Created by Joshua Detwiler, Melanie Trammell on 2019.05.04  * 
 * Copyright © 2019 Joshua Detwiler, Melanie Trammell. All rights reserved. * 
 */
package edu.vt.EntityBeans;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

// The @Entity annotation designates this class as a JPA Entity class 
// representing the PaymentMethod table in the GreensDB database.
@Entity

// Name of the database table represented
@Table(name = "PaymentMethod")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PaymentMethod.findAll", 
                query = "SELECT p FROM PaymentMethod p")
    , @NamedQuery(name = "PaymentMethod.findById", 
                query = "SELECT p FROM PaymentMethod p WHERE p.id = :id")
    , @NamedQuery(name = "PaymentMethod.findByNameOnCreditCard", 
                query = "SELECT p FROM PaymentMethod p WHERE p.nameOnCreditCard = :nameOnCreditCard")
    , @NamedQuery(name = "PaymentMethod.findByCreditCardNumber", 
                query = "SELECT p FROM PaymentMethod p WHERE p.creditCardNumber = :creditCardNumber")
    , @NamedQuery(name = "PaymentMethod.findByCreditCardType", 
                query = "SELECT p FROM PaymentMethod p WHERE p.creditCardType = :creditCardType")
    , @NamedQuery(name = "PaymentMethod.findByCreditCardExpiration", 
                query = "SELECT p FROM PaymentMethod p WHERE p.creditCardExpiration = :creditCardExpiration")
    , @NamedQuery(name = "PaymentMethod.findByBillingAddress1", 
                query = "SELECT p FROM PaymentMethod p WHERE p.billingAddress1 = :billingAddress1")
    , @NamedQuery(name = "PaymentMethod.findByBillingAddress2", 
                query = "SELECT p FROM PaymentMethod p WHERE p.billingAddress2 = :billingAddress2")
    , @NamedQuery(name = "PaymentMethod.findByBillingCity", 
                query = "SELECT p FROM PaymentMethod p WHERE p.billingCity = :billingCity")
    , @NamedQuery(name = "PaymentMethod.findByBillingState", 
                query = "SELECT p FROM PaymentMethod p WHERE p.billingState = :billingState")
    , @NamedQuery(name = "PaymentMethod.findByBillingZipcode", 
                query = "SELECT p FROM PaymentMethod p WHERE p.billingZipcode = :billingZipcode")
    /* Added functionalities. */
    , @NamedQuery(name = "PaymentMethod.findPaymentMethodsByUserPrimaryKey", 
            query = "SELECT p FROM PaymentMethod p WHERE p.userId.id = :primaryKey")
})

public class PaymentMethod implements Serializable {

    /*
    ========================================================
    Instance variables representing the attributes (columns)
    of the PaymentMethod table in the GreensDB database.
    ========================================================
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name_on_credit_card")
    private String nameOnCreditCard;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 19)
    @Column(name = "credit_card_number")
    private String creditCardNumber;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "credit_card_type")
    private String creditCardType;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "credit_card_expiration")
    private String creditCardExpiration;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "billing_address1")
    private String billingAddress1;
    
    @Size(max = 128)
    @Column(name = "billing_address2")
    private String billingAddress2;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "billing_city")
    private String billingCity;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "billing_state")
    private String billingState;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "billing_zipcode")
    private String billingZipcode;
    
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;

    /*
    =========================================================================
    Class constructors for instantiating a PaymentMethod entity object to
    represent a row in the PaymentMethod table in the GreensDB database.
    =========================================================================
     */
    public PaymentMethod() {
    }

    public PaymentMethod(Integer id) {
        this.id = id;
    }

    public PaymentMethod(Integer id, String nameOnCreditCard, String creditCardNumber, String creditCardType, 
            String creditCardExpiration, String billingAddress1, String billingCity, String billingState, 
            String billingZipcode) {
        this.id = id;
        this.nameOnCreditCard = nameOnCreditCard;
        this.creditCardNumber = creditCardNumber;
        this.creditCardType = creditCardType;
        this.creditCardExpiration = creditCardExpiration;
        this.billingAddress1 = billingAddress1;
        this.billingCity = billingCity;
        this.billingState = billingState;
        this.billingZipcode = billingZipcode;
    }

    /*
    ======================================================
    Getter and Setter methods for the attributes (columns)
    of the PaymentMethod table in the GreensDB database.
    ======================================================
     */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameOnCreditCard() {
        return nameOnCreditCard;
    }

    public void setNameOnCreditCard(String nameOnCreditCard) {
        this.nameOnCreditCard = nameOnCreditCard;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getCreditCardType() {
        return creditCardType;
    }

    public void setCreditCardType(String creditCardType) {
        this.creditCardType = creditCardType;
    }

    public String getCreditCardExpiration() {
        return creditCardExpiration;
    }

    public void setCreditCardExpiration(String creditCardExpiration) {
        this.creditCardExpiration = creditCardExpiration;
    }

    public String getBillingAddress1() {
        return billingAddress1;
    }

    public void setBillingAddress1(String billingAddress1) {
        this.billingAddress1 = billingAddress1;
    }

    public String getBillingAddress2() {
        return billingAddress2;
    }

    public void setBillingAddress2(String billingAddress2) {
        this.billingAddress2 = billingAddress2;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public String getBillingState() {
        return billingState;
    }

    public void setBillingState(String billingState) {
        this.billingState = billingState;
    }

    public String getBillingZipcode() {
        return billingZipcode;
    }

    public void setBillingZipcode(String billingZipcode) {
        this.billingZipcode = billingZipcode;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    /*
    ================
    Instance Methods
    ================
     */
    /**
     * @return Generates and returns a hash code value for the object with id
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Checks if the PaymentMethod object identified by 'object' is the same as 
     * the PaymentMethod object identified by 'id'
     *
     * @param object The PaymentMethod object identified by 'object'
     * @return True if the PaymentMethod 'object' and 'id' are the same; otherwise, return False
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaymentMethod)) {
            return false;
        }
        PaymentMethod other = (PaymentMethod) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        // Convert the PaymentMethod object's database primary key (Integer) to String type and return it.
        return id.toString();
    }
    
}
