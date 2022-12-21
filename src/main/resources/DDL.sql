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

INSERT INTO product (name, price, status) VALUES ('Хлеб нарезной', 1.23, default);
INSERT INTO product (name, price, status) VALUES ('Белый рис', 3.55, 'акция');
INSERT INTO product (name, price, status) VALUES ('Куриные яйца', 3.7, default);
INSERT INTO product (name, price, status) VALUES ('Местный сыр', 17, 'акция');
INSERT INTO product (name, price, status) VALUES ('Куриное филе', 11.5, default);
INSERT INTO product (name, price, status) VALUES ('Говяжий окорок', 20, 'акция');
INSERT INTO product (name, price, status) VALUES ('Яблоки', 4, default);
INSERT INTO product (name, price, status) VALUES ('Бананы', 5, default);
INSERT INTO product (name, price, status) VALUES ('Апельсины', 5, 'акция');
INSERT INTO product (name, price, status) VALUES ('Помидоры', 7.7, default);
INSERT INTO product (name, price, status) VALUES ('Картофель', 2, default);
INSERT INTO product (name, price, status) VALUES ('Лук', 1.9, 'акция');
INSERT INTO product (name, price, status) VALUES ('Вода', 1.6, 'акция');
INSERT INTO product (name, price, status) VALUES ('Вино', 20, default);
INSERT INTO product (name, price, status) VALUES ('Сигареты', 4.5, default);

INSERT INTO discount_card (code, discount) VALUES ('8888', '10');
INSERT INTO discount_card (code, discount) VALUES ('1111', '13');
INSERT INTO discount_card (code, discount) VALUES ('4123', '5');
INSERT INTO discount_card (code, discount) VALUES ('9999', '20');
INSERT INTO discount_card (code, discount) VALUES ('9876', '8');
INSERT INTO discount_card (code, discount) VALUES ('1001', '10');