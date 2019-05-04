/*
 * Created by Melanie Trammell on 2019.04.16  * 
 * Copyright © 2019 Melanie Trammell. All rights reserved. * 
 */
package edu.vt.EntityBeans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Melanie
 */
@Entity
@Table(name = "Orders")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o")
    , @NamedQuery(name = "Orders.findById", query = "SELECT o FROM Orders o WHERE o.id = :id")
    , @NamedQuery(name = "Orders.findByOrderType", query = "SELECT o FROM Orders o WHERE o.orderType = :orderType")
    , @NamedQuery(name = "Orders.findByOrderTimestamp", query = "SELECT o FROM Orders o WHERE o.orderTimestamp = :orderTimestamp")
    , @NamedQuery(name = "Orders.findByOrderStatus", query = "SELECT o FROM Orders o WHERE o.orderStatus = :orderStatus")
    , @NamedQuery(name = "Orders.findByOrderTotal", query = "SELECT o FROM Orders o WHERE o.orderTotal = :orderTotal")
    , @NamedQuery(name = "Orders.findBySpecialInstructions", query = "SELECT o FROM Orders o WHERE o.specialInstructions = :specialInstructions")
    ,@NamedQuery(name = "Orders.findOrdersByUserPrimaryKey", query = "SELECT o FROM Orders o WHERE o.userId.id = :primaryKey")
})
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    //A string that has Json data
    //Example is:
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 16777215)
    @Column(name = "order_items")
    private String orderItems;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "order_type")
    private String orderType;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "order_timestamp")
    @Temporal(TemporalType.DATE)
    private Date orderTimestamp;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "order_status")
    private String orderStatus;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "order_total")
    private float orderTotal;
    
   //new column in table, April
    @Size(min = 1, max = 500)
    @Column(name = "special_instructions")
    private String specialInstructions;
    
    //new column in table, April 23
    @Basic(optional = false)
    @NotNull
    @Column(name = "text_notification")
    private boolean textNotification;    
    
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;

    public Orders() {
    }

    public Orders(Integer id) {
        this.id = id;
    }

    public Orders(Integer id, User userId, String orderItems, String orderType, Date orderTimestamp, String orderStatus, float orderTotal, String specialInstructions, boolean textNotification) {
        this.id = id;
        this.userId = userId;
        this.orderItems = orderItems;
        this.orderType = orderType;
        this.orderTimestamp = orderTimestamp;
        this.orderStatus = orderStatus;
        this.orderTotal = orderTotal;
        this.specialInstructions = specialInstructions;
        this.textNotification = textNotification;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(String orderItems) {
        this.orderItems = orderItems;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Date getOrderTimestamp() {
        return orderTimestamp;
    }

    public void setOrderTimestamp(Date orderTimestamp) {
        this.orderTimestamp = orderTimestamp;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public float getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(float orderTotal) {
        this.orderTotal = orderTotal;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
    
    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }
    
    public boolean getTextNotification() {
        return textNotification;
    }

    public void setTextNotification(boolean textNotification) {
        this.textNotification = textNotification;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orders)) {
            return false;
        }
        Orders other = (Orders) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.vt.EntityBeans.Orders[ id=" + id + " ]";
    }
    
}
