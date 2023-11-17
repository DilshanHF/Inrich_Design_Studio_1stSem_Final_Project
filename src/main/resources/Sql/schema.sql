
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

create table employee_attandance(
                                    att_id varchar(25)primary key ,
                                    e_id varchar(35),
                                    date date not null ,
                                    in_time time not null ,
                                    out_time time not null ,
                                    work_hours double(20,2),
                                    ot_hours double(20,2),
                                    constraint foreign key (e_id) references employee (e_id) on DELETE cascade on UPDATE cascade
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
                     item_type varchar(20) not null ,
                     category varchar(55)not null ,
                     qty_on_hand int not null,
                     unit_price  double(40,2)
);
create table order_detail(
                             item_id varchar (35) ,
                             order_id varchar(35) ,
                             qty int not null ,
                             amount double(40,2) not null,
                             constraint foreign key (item_id) references item (item_code) on delete cascade on UPDATE cascade ,
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
    date date,
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
    item_code varchar(55)not null ,
    inven_id varchar(55)not null,
    constraint foreign key (inven_id)references inventory(inventory_id)on delete cascade on update cascade ,
    constraint foreign key (item_code)references item(item_code)on delete cascade on update cascade
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