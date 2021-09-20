--DDL For Creating the WritingGroups Table. This represents a table of WRitiners working together on  a book. Each groupName is Unique.**
CREATE TABLE writingGroups (
    groupName VARCHAR(50) NOT NULL,
    headWriter VARCHAR(50) NOT NULL,
    yearFormed INT NOT NULL,
    subject VARCHAR(50) NOT NULL,
    CONSTRAINT      writingGroups        PRIMARY KEY(groupName));
--DDL for creating the publisher table. It represents a publisher and all the relating information for theat publisher. Each Publisher Name is Unique
CREATE TABLE publishers (
    publisherName       VARCHAR(50)     NOT NULL,
    publisherAddress    VARCHAR(50)     NOT NULL,
    publisherPhone      VARCHAR(50)     NOT NULL,
    publisherEmail      VARCHAR(50)     NOT NULL,              
    CONSTRAINT      publishers           PRIMARY KEY(publisherName));

--DDL for creating the book table. This represents each book stored in the dataBase with related information. A GroupName and bookTitle are unique. AS Well as BookTitle And Publisher.
CREATE TABLE books (
    groupName           VARCHAR(50)          NOT NULL,
    bookTitle           VARCHAR(50)          NOT NULL,
    publisherName       VARCHAR(50)          NOT NULL,
    yearPublished       INT                  NOT NULL,
    numberPages         INT                  NOT NULL,
    CONSTRAINT      books                       PRIMARY KEY(groupName, bookTitle),

    CONSTRAINT      books_uk_01                 UNIQUE (bookTitle, PublisherName),

    CONSTRAINT      books_writingGroups_01      FOREIGN KEY (groupName)
    REFERENCES      writingGroups (groupName),

    CONSTRAINT      books_publishers_01         FOREIGN KEY (publisherName)
    REFERENCES      publishers (publisherName));