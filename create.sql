create sequence hibernate_sequence start 1 increment 1;
create table address (id int8 not null, city varchar(255), country varchar(255), house_number int4, postal_code varchar(255), street varchar(255), primary key (id));
create table customer_order (id int8 not null, external_shipment_id varchar(255), order_status varchar(255), user_name varchar(255), address_id int8, primary key (id));
create table order_to_item (fk_order_id int8 not null, fk_order_item_id int8 not null);
create table order_item (id int8 not null, category varchar(255), name varchar(255), price int8, quantity int8, primary key (id));
alter table if exists order_to_item add constraint UK_o00cek2wrdwulma772n0s0pnc unique (fk_order_item_id);
alter table if exists customer_order add constraint FK83an1a9jt6ho0ibawhd5nrsle foreign key (address_id) references address;
alter table if exists order_to_item add constraint FK3813xv82raowyxxgu5e0w2rva foreign key (fk_order_item_id) references order_item;
alter table if exists order_to_item add constraint FKis5e3mcn4dpdtot5etf96kvgf foreign key (fk_order_id) references customer_order;
