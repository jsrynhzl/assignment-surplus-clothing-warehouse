create table if not exists clothing (
  id identity,
  name varchar(50) not null,
  year_of_creation int not null,
  price double not null,
  brand varchar(50) not null,
  created_at timestamp not null
);