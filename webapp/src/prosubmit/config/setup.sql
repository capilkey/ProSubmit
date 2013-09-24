/**
*	@database pro_submit
*/
START TRANSACTION;
DROP DATABASE pro_submit;
CREATE DATABASE pro_submit;
USE pro_submit;
SET FOREIGN_KEY_CHECKS = 0;
SET AUTOCOMMIT = 0;

DROP TABLE IF EXISTS tempppartner;
CREATE TABLE temppartner(
	`temppartner_id` INT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
	`partner_company` VARCHAR(100) NOT NULL,
	`partner_email` VARCHAR(40) NOT NULL UNIQUE,
	`partner_firstname` VARCHAR(25) NOT NULL,
	`partner_lastname` VARCHAR(25) NOT NULL,
	`partner_jobtitle` VARCHAR(50) NOT NULL,
	`partner_telephone` VARCHAR(13) NOT NULL,
	`partner_companyaddress` VARCHAR(200) NOT NULL,
	`partner_hashpassword` VARCHAR(100) NOT NULL,
	`partner_createdate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`partner_authtoken` VARCHAR(200) NOT NULL
);

DROP TABLE IF EXISTS partner;
CREATE TABLE partner(
	`partner_id` INT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
	`partner_company` VARCHAR(100) NOT NULL,
	`partner_email` VARCHAR(40) NOT NULL UNIQUE,
	`partner_firstname` VARCHAR(25) NOT NULL,
	`partner_lastname` VARCHAR(25) NOT NULL,
	`partner_jobtitle` VARCHAR(50) NOT NULL,
	`partner_telephone` VARCHAR(13) NOT NULL,
	`partner_companyaddress` VARCHAR(200) NOT NULL,
	`partner_hashpassword` VARCHAR(100) NOT NULL,
	`partner_createdate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS semester;
CREATE TABLE semester(
	`semester_code` VARCHAR(20) NOT NULL PRIMARY KEY,
	`semester_name` VARCHAR(75) NOT NULL
);

DROP TABLE IF EXISTS course;
CREATE TABLE course(
	`course_id` VARCHAR(10) NOT NULL PRIMARY KEY,
	`course_name` VARCHAR(100) NOT NULL
);

DROP TABLE IF EXISTS student;
CREATE TABLE student(
	`student_id` VARCHAR(40) NOT NULL PRIMARY KEY,
	`student_firstname` VARCHAR(25) NOT NULL,
	`student_lastname` VARCHAR(25) NOT NULL,
	`student_email` VARCHAR(50) NOT NULL UNIQUE,
	`student_username` VARCHAR(25) NOT NULL,
	`group_id` INT UNSIGNED NOT NULL REFERENCES `group` (group_id)
);

DROP TABLE IF EXISTS `group`;
CREATE TABLE `group` (
	`group_id` INT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
	`group_name` VARCHAR(50) NOT NULL,
	`group_number` SMALLINT UNSIGNED NOT NULL,
	`group_desc` TEXT NOT NULL,
	`semester_code` VARCHAR(20) NOT NULL REFERENCES semester (semester_code),
	`course_id` VARCHAR(10) NOT NULL REFERENCES course (course_id),
	`student_id` VARCHAR(40) NOT NULL REFERENCES student(student_id)
);

DROP TABLE IF EXISTS project_status;
CREATE TABLE project_status(
	`projstatus_id` SMALLINT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
	`projstatus_name` VARCHAR(15) NOT NULL,
	`projstatus_desc` TEXT NOT NULL
);
INSERT INTO `project_status` VALUES
('',"Awaiting Approval","The project proposal has been submitted, but not looked at by a professor yet."),
('',"Pending Approval","The project proposal has been reviewed by a professor."),
('',"Approved","The project proposal has been accepted as a valid project."),
('',"Rejected","The project proposal has been rejected as a valid project."),
('',"Matched","The project proposal has been matched to a student group."),
('',"Suspended","The design or development on the project has been suspended."),
('',"Developing","The development of the project is ongoing."),
('',"Completed","All phases of the project have been completed.");

DROP TABLE IF EXISTS project_category;
CREATE TABLE project_category(
	`projcategory_id` SMALLINT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
	`projcategory_name` VARCHAR(25) NOT NULL,
	`projcategory_desc` TEXT NULL
);
INSERT INTO `project_category` (`projcategory_id`,`projcategory_name`) VALUES
('',"Mobile Application"),
('',"Web Service"),
('',"Web Application"),
('',"General Application");

DROP TABLE IF EXISTS project;
CREATE TABLE project(
	`project_id` INT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
	`partner_id` INT UNSIGNED NOT NULL REFERENCES partner (partner_id),
	`project_title` VARCHAR(100) NOT NULL,
	`project_desc` TEXT NOT NULL,
	`project_createdate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`project_editdate` TIMESTAMP NOT NULL,
	`group_id` INT UNSIGNED NULL REFERENCES `group` (`group_id`),
	`projstatus_id` SMALLINT UNSIGNED NOT NULL REFERENCES project_status (`projstatus_id`),
	`projcategory_id` SMALLINT UNSIGNED NOT NULL REFERENCES project_category (`projcategory_id`)
);

DROP TABLE IF EXISTS professor;
CREATE TABLE professor(
	`professor_id` INT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
	`professor_username` VARCHAR(25) NOT NULL UNIQUE,
	`professor_firstname` VARCHAR(25) NOT NULL,
	`professor_lastname` VARCHAR(25) NOT NULL
);

DROP TABLE IF EXISTS project_comment;
CREATE TABLE project_comment(
	`projcom_id` INT UNSIGNED AUTO_INCREMENT NOT NULL,
	`project_id` INT UNSIGNED NOT NULL REFERENCES project (project_id),
	`projcom_text` TEXT NOT NULL,
	`projcom_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`professor_id` INT UNSIGNED NOT NULL REFERENCES professor (professor_id),
	CONSTRAINT PRIMARY KEY (projcom_id,project_id)
);

DROP TABLE IF EXISTS project_rank;
CREATE TABLE project_rank(
	`group_id` INT UNSIGNED NOT NULL REFERENCES `group` (group_id),
	`project_id` INT UNSIGNED NOT NULL REFERENCES project (project_id),
	`projrank_val` TINYINT(1) UNSIGNED NOT NULL,
	CONSTRAINT PRIMARY KEY (group_id,project_id)
);

DROP TABLE IF EXISTS system_admin;
CREATE TABLE system_admin(
	`user_id` VARCHAR(40) NOT NULL PRIMARY KEY
);

SET FOREIGN_KEY_CHECKS = 1;
COMMIT;
