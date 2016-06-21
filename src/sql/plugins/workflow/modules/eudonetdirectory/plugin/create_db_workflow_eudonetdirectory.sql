--
-- Table structure for table task_create_eudonetdirectory_cf
--
DROP TABLE IF EXISTS task_create_eudonetdirectory_cf;
CREATE TABLE task_create_eudonetdirectory_cf(
  id_task INT DEFAULT NULL,
  id_directory INT DEFAULT NULL,
  session_key_family varchar(255) default NULL,
  subscriber_login varchar(255) default NULL,
  subscriber_password varchar(255) default NULL,
  user_login varchar(255) default NULL,
  user_password varchar(255) default NULL,
  base_name varchar(255) default NULL,
  wsdl_url varchar(255) default NULL,
  PRIMARY KEY (id_task)
);

--
-- Table structure for table task_create_eudonet_data_cf
--
DROP TABLE IF EXISTS task_create_eudonet_data_cf;
CREATE TABLE task_create_eudonet_data_cf(
  id_attribut INT DEFAULT NULL,
  id_task INT DEFAULT NULL,
  order_entry INT DEFAULT NULL,
  eudonet_key varchar(255) default NULL,
  
  PRIMARY KEY (id_attribut),
  CONSTRAINT `fk_eudonet_directory_id_task` FOREIGN KEY(`id_task`) references task_create_eudonetdirectory_cf(`id_task`)

);


  