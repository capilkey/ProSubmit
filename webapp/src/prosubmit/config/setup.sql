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
	`company_name` VARCHAR(100) NOT NULL,
	`industry` SMALLINT(5) UNSIGNED NOT NULL REFERENCES industry(id),
	`company_url` VARCHAR(200) NULL,
	`email` VARCHAR(40) NOT NULL UNIQUE,
	`firstname` VARCHAR(25) NOT NULL,
	`lastname` VARCHAR(25) NOT NULL,
	`jobtitle` VARCHAR(50) NOT NULL,
	`telephone` VARCHAR(20) NOT NULL,
	`extension` CHAR(10) NULL,
	`companyaddress` VARCHAR(200) NOT NULL,
	`password` VARCHAR(100) NOT NULL,
	`createdate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`authtoken` VARCHAR(200) NOT NULL UNIQUE,
	`expires` TIMESTAMP NULL
);

DROP TABLE IF EXISTS partner;
CREATE TABLE partner(
	`partner_id` INT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
  `company_name` VARCHAR(100) NOT NULL,
  `industry` SMALLINT(5) UNSIGNED NOT NULL REFERENCES industry(id),
  `company_url` VARCHAR(200) NULL,
  `email` VARCHAR(40) NOT NULL UNIQUE,
  `firstname` VARCHAR(25) NOT NULL,
  `lastname` VARCHAR(25) NOT NULL,
  `jobtitle` VARCHAR(50) NOT NULL,
  `telephone` VARCHAR(20) NOT NULL,
  `extension` CHAR(10) NULL,
  `companyaddress` VARCHAR(200) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `createdate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
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
	`student_id` INT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
	`student_bio` TEXT NULL,
	`student_firstname` VARCHAR(25) NOT NULL,
	`student_lastname` VARCHAR(25) NOT NULL,
	`student_email` VARCHAR(50) NOT NULL,
	`student_username` VARCHAR(25) NOT NULL,
	`group_id` INT UNSIGNED NULL REFERENCES `group` (group_id)
);

DROP TABLE IF EXISTS `group`;
CREATE TABLE `group` (
	`group_id` INT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
	`group_name` VARCHAR(50) NOT NULL,
	`group_number` SMALLINT UNSIGNED NOT NULL,
	`group_desc` TEXT NOT NULL,
	`semester_code` VARCHAR(20) NOT NULL REFERENCES semester (semester_code),
	`course_id` VARCHAR(10) NOT NULL REFERENCES course (course_id),
	`student_id` INT UNSIGNED NOT NULL REFERENCES student(student_id)
);

DROP TABLE IF EXISTS project_status;
CREATE TABLE project_status(
	`projstatus_id` SMALLINT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
	`projstatus_name` VARCHAR(15) NOT NULL,
	`projstatus_desc` TEXT NOT NULL
);
INSERT INTO `project_status` VALUES
(1,"Awaiting Approval","The project proposal has been submitted, but not looked at by a professor yet."),
(2,"Pending Approval","The project proposal has been reviewed by a professor."),
(3,"Approved","The project proposal has been accepted as a valid project."),
(4,"Rejected","The project proposal has been rejected as a valid project."),
(5,"Matched","The project proposal has been matched to a student group."),
(6,"Suspended","The design or development on the project has been suspended."),
(7,"Developing","The development of the project is ongoing."),
(8,"Completed","All phases of the project have been completed.");

DROP TABLE IF EXISTS project_category;
CREATE TABLE project_category(
	`projcategory_id` SMALLINT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
	`projcategory_name` VARCHAR(25) NOT NULL,
	`projcategory_desc` TEXT NULL
);
INSERT INTO `project_category` (`projcategory_id`,`projcategory_name`) VALUES
(1,"Mobile Application"),
(2,"Web Service"),
(3,"Web Application"),
(4,"General Application");

