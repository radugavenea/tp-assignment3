-- MySQL Script generated by MySQL Workbench
-- Fri Apr 21 17:21:12 2017
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema warehouse
-- -----------------------------------------------------

DROP SCHEMA IF EXISTS `warehouse`; 

-- -----------------------------------------------------
-- Schema warehouse
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `warehouse` DEFAULT CHARACTER SET utf8 ;
USE `warehouse` ;

-- -----------------------------------------------------
-- Table `warehouse`.`customerEntity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `warehouse`.`customerEntity` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL,
  `address` VARCHAR(255) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `warehouse`.`productEntity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `warehouse`.`productEntity` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL,
  `stock` INT NULL,
  `price` FLOAT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `warehouse`.`orderEntity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `warehouse`.`orderEntity` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `orderNumber` VARCHAR(45) NULL,
  `customerId` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_order_customer1_idx` (`customerId` ASC),
  CONSTRAINT `fk_order_customer1`
    FOREIGN KEY (`customerId`)
    REFERENCES `warehouse`.`customerEntity` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `warehouse`.`orderProductEntity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `warehouse`.`orderProductEntity` (
  `orderId` INT NOT NULL,
  `productId` INT NOT NULL,
  `quantity` INT NULL,
  PRIMARY KEY (`orderId`, `productId`),
  INDEX `fk_order_has_product_product1_idx` (`productId` ASC),
  INDEX `fk_order_has_product_order_idx` (`orderId` ASC),
  CONSTRAINT `fk_order_has_product_order`
    FOREIGN KEY (`orderId`)
    REFERENCES `warehouse`.`orderEntity` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_has_product_product1`
    FOREIGN KEY (`productId`)
    REFERENCES `warehouse`.`productEntity` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;



INSERT INTO `warehouse`.`customerEntity` (`id`, `name`, `address`) VALUES ('1', 'Mirel Zeama', 'Baritiu 26');
INSERT INTO `warehouse`.`customerEntity` (`id`, `name`, `address`) VALUES ('2', 'Petre Joaca-Bine', 'Baritiu 28');
INSERT INTO `warehouse`.`customerEntity` (`id`, `name`, `address`) VALUES ('3', 'Gigel Nu', 'Baritiu 26');
INSERT INTO `warehouse`.`customerEntity` (`id`, `name`, `address`) VALUES ('4', 'Petronela Da', 'la ea acasa');
INSERT INTO `warehouse`.`customerEntity` (`id`, `name`, `address`) VALUES ('5', 'Eugenia Bla', 'o adresa');
INSERT INTO `warehouse`.`customerEntity` (`id`, `name`, `address`) VALUES ('6', 'Georgica Buletin', 'Titulescu 29');

INSERT INTO `warehouse`.`productEntity` (`id`, `name`, `stock`, `price`) VALUES ('1', 'Moka Pot', '43', '54.95');
INSERT INTO `warehouse`.`productEntity` (`id`, `name`, `stock`, `price`) VALUES ('2', 'Aeropress', '23', '210');
INSERT INTO `warehouse`.`productEntity` (`id`, `name`, `stock`, `price`) VALUES ('3', 'Latiera', '98', '102.5');
INSERT INTO `warehouse`.`productEntity` (`id`, `name`, `stock`, `price`) VALUES ('4', 'V60', '123', '105');
INSERT INTO `warehouse`.`productEntity` (`id`, `name`, `stock`, `price`) VALUES ('5', 'Lingurita', '234', '5.5');
INSERT INTO `warehouse`.`productEntity` (`id`, `name`, `stock`, `price`) VALUES ('6', 'Rasnita cafea', '122', '82.5');
INSERT INTO `warehouse`.`productEntity` (`id`, `name`, `stock`, `price`) VALUES ('7', 'French Press', '222', '132.5');






