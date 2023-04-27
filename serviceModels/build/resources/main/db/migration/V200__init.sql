INSERT into customer(id,name,phone,email) values
(UUID_TO_BIN('fe1f2731-ef07-4203-aa89-e645d0b82026'),'customer 1', '5555-123445','test1@gmail.com');
INSERT into customer(id,name,phone,email) values
(UUID_TO_BIN('7d1be453-a7ed-4253-9727-3458c77c6a6c'),'customer 2', '315274008','test2@gmail.com');
insert into shipping_address(id,number,city,zip_code,country,customer_id) values
(UUID_TO_BIN('71decaa1-ec25-4b51-a00f-6c092de6d168'), '1234', 'Cali','7053','Colombia',
UUID_TO_BIN('fe1f2731-ef07-4203-aa89-e645d0b82026'));
insert into shipping_address(id,number,city,zip_code,country,customer_id) values
(UUID_TO_BIN('176561d3-72be-44db-9dff-4a5b552df36e'), 'Calle 123323', 'Bogota','1080','Colombia',
UUID_TO_BIN('fe1f2731-ef07-4203-aa89-e645d0b82026'));
insert into shipping_address(id,number,city,zip_code,country,customer_id) values
(UUID_TO_BIN('54420a19-37de-42ee-bb27-9567ec32b00a'), '4321', 'Medellin','7053','Colombia',
UUID_TO_BIN('fe1f2731-ef07-4203-aa89-e645d0b82026'));
insert into payment_type(id, name) values
(UUID_TO_BIN('f6b143e3-59a2-4777-a2a2-b73d87a6e047'), 'Cash');
insert into payment_type(id, name) values
(UUID_TO_BIN('22f3ac38-0fe5-486e-8c1a-01d8273ebf96'), 'Other');
insert into payment_type(id, name) values
(UUID_TO_BIN('7610ef67-f546-43eb-a3d7-52a2a419b6c1'), 'Credit card');
insert into payment_type(id, name) values
(UUID_TO_BIN('7158a3e3-953c-4500-bdc7-c23b117a0b38'), 'Check');
insert into payment_type(id, name) values
(UUID_TO_BIN('98df327a-4ec4-4194-a8da-52cfaf35455c'), 'Bank account');
insert into product(id, name, description, price, weight) values
(UUID_TO_BIN('41ee4815-52ea-462b-81bf-415b6a30eadc'), 'Product 1', 'Description Product 1', 5.5, 10.0);
insert into product(id, name, description, price, weight) values
(UUID_TO_BIN('5c01a817-78ae-4ceb-b932-d41d25e98411'), 'Product 2', 'Description Product 2', 30.0, 2.7);
insert into product(id, name, description, price, weight) values
(UUID_TO_BIN('c50e0f7b-714e-433f-8433-644f7c814524'), 'Product 3', 'Description Product 3', 150.5, 0.0);
insert into product(id, name, description, price, weight) values
(UUID_TO_BIN('af57ec78-327b-428f-bba6-a29c1fa329f7'), 'Product 4', 'Description Product 4', 600.4, 25.3);