DROP TABLE IF EXISTS project;
CREATE TABLE project(
	`project_id` INT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
	`partner_id` INT UNSIGNED NOT NULL REFERENCES partner (partner_id),
	`project_title` VARCHAR(100) NOT NULL,
	`project_desc` TEXT NOT NULL,
	`project_createdate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`project_editdate` TIMESTAMP NULL,
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

DROP TABLE IF EXISTS industry;
CREATE TABLE industry(
  `id` SMALLINT(5) UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
  `industry` VARCHAR(50) NOT NULL
);
 INSERT INTO industry (industry) VALUES
("Accounting"),
("Airlines/Aviation"),
("Alternative Dispute Resolution"),
("Alternative Medicine"),
("Animation"),
("Apparel &amp; Fashion"),
("Architecture &amp; Planning"),
("Arts and Crafts"),
("Automotive"),
("Aviation &amp; Aerospace"),
("Banking"),
("Biotechnology"),
("Broadcast Media"),
("Building Materials"),
("Business Supplies and Equipment"),
("Capital Markets"),
("Chemicals"),
("Civic &amp; Social Organization"),
("Civil Engineering"),
("Commercial Real Estate"),
("Computer &amp; Network Security"),
("Computer Games"),
("Computer Hardware"),
("Computer Networking"),
("Computer Software"),
("Construction"),
("Consumer Electronics"),
("Consumer Goods"),
("Consumer Services"),
("Cosmetics"),
("Dairy"),
("Defense &amp; Space"),
("Design"),
("Education Management"),
("E-Learning"),
("Electrical/Electronic Manufacturing"),
("Entertainment"),
("Environmental Services"),
("Events Services"),
("Executive Office"),
("Facilities Services"),
("Farming"),
("Financial Services"),
("Fine Art"),
("Fishery"),
("Food &amp; Beverages"),
("Food Productio"),
("Fund-Raising"),
("Furniture"),
("Gambling &amp; Casinos"),
("Glass, Ceramics &amp; Concrete"),
("Government Administration"),
("Government Relations"),
("Graphic Design"),
("Health, Wellness and Fitness"),
("Higher Education"),
("Hospital &amp; Health Care"),
("Hospitality"),
("Human Resources"),
("Import and Export"),
("Individual &amp; Family Services"),
("Industrial Automation"),
("Information Services"),
("Information Technology and Services"),
("Insurance"),
("International Affairs"),
("International Trade and Development"),
("Internet"),
("Investment Banking"),
("Ivestment Management"),
("Judiciary"),
("Law Enforcement"),
("Law Practice"),
("Legal Services"),
("Legislative Office"),
("Leisure, Travel &amp; Tourism"),
("Libraries"),
("Logistics and Supply Chain"),
("Luxury Goods &amp; Jewelry"),
("Machinery"),
("Management Consulting"),
("Maritime"),
("Marketing and Advertising"),
("Market Research"),
("Mechanical or Industrial Engineering"),
("Media Production"),
("Medical Devices"),
("Medical Practice"),
("Mental Health Care"),
("Military"),
("Mining &amp; Metals"),
("Motion Pictures and Film"),
("Museums and Institutions"),
("Music"),
("Nanotechnology"),
("Newspapers"),
("Nonprofit Organization Management"),
("Oil &amp; Energy"),
("Online Media"),
("Outsourcing/Offshoring"),
("Package/Freight Delivery"),
("Packaging and Containers"),
("Paper &amp; Forest Products"),
("Performing Arts"),
("Pharmaceuticals"),
("Philanthropy"),
("Photography"),
("Plastics"),
("Political Organization"),
("Primary/Secondary Education"),
("Printing"),
("Professional Training &amp; Coaching"),
("Program Development"),
("Public Policy"),
("Public Relations and Communications"),
("Public Safety"),
("Publishing"),
("Railroad Manufacture"),
("Ranching"),
("Real Estate"),
("Recreational Facilities and Services"),
("Religious Institutions"),
("Renewables &amp; Environment"),
("Research"),
("Restaurants"),
("Retail"),
("Security and Investigations"),
("Semiconductors"),
("Shipbuilding"),
("Sporting Goods"),
("Sports"),
("Staffing and Recruiting"),
("Supermarkets"),
("Telecommunications"),
("Textiles"),
("Think Tanks"),
("Tobacco"),
("Translation and Localization"),
("Transportation/Trucking/Railroad"),
("Utilities"),
("Venture Capital &amp; Private Equity"),
("Veterinary"),
("Warehousing"),
("Wholesale"),
("Wine and Spirits"),
("Wireless"),
("Writing and Editing");
SET FOREIGN_KEY_CHECKS = 1;
COMMIT;
