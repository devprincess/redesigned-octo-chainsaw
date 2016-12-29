# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table category (
  id                            integer auto_increment not null,
  name                          varchar(255),
  views                         integer,
  constraint pk_category primary key (id)
);

create table customer (
  id                            integer auto_increment not null,
  name                          varchar(255),
  mobile                        varchar(255),
  email                         varchar(255),
  pwd                           varchar(255),
  gender                        varchar(255),
  birthdate                     datetime(6),
  idpaymethod                   integer,
  constraint pk_customer primary key (id)
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
  constraint pk_product primary key (id)
);

create table shippingmethod (
  id                            integer auto_increment not null,
  name                          varchar(255),
  constraint pk_shippingmethod primary key (id)
);

create table stock (
  id                            integer auto_increment not null,
  idproduct                     integer,
  quantity                      integer,
  constraint pk_stock primary key (id)
);


# --- !Downs

drop table if exists category;

drop table if exists customer;

drop table if exists paymethod;

drop table if exists product;

drop table if exists shippingmethod;

drop table if exists stock;

