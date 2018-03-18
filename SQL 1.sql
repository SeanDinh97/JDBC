--Table structure of table WritingGroups
CREATE TABLE WritingGroups (
    GroupName  varchar(100) NOT NULL,
    HeadWriter varchar(30),
    YearFormed varchar(4),
    Subject    varchar(20),
    CONSTRAINT WritingGroups_pk PRIMARY KEY (GroupName)
 );

--Table structure of table Publishers
CREATE TABLE Publishers (
    PublisherName varchar(100) NOT NULL,
    PublisherAddress varchar (100),
    PublisherPhone   varchar (20),
    PublisherEmail varchar (100),
    CONSTRAINT Publishers_pk PRIMARY KEY (PublisherName)
);
--Table structure of table Books
CREATE TABLE Books (
    GroupName varchar(100) NOT NULL,
    BookTitle varchar(100)NOT NULL,
    PublisherName varchar (100) NOT NULL,
    YearPublished varchar (4),
    NumberPages   int NOT NULL,
    CONSTRAINT Books_pk PRIMARY KEY (GroupName, BookTitle),
    CONSTRAINT Books_fk FOREIGN KEY (GroupName) REFERENCES WritingGroups (GroupName),
    CONSTRAINT Books_fk2 FOREIGN KEY (PublisherName) REFERENCES Publishers (PublisherName)
);

--Data for the table WritingGroups

INSERT INTO WritingGroups(GroupName, HeadWriter, YearFormed, Subject) VALUES 
('Los Angeles Writing Group', 'John Smith', '2009', 'Fiction');
INSERT INTO WritingGroups(GroupName, HeadWriter, YearFormed, Subject) VALUES 
('Santa Monica Writing Group', 'Michael Williams', '2012', 'Nonfiction');
INSERT INTO WritingGroups(GroupName, HeadWriter, YearFormed, Subject) VALUES 
('Garden Grove Writing Group', 'David Robinson', '2011', 'Romance');
INSERT INTO WritingGroups(GroupName, HeadWriter, YearFormed, Subject) VALUES 
('Long Beach Writing Group', 'Mary Jones', '2014', 'Horror');
INSERT INTO WritingGroups(GroupName, HeadWriter, YearFormed, Subject) VALUES 
('Santa Ana Writing Group', 'Sophia Taylor', '2006', 'Mystery');
INSERT INTO WritingGroups(GroupName, HeadWriter, YearFormed, Subject) VALUES 
('Fullerton Writing Group', 'Isabella Hernandez', '2004', 'Drama');

--Data for the table Publishers
INSERT INTO Publishers (PublisherName, PublisherAddress, PublisherPhone, PublisherEmail) VALUES
('Lion Publishing', '123 6th St. Melbourne, FL 32904','239-200-6398','contact@LionPublishing.com');
INSERT INTO Publishers (PublisherName, PublisherAddress, PublisherPhone, PublisherEmail) VALUES
('Koala Pages','71 Pilgrim Avenue Chevy Chase, MD 20815','240-200-3745','contact@KoalaPages.org');
INSERT INTO Publishers (PublisherName, PublisherAddress, PublisherPhone, PublisherEmail) VALUES
('Bear Corporation','70 Bowman St. South Windsor, CT 06074','203-200-7944','contact@BearCorp.com');
INSERT INTO Publishers (PublisherName, PublisherAddress, PublisherPhone, PublisherEmail) VALUES
('Turtle Company','4 Goldfield Rd. Honolulu, HI 96815','808-200-1015','contact@TurtleCo.net');
INSERT INTO Publishers (PublisherName, PublisherAddress, PublisherPhone, PublisherEmail) VALUES
('Owl Press','44 Shirley Ave. West Chicago, IL 60185','217-200-8931','contact@OwlPress.org');
INSERT INTO Publishers (PublisherName, PublisherAddress, PublisherPhone, PublisherEmail) VALUES
('Bumblebee Books','514 S. Magnolia St. Orlando, FL 32806','239-200-5251','contact@BBBooks.com');

--Data for the table Books
INSERT INTO Books (GroupName, BookTitle, PublisherName, YearPublished, NumberPages) VALUES
('Los Angeles Writing Group','Throne Games','Lion Publishing','2011',523);
INSERT INTO Books (GroupName, BookTitle, PublisherName, YearPublished, NumberPages) VALUES
('Santa Monica Writing Group','History of Airplanes','Bear Corporation','2017',356);
INSERT INTO Books (GroupName, BookTitle, PublisherName, YearPublished, NumberPages) VALUES
('Los Angeles Writing Group','Dr. Robot','Lion Publishing','2013',201);
INSERT INTO Books (GroupName, BookTitle, PublisherName, YearPublished, NumberPages) VALUES
('Garden Grove Writing Group','Love','Turtle Company','2015',236);
INSERT INTO Books (GroupName, BookTitle, PublisherName, YearPublished, NumberPages) VALUES
('Long Beach Writing Group','Monster 2','Koala Pages','2018',278);
INSERT INTO Books (GroupName, BookTitle, PublisherName, YearPublished, NumberPages) VALUES
('Santa Ana Writing Group','Cabin 4','Owl Press','2007',270);
INSERT INTO Books (GroupName, BookTitle, PublisherName, YearPublished, NumberPages) VALUES
('Los Angeles Writing Group','East World','Koala Pages','2011',369);
INSERT INTO Books (GroupName, BookTitle, PublisherName, YearPublished, NumberPages) VALUES
('Fullerton Writing Group','The Room','Owl Press','2005',197);
INSERT INTO Books (GroupName, BookTitle, PublisherName, YearPublished, NumberPages) VALUES
('Santa Ana Writing Group','Night','Bumblebee Books','2009',237);
INSERT INTO Books (GroupName, BookTitle, PublisherName, YearPublished, NumberPages) VALUES
('Long Beach Writing Group','Monster','Koala Pages','2014',298);
