create database SuperMarket;

create table product (
    id serial primary key,
    name varchar(50),
    price double precision,
    status varchar(20)
);

create table discount_card(
    id serial primary key,
    code varchar (5),
    discount integer
);

INSERT INTO product (name, price, status) VALUES ('Bread', 1.23, default);
INSERT INTO product (name, price, status) VALUES ('Res', 3.55, 'promotion');
INSERT INTO product (name, price, status) VALUES ('Eggs', 3.7, default);
INSERT INTO product (name, price, status) VALUES ('Cheese', 17, 'promotion');
INSERT INTO product (name, price, status) VALUES ('Chicken fillet', 11.5, default);
INSERT INTO product (name, price, status) VALUES ('Beef ham', 20, 'promotion');
INSERT INTO product (name, price, status) VALUES ('Apples', 4, default);
INSERT INTO product (name, price, status) VALUES ('Bananas', 5, default);
INSERT INTO product (name, price, status) VALUES ('Oranges', 5, 'promotion');
INSERT INTO product (name, price, status) VALUES ('Tomatoes', 7.7, default);
INSERT INTO product (name, price, status) VALUES ('Potato', 2, default);
INSERT INTO product (name, price, status) VALUES ('Onion', 1.9, 'promotion');
INSERT INTO product (name, price, status) VALUES ('Water', 1.6, 'promotion');
INSERT INTO product (name, price, status) VALUES ('Wine', 20, default);
INSERT INTO product (name, price, status) VALUES ('Cigarettes', 4.5, default);

INSERT INTO discount_card (code, discount) VALUES ('8888', '10');
INSERT INTO discount_card (code, discount) VALUES ('1111', '13');
INSERT INTO discount_card (code, discount) VALUES ('4123', '5');
INSERT INTO discount_card (code, discount) VALUES ('9999', '20');
INSERT INTO discount_card (code, discount) VALUES ('9876', '8');
INSERT INTO discount_card (code, discount) VALUES ('1001', '10');