CREATE TABLE WritingGroups (
    GroupName  varchar(20) NOT NULL,
    HeadWriter varchar(30),
    YearFormed varchar(4),
    Subject    varchar(20),
    CONSTRAINT WritingGroups_pk PRIMARY KEY (GroupName)
 );
CREATE TABLE Publishers (
    PublisherName varchar(100) NOT NULL,
    PublisherAddress varchar (100),
    PublisherPhone   varchar (20),
    PublisherEmail varchar (100),
    CONSTRAINT Publishers_pk PRIMARY KEY (PublisherName)
);
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