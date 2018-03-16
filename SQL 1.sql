--Table structure of table WritingGroups
CREATE TABLE WritingGroups (
    GroupName  varchar(20) NOT NULL,
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
    GroupName varchar(20) NOT NULL,
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
();

--Data for the table Books
INSERT INTO Books (GroupName, BookTitle, PublisherName, YearPublished, NumberPages) VALUES
();
