INSERT INTO writingGroups (groupName, headWriter, yearFormed, subject)
    VALUES ('The Scribble Society','Mark Twain',1990,'Drama'),
('Masters of Text','William Shakespeare',2000,'Horror'),
('Always Write', 'J. K. Rowling',2001,'Comedy'),
('Plot Twists', 'Jane Austen',2004,'Drama'),
('Novice Narrators','Leo Tolstoy',2002,'Crimne'),
('From Left to Write','F. Scott Fitzgerald',2002,'Horror'),
('The Pencil Pack','Stephen King',2007,'Crime');
INSERT INTO  publishers (publisherName, publisherAddress, publisherPhone, publisherEmail)
    VALUES  ('Penguin Random House','1160 Trademark Dr # 111, Reno, NV 89521','909-123-1234','PRH@gmail.com'),
('Hachette Livre','50 Victoria Embankment EC4Y 0DZ London UK','808-444-5555','HL@gmail.com'),
('HarperCollins', '5665 Carmichael Road Montgomery, AL 36117','888-965-8737','email@harpercollins.com'),
('Macmillan Publishers', '120 Broadway, New York, NY','888-330-8477','MP@gmail.com'),
('Simon & Schuster','1230 6th Ave, New York, NY 10020','800-223-2336','SS@gmail.com');
INSERT INTO  books (groupName, bookTitle, publisherName, yearPublished,numberPages)
    VALUES  ('The Scribble Society','In Search of Lost Time','Penguin Random House', 1913 , 345),
('The Scribble Society','Ulysses','Penguin Random House', 1900 , 205),
('Masters of Text','Don Quixote','Hachette Livre', 2003 , 803),
('From Left to Write','The Great Gatsby','HarperCollins', 2005 , 736),
('Always Write','One Hundred Years of Solitude','HarperCollins', 1990 , 404),
('Plot Twists','Moby Dick','Macmillan Publishers', 2005 , 777),
('Novice Narrators','One Hundred Years of Solitude','Macmillan Publishers', 1994 , 673),
('Masters of Text','Hamlet','Simon & Schuster', 2003 , 1111),
('The Pencil Pack','The Catcher in the Rye','Simon & Schuster', 2008 , 568);