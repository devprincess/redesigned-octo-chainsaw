# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table category (
  id                            integer auto_increment not null,
  name                          varchar(255),
  views                         integer,
  constraint pk_category primary key (id)
);

create table o_order (
  id                            integer auto_increment not null,
  idcustomer                    integer,
  idorderstatus                 integer,
  date                          datetime(6),
  idshippingmethod              integer,
  constraint pk_o_order primary key (id)
);

create table o_orderdetail (
  id                            integer auto_increment not null,
  order_id                      integer not null,
  idorder                       integer,
  idproduct                     integer,
  quantity                      integer,
  constraint pk_o_orderdetail primary key (id)
);

create table paymethod (
  id                            integer auto_increment not null,
  name                          varchar(255),
  constraint pk_paymethod primary key (id)
);

create table product (
  id                            integer auto_increment not null,
  name                          varchar(255),
  description                   varchar(800),
  price                         double,
  idcategory                    integer,
  views                         integer,
  url                           varchar(255),
  constraint pk_product primary key (id)
);

create table role (
  id                            integer auto_increment not null,
  name                          varchar(255),
  constraint pk_role primary key (id)
);

create table shippingmethod (
  id                            integer auto_increment not null,
  name                          varchar(255),
  constraint pk_shippingmethod primary key (id)
);

create table shoppingcart (
  id                            integer auto_increment not null,
  idcustomer                    integer,
  constraint pk_shoppingcart primary key (id)
);

create table shoppingcartitem (
  id                            integer auto_increment not null,
  shopping_cart_id              integer not null,
  idproduct                     integer,
  quantity                      integer,
  constraint pk_shoppingcartitem primary key (id)
);

create table stock (
  id                            integer auto_increment not null,
  idproduct                     integer,
  quantity                      integer,
  constraint pk_stock primary key (id)
);

create table user (
  id                            integer auto_increment not null,
  name                          varchar(255),
  mobile                        varchar(255),
  email                         varchar(255),
  pwd                           varchar(255),
  gender                        varchar(255),
  birthdate                     datetime(6),
  address                       varchar(255),
  idpaymethod                   integer,
  idrole                        integer,
  constraint pk_user primary key (id)
);

alter table o_orderdetail add constraint fk_o_orderdetail_order_id foreign key (order_id) references o_order (id) on delete restrict on update restrict;
create index ix_o_orderdetail_order_id on o_orderdetail (order_id);

alter table shoppingcartitem add constraint fk_shoppingcartitem_shopping_cart_id foreign key (shopping_cart_id) references shoppingcart (id) on delete restrict on update restrict;
create index ix_shoppingcartitem_shopping_cart_id on shoppingcartitem (shopping_cart_id);


# --- !Downs

alter table o_orderdetail drop foreign key fk_o_orderdetail_order_id;
drop index ix_o_orderdetail_order_id on o_orderdetail;

alter table shoppingcartitem drop foreign key fk_shoppingcartitem_shopping_cart_id;
drop index ix_shoppingcartitem_shopping_cart_id on shoppingcartitem;

drop table if exists category;

drop table if exists o_order;

drop table if exists o_orderdetail;

drop table if exists paymethod;

drop table if exists product;

drop table if exists role;

drop table if exists shippingmethod;

drop table if exists shoppingcart;

drop table if exists shoppingcartitem;

drop table if exists stock;

drop table if exists user;

