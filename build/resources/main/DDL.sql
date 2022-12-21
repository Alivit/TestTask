create database SuperMarket;

create table product (
    id serial primary key,
    name varchar(50),
    price double precision,
    status varchar(20)
);

create table discount_card(
    id serial primary key,
    code integer,
    discount integer
);

INSERT INTO product (name, price, status) VALUES ('���� ��������', 1.23, default);
INSERT INTO product (name, price, status) VALUES ('����� ���', 3.55, '�����');
INSERT INTO product (name, price, status) VALUES ('������� ����', 3.7, default);
INSERT INTO product (name, price, status) VALUES ('������� ���', 17, '�����');
INSERT INTO product (name, price, status) VALUES ('������� ����', 11.5, default);
INSERT INTO product (name, price, status) VALUES ('������� ������', 20, '�����');
INSERT INTO product (name, price, status) VALUES ('������', 4, default);
INSERT INTO product (name, price, status) VALUES ('������', 5, default);
INSERT INTO product (name, price, status) VALUES ('���������', 5, '�����');
INSERT INTO product (name, price, status) VALUES ('��������', 7.7, default);
INSERT INTO product (name, price, status) VALUES ('���������', 2, default);
INSERT INTO product (name, price, status) VALUES ('���', 1.9, '�����');
INSERT INTO product (name, price, status) VALUES ('����', 1.6, '�����');
INSERT INTO product (name, price, status) VALUES ('����', 20, default);
INSERT INTO product (name, price, status) VALUES ('��������', 4.5, default);

INSERT INTO discount_card (code, discount) VALUES ('8888', '10');
INSERT INTO discount_card (code, discount) VALUES ('1111', '13');
INSERT INTO discount_card (code, discount) VALUES ('4123', '5');
INSERT INTO discount_card (code, discount) VALUES ('9999', '20');
INSERT INTO discount_card (code, discount) VALUES ('9876', '8');
INSERT INTO discount_card (code, discount) VALUES ('1001', '10');