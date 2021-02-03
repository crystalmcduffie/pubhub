drop table if exists books;

create table books (
  isbn_13 varchar (13) primary key,
  title varchar (100),
  author varchar (80),
  publish_date date,
  price decimal (6,2),
  content bytea
);

insert into books values (
  '1111111111111',          	-- id
  'The Adventures of Steve',    -- title
  'Russell Barron', 			-- author
  current_date,    				-- publishDate
  123.50,   					-- price
  null							-- blob
);

drop table if exists book_tags;
create table book_tags
(
    isbn_13 varchar (13) references books(isbn_13),
    tag_name varchar (100),
    primary key (isbn_13, tag_name)
)

insert into book_tags values (
  '1111111111111',          	-- id
  'Adventure'    -- booktag
);

