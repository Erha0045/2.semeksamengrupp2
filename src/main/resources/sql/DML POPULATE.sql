
-- TESTDATA 
SET FOREIGN_KEY_CHECKS=0;
TRUNCATE TABLE task;
ALTER TABLE task AUTO_INCREMENT = 1;

-- TASK
INSERT INTO alfasolutionsdb.task (projectid, taskno, name, startdate, finishdate, duration, isSubTask, tasktimeconsumption, noOfPersons, workinghoursday) 
VALUES ('4', '9.0', 'BAD', '2020-01-12', '2020-01-30', '19', 'no', '0', '0', '0');
INSERT INTO alfasolutionsdb.task (projectid, taskno, name, startdate, finishdate, duration, isSubTask, tasktimeconsumption, noOfPersons, workinghoursday) 
VALUES ('4', '9.1', 'Brugser', '2020-01-12', '2020-01-14', '3', 'yes', '8', '2', '7.5');
INSERT INTO alfasolutionsdb.task (projectid, taskno, name, startdate, finishdate, duration, isSubTask, tasktimeconsumption, noOfPersons, workinghoursday) 
VALUES ('4', '9.2', 'Toilet', '2020-01-13', '2020-01-16', '4', 'yes', '30', '1', '10');
INSERT INTO alfasolutionsdb.task (projectid, taskno, name, startdate, finishdate, duration, isSubTask, tasktimeconsumption, noOfPersons, workinghoursday) 
VALUES ('4', '9.05', 'Håndvask', '2020-01-18', '2020-01-20', '3', 'yes', '33', '1', '11');
INSERT INTO alfasolutionsdb.task (projectid, taskno, name, startdate, finishdate, duration, isSubTask, tasktimeconsumption, noOfPersons, workinghoursday) 
VALUES ('4', '9.17', 'Badekar', '2020-01-21', '2020-01-30', '10', 'yes', '100', '2', '5');
INSERT INTO alfasolutionsdb.task (projectid, taskno, name, startdate, finishdate, duration, isSubTask, tasktimeconsumption, noOfPersons, workinghoursday) 
VALUES ('1', '8.00', 'KØKKEN', '2020-01-01', '2020-01-08', '8', 'no', '0', '0', '0');
INSERT INTO alfasolutionsdb.task (projectid, taskno, name, startdate, finishdate, duration, isSubTask, tasktimeconsumption, noOfPersons, workinghoursday)
VALUES ('1', '8.25', 'Køkken1', '2020-01-02', '2020-01-08', '7', 'yes', '1', '4', '2');
INSERT INTO alfasolutionsdb.task (projectid, taskno, name, startdate, finishdate, duration, isSubTask, tasktimeconsumption, noOfPersons, workinghoursday) 
VALUES ('1', '8.30', 'Køkken2', '2020-01-03', '2020-01-08', '6', 'yes', '2', '5', '3');
INSERT INTO alfasolutionsdb.task (projectid, taskno, name, startdate, finishdate, duration, isSubTask, tasktimeconsumption, noOfPersons, workinghoursday) 
VALUES ('1', '8.30', 'Køkken3', '2020-01-04', '2020-01-08', '5', 'yes', '3', '6', '4');
INSERT INTO alfasolutionsdb.task (projectid, taskno, name, startdate, finishdate, duration, isSubTask, tasktimeconsumption, noOfPersons, workinghoursday) 
VALUES ('1', '7.00', 'HAVE', '2020-02-01', '2020-02-08', '8', 'no', '0', '0', '0');
INSERT INTO alfasolutionsdb.task (projectid, taskno, name, startdate, finishdate, duration, isSubTask, tasktimeconsumption, noOfPersons, workinghoursday) 
VALUES ('1', '7.10', 'Have ting1', '2020-02-06', '2020-02-08', '3', 'yes', '5', '2', '5');
INSERT INTO alfasolutionsdb.task (projectid, taskno, name, startdate, finishdate, duration, isSubTask, tasktimeconsumption, noOfPersons, workinghoursday) 
VALUES ('1', '7.41', 'Have ting2', '2020-02-05', '2020-02-08', '4', 'yes', '4', '2', '5');
INSERT INTO alfasolutionsdb.task (projectid, taskno, name, startdate, finishdate, duration, isSubTask, tasktimeconsumption, noOfPersons, workinghoursday) 
VALUES ('1', '7.20', 'Have ting3', '2020-02-04', '2020-02-05', '5', 'yes', '2', '2', '5');
INSERT INTO alfasolutionsdb.task (projectid, taskno, name, startdate, finishdate, duration, isSubTask, tasktimeconsumption, noOfPersons, workinghoursday) 
VALUES ('1', '7.80', 'Have ting4', '2020-02-03', '2020-02-04', '2', 'yes', '1', '2', '5');
INSERT INTO alfasolutionsdb.task (projectid, taskno, name, startdate, finishdate, duration, isSubTask, tasktimeconsumption, noOfPersons, workinghoursday) 
VALUES ('1', '7.70', 'Have ting5', '2020-02-02', '2020-02-08', '7', 'yes', '2', '2', '5');

TRUNCATE TABLE project;
ALTER TABLE project AUTO_INCREMENT = 1;
-- PROJECTS
INSERT INTO alfasolutionsdb.project(projectname, ownername, startdate, deadlinedate) 
VALUES ('HUS HAVE', 'a', '2020-01-01', '2020-02-08');
INSERT INTO alfasolutionsdb.project(projectname, ownername, startdate, deadlinedate) 
VALUES ('BIL', 'a', '2020-01-01', '2020-01-01');
INSERT INTO alfasolutionsdb.project(projectname, ownername, startdate, deadlinedate) 
VALUES ('NOGET', 'a', '2018-01-01', '2022-01-01');
INSERT INTO alfasolutionsdb.project(projectname, ownername, startdate, deadlinedate) 
VALUES ('BAD', 'a', '2020-01-01', '2020-02-02'); --  projectId=4

TRUNCATE TABLE userinfo;
ALTER TABLE userinfo AUTO_INCREMENT = 1;
-- userinfo
Insert into alfasolutionsdb.userinfo (username, password, usertype) VALUE('a','a','user');
Insert into alfasolutionsdb.userinfo (username, password, usertype) VALUE('luff','1234','user');

SET FOREIGN_KEY_CHECKS=1;



