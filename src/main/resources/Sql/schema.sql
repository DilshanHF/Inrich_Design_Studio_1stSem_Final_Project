
create table Employee(
                         e_id varchar(35) primary key ,
                         e_name varchar(155) not null,
                         e_address text not null,
                         e_tel varchar(15) not null,
                        e_nic varchar(25)not null
);

create table customer(
                         customer_id varchar(35) primary key,
                         customer_name varchar(50)not null ,
                         address text not null,
                         c_tel varchar(14) not null,
                         c_email varchar(11) not null
);

create table attandance(


);
desc employee_attandance;


create table user(
                     email varchar(155)primary key not null ,
                    first_name varchar(155)not null ,
                    last_name varchar(155)not null ,
                    password varchar(155)not null
);
create table orders(

                       order_id varchar(35) primary key ,
                       order_date date not null ,
                       description text ,
                       customer_id varchar(35) ,
                       handover_date date,
                       constraint foreign key (customer_id) references customer (customer_id) on DELETE cascade on UPDATE cascade

);
create table item(
                     item_code varchar (35) primary key ,
                     item_name varchar(155) not null ,
                     Wood_type varchar(20) not null ,

                     unit_price  double(40,2)

);
create table order_detail(
                             item_code varchar (35) ,
                             order_id varchar(35) ,
                             qty int not null ,
                             amount double(40,2) not null,
                             constraint foreign key (item_code ) references item (item_code) on delete cascade on UPDATE cascade ,
                             constraint foreign key (order_id) references orders (order_id) on delete cascade on UPDATE cascade
);
drop table user;
create table delivery(
    deli_id varchar(55)primary key not null ,
    c_name varchar(155)not null ,
    order_id varchar(155)not null ,
    address varchar(155),
    constraint foreign key (order_id)references orders(order_id) on delete cascade on update cascade
);

create table income(
    income_id varchar(50)primary key ,
    orde_id varchar(50)not null ,
    income_type varchar(155)not null,
    amount double(40,2)not null,
    date date,
    constraint foreign key (orde_id)references orders(order_id)on delete cascade on update cascade


);
create table expense(
    expense_id varchar(100) primary key,
    date date not null ,
    descripton varchar(155)not null ,
    type varchar(55),
    amount double(55,2)not null
);

create table inventory(
    inventory_id varchar(55)primary key ,
    item_code varchar(155)not null ,
    date date,
    duration varchar(55),
    constraint foreign key (item_code)references item(item_code)on delete cascade on update cascade
);
create table inventory_details(
    order_id varchar(55)not null ,
    item_code varchar(155)not null ,
    Qty int not null ,
    handOverDate date not null

);

desc user;
select * from user;
select * from customer;
select * from user;
drop table customer;

ALTER TABLE customer
    MODIFY COLUMN c_email VARCHAR(155);

Drop TABLE income;
show tables ;

create table income(
                       income_id varchar(50)primary key ,
                       orde_id varchar(50)not null ,
                       income_type varchar(155)not null,
                       description varchar(155)not null ,
                       amount double(40,2)not null,
                       date date,
                       constraint foreign key (orde_id)references orders(order_id)on delete cascade on update cascade


);
desc income;
drop table expense;
desc expense;
drop table item;
drop table order_detail;
drop table inventory;
drop table inventory_details;
show tables;
desc item;

alter table orders drop column description;

desc orders;
drop table item;

alter table income drop column description;

