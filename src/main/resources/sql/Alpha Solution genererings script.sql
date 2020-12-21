-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema alfasolutionsdb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema alfasolutionsdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `alfasolutionsdb` DEFAULT CHARACTER SET utf8 ;
USE `alfasolutionsdb` ;

-- -----------------------------------------------------
-- Table `alfasolutionsdb`.`userinfo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `alfasolutionsdb`.`userinfo` (
  `iduserinfo` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(100) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `usertype` VARCHAR(45) NULL DEFAULT 'user',
  PRIMARY KEY (`iduserinfo`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `alfasolutionsdb`.`project`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `alfasolutionsdb`.`project` (
  `idproject` INT(11) NOT NULL AUTO_INCREMENT,
  `projectname` VARCHAR(45) NOT NULL,
  `ownername` VARCHAR(100) NOT NULL,
  `startdate` VARCHAR(45) NOT NULL,
  `deadlinedate` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idproject`),
  INDEX `FKProjectUserInfo_idx` (`ownername` ASC) VISIBLE,
  CONSTRAINT `FKPojectuser`
    FOREIGN KEY (`ownername`)
    REFERENCES `alfasolutionsdb`.`userinfo` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `alfasolutionsdb`.`task`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `alfasolutionsdb`.`task` (
  `idtask` INT(11) NOT NULL AUTO_INCREMENT,
  `projectid` INT(11) NOT NULL,
  `taskno` DECIMAL(5,2) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `startdate` DATE NOT NULL,
  `finishdate` DATE NULL DEFAULT NULL,
  `duration` SMALLINT(6) NULL DEFAULT NULL,
  `isSubTask` VARCHAR(5) NOT NULL,
  `tasktimeconsumption` INT(11) NOT NULL,
  `noOfPersons` INT(11) NULL DEFAULT NULL,
  `workinghoursday` DOUBLE NULL DEFAULT NULL,
  PRIMARY KEY (`idtask`),
  INDEX `FKprojecttask_idx` (`projectid` ASC) VISIBLE,
  CONSTRAINT `FKprojecttask`
    FOREIGN KEY (`projectid`)
    REFERENCES `alfasolutionsdb`.`project` (`idproject`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 19
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
