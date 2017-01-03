-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema caogen
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema caogen
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `caogen` DEFAULT CHARACTER SET utf8 ;
USE `caogen` ;

-- -----------------------------------------------------
-- Table `caogen`.`persistent_logins`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `caogen`.`persistent_logins` ;

CREATE TABLE IF NOT EXISTS `caogen`.`persistent_logins` (
  `username` VARCHAR(64) NOT NULL,
  `series` VARCHAR(64) NOT NULL,
  `token` VARCHAR(64) NOT NULL,
  `last_used` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `caogen`.`t_sys_param`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `caogen`.`t_sys_param` ;

CREATE TABLE IF NOT EXISTS `caogen`.`t_sys_param` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` VARCHAR(255) NOT NULL COMMENT '名称',
  `value` VARCHAR(255) NOT NULL COMMENT '父角色id',
  `remark` VARCHAR(100) NULL DEFAULT NULL COMMENT '备注',
  `ctime` BIGINT(15) NULL DEFAULT NULL COMMENT '创建时间',
  `mtime` BIGINT(15) NULL DEFAULT NULL COMMENT '最后更新时间',
  `rstatus` TINYINT(2) NULL DEFAULT NULL COMMENT '状态 0:正常 1:删除',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8
COMMENT = '参数表';


-- -----------------------------------------------------
-- Table `caogen`.`t_sys_resource`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `caogen`.`t_sys_resource` ;

CREATE TABLE IF NOT EXISTS `caogen`.`t_sys_resource` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` VARCHAR(255) NOT NULL COMMENT '名称',
  `icon` VARCHAR(10) NULL DEFAULT 'icon-sys' COMMENT '图标',
  `link` VARCHAR(50) NOT NULL DEFAULT '-1' COMMENT '资源url',
  `pid` BIGINT(20) NOT NULL DEFAULT '0' COMMENT '父资源id',
  `remark` VARCHAR(100) NULL DEFAULT NULL COMMENT '备注',
  `ctime` BIGINT(15) NULL DEFAULT NULL COMMENT '创建时间',
  `mtime` BIGINT(15) NULL DEFAULT NULL COMMENT '最后更新时间',
  `rstatus` TINYINT(2) NULL DEFAULT NULL COMMENT '状态 0:正常 1:删除',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 16
DEFAULT CHARACTER SET = utf8
COMMENT = '资源表';


-- -----------------------------------------------------
-- Table `caogen`.`t_sys_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `caogen`.`t_sys_role` ;

CREATE TABLE IF NOT EXISTS `caogen`.`t_sys_role` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` VARCHAR(255) NOT NULL COMMENT '名称',
  `pid` BIGINT(20) NOT NULL DEFAULT '0' COMMENT '父角色id',
  `remark` VARCHAR(100) NULL DEFAULT NULL COMMENT '备注',
  `ctime` BIGINT(15) NULL DEFAULT NULL COMMENT '创建时间',
  `mtime` BIGINT(15) NULL DEFAULT NULL COMMENT '最后更新时间',
  `rstatus` TINYINT(2) NULL DEFAULT NULL COMMENT '状态 0:正常 1:删除',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 27
DEFAULT CHARACTER SET = utf8
COMMENT = '角色表';


-- -----------------------------------------------------
-- Table `caogen`.`t_sys_role_resource_link`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `caogen`.`t_sys_role_resource_link` ;

CREATE TABLE IF NOT EXISTS `caogen`.`t_sys_role_resource_link` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `role_id` BIGINT(20) NOT NULL COMMENT '角色id',
  `resource_id` BIGINT(20) NOT NULL COMMENT '用户id',
  `remark` VARCHAR(100) NULL DEFAULT NULL COMMENT '备注',
  `ctime` BIGINT(15) NULL DEFAULT NULL COMMENT '创建时间',
  `mtime` BIGINT(15) NULL DEFAULT NULL COMMENT '最后更新时间',
  `rstatus` TINYINT(2) NULL DEFAULT NULL COMMENT '状态 0:正常 1:删除',
  PRIMARY KEY (`id`),
  INDEX `fk_link_role_resource_resource_id_idx` (`resource_id` ASC),
  INDEX `fk_link_role_resource_role_id` (`role_id` ASC),
  CONSTRAINT `fk_link_role_resource_resource_id`
    FOREIGN KEY (`resource_id`)
    REFERENCES `caogen`.`t_sys_resource` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_link_role_resource_role_id`
    FOREIGN KEY (`role_id`)
    REFERENCES `caogen`.`t_sys_role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 88
DEFAULT CHARACTER SET = utf8
COMMENT = '角色、资源关系表';


-- -----------------------------------------------------
-- Table `caogen`.`t_sys_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `caogen`.`t_sys_user` ;

CREATE TABLE IF NOT EXISTS `caogen`.`t_sys_user` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `username` VARCHAR(255) NOT NULL COMMENT '名称',
  `password` VARCHAR(512) NOT NULL COMMENT '密码',
  `department` VARCHAR(45) NULL DEFAULT NULL COMMENT '部门',
  `phone` VARCHAR(45) NULL DEFAULT NULL COMMENT '手机号',
  `position` VARCHAR(45) NULL DEFAULT NULL COMMENT '职位',
  `remark` VARCHAR(100) NULL DEFAULT NULL COMMENT '备注',
  `email` VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
  `ctime` BIGINT(15) NULL DEFAULT NULL COMMENT '创建时间',
  `mtime` BIGINT(15) NULL DEFAULT NULL COMMENT '最后更新时间',
  `rstatus` TINYINT(2) NULL DEFAULT NULL COMMENT '状态 0:正常 1:删除',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8
COMMENT = '系统用户表';


-- -----------------------------------------------------
-- Table `caogen`.`t_sys_user_role_link`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `caogen`.`t_sys_user_role_link` ;

CREATE TABLE IF NOT EXISTS `caogen`.`t_sys_user_role_link` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户id',
  `role_id` BIGINT(20) NOT NULL COMMENT '角色id',
  `remark` VARCHAR(100) NULL DEFAULT NULL COMMENT '备注',
  `ctime` BIGINT(15) NULL DEFAULT NULL COMMENT '创建时间',
  `mtime` BIGINT(15) NULL DEFAULT NULL COMMENT '最后更新时间',
  `rstatus` TINYINT(2) NULL DEFAULT NULL COMMENT '状态 0:正常 1:删除',
  PRIMARY KEY (`id`),
  INDEX `fk_link_user_role_user_id_idx` (`user_id` ASC),
  INDEX `fk_link_user_role_role_id_idx` (`role_id` ASC),
  CONSTRAINT `fk_link_user_role_role_id`
    FOREIGN KEY (`role_id`)
    REFERENCES `caogen`.`t_sys_role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_link_user_role_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `caogen`.`t_sys_user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8
COMMENT = '用户、角色关系表';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
