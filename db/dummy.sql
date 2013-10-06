USE pro_submit;
SET FOREIGN_KEY_CHECKS = 0;
DELETE FROM `partner`;
INSERT INTO `partner` VALUES 
(1,"Bell Canada","raburrell@myseneca.ca","Ramone","Burrell","Director Of IT","1-416-111-1111","1234 Bedrock Ave. M1F-2J5 Toronto Candada",SHA1("prosubmit"),current_timestamp),
(2,"Rogers Canada","capilkey@myseneca.ca","Chad","Pilkey","Directory Of IT","1-416-222-2222","1234 Bedrock Ave. M1F-2J5 Toronto Candada",SHA1("prosubmit"),current_timestamp),
("","Facebook","cxie8@myseneca.ca","Chaobo","Xie","Directory Of IT","1-416-222-2222","1234 Bedrock Ave. M1F-2J5 Toronto Candada",SHA1("prosubmit"),current_timestamp),
("","Twitter","xlian3@myseneca.ca","Xing","Liang","Directory Of IT","1-416-222-2222","1234 Bedrock Ave. M1F-2J5 Toronto Candada",SHA1("prosubmit"),current_timestamp);

DELETE FROM `semester`;
INSERT INTO `semester` VALUES
("20133","Fall 2013"),
("20141","Winter 2014"),
("20142","Summer 2014"),
("20143","Fall   2014"),
("20144","Winter 2015");

DELETE FROM `course`;
INSERT INTO `course` VALUES
("PRJ566","Project Planning and Management"),
("PRJ966","Extreme Project Planning and Management");

DELETE FROM `student`;
INSERT INTO `student` VALUES 
(1,"Ramone","Burrell","raburrell@myseneca.ca","raburrell",1),
(2,"Chad","Pilkey","capilkey@myseneca.ca","capilkey",1),
(3,"Chaobo","Xie","cxie8@myseneca.ca","cxie8",1),
(4,"Xian","Lian","raburrell@myseneca.ca","xlian3",1),

(5,"Antolio","Vivaldi","raburrell@myseneca.ca","alvivaldi",2),
(6,"Johannes Sebastian","Bach","capilkey@myseneca.ca","jsbach",2),
(7,"Ludwig Van","Beethoven","cxie8@myseneca.ca","lvbeethoven",3);


DELETE FROM `group`;
INSERT INTO `group` VALUES
(1,"BrainStorm Factory",1,"WE DO STUFF","20133","PRJ566",1),
(2,"The Prolifics",2,"WE MADE A LOT OF GOOD MUSIC","20133","PRJ566",5);



DELETE FROM `project`;
INSERT INTO `project` VALUES
("",1,"Service Pro","Android application for managing services",current_timestamp,NULL,NULL,1,1),
("",2,"Rogers ServPro","IOS application for managing services",current_timestamp,NULL,NULL,1,1);



DELETE FROM `professor`;
INSERT INTO `professor` VALUES
(1,"raburrell","Ramone","Burrell"),
(2,"capilkey","Chad","Pilkey"),
(3,"cxie8","Chaobo","Xie"),
(4,"xlian3","Xian","Liang");

DELETE FROM `system_admin`;
INSERT INTO `system_admin` VALUES
("raburrell"),
("capilkey"),
("cxie8"),
("xlian3");


SET FOREIGN_KEY_CHECKS = 1;
