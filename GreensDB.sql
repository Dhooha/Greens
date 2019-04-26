# ---------------------------------------
# SQL script to create the tables for the
# Greens Database (GreensDB)
# Created by Joshua Detwiler
# ---------------------------------------

/*
Tables to be dropped must be listed in a logical order based on dependency.
UserSurvey and UserPhoto depend on User. Therefore, they must be dropped before User.
*/
DROP TABLE IF EXISTS PaymentMethod, Cart, Orders, UserPhoto, User;


/* The User table contains attributes of interest of a User. */
CREATE TABLE User
(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    username VARCHAR(32) NOT NULL,
    password VARCHAR(256) NOT NULL,  /* To store Salted and Hashed Password Parts */
    first_name VARCHAR(50) NOT NULL,
    middle_name VARCHAR(50),
    last_name VARCHAR(50) NOT NULL,
    delivery_address1 VARCHAR(128),
    delivery_address2 VARCHAR(128),
    delivery_city VARCHAR(64),
    delivery_state VARCHAR(2),
    delivery_zipcode VARCHAR(10),    /* e.g., 24060-1804 */
    security_question_number INT NOT NULL,  /* Refers to the number of the selected security question */
    security_answer VARCHAR(128) NOT NULL,
    email VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
);

/* The UserPhoto table contains attributes of interest of a user's photo. */
CREATE TABLE UserPhoto
(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
    extension ENUM('jpeg', 'jpg', 'png', 'gif') NOT NULL,
    user_id INT UNSIGNED NOT NULL,
    FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE
);

/* The Orders table contains past and current customer orders. */
CREATE TABLE Orders /* ORDER is a SQL reserved word */
(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
    order_items MEDIUMTEXT NOT NULL,
    order_type ENUM('DINE_IN', 'CARRY_OUT', 'DELIVERY') NOT NULL,
    order_timestamp DATE NOT NULL,
    order_status ENUM('PLACED', 'READY', 'CLAIMED') NOT NULL,
    order_total FLOAT NOT NULL,
    special_instructions VARCHAR(500),
    text_notification boolean DEFAULT false,
    user_id INT UNSIGNED NOT NULL,
    FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE
);

/* The Cart table contains items that are to be purchased for a user. */
CREATE TABLE Cart
(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
    cart_items MEDIUMTEXT NOT NULL,
    user_id INT UNSIGNED NOT NULL,
    FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE
);

/* The PaymentMethod table contains credit cards for Users. */
CREATE TABLE PaymentMethod
(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name_on_credit_card VARCHAR(100) NOT NULL,
    credit_card_number VARCHAR(19) NOT NULL,    /* longest credit card numbers are 19, internationally */
    credit_card_type ENUM('VISA', 'MASTERCARD', 'DISCOVER') NOT NULL,
    credit_card_expiration VARCHAR(5) NOT NULL, /* MM/DD has 5 characters */
    billing_address1 VARCHAR(128) NOT NULL,
    billing_address2 VARCHAR(128),
    billing_city VARCHAR(64) NOT NULL,
    billing_state VARCHAR(2) NOT NULL,
    billing_zipcode VARCHAR(10) NOT NULL,    /* e.g., 24060-1804 */
    user_id INT UNSIGNED NOT NULL,
    FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE
);