INSERT INTO tb_client (co_seq_client, nm_name, nm_nick_name, dd_birthday) VALUES (1, 'Fernandes Silva', 'choyo', '1979-05-02');
INSERT INTO tb_contact (co_seq_contact, nm_cellphone, nm_email, co_seq_client) VALUES (1, '+5511940748828', 'fernandesgs10@gmail.com', 1);
INSERT INTO tb_address (co_seq_address, nm_address, nm_address_number, nm_address_city_code, nm_address_zip_code, co_seq_client) VALUES (1, 'Nicolls Dr.', '7007', 'Bronx', '1248', 1);

INSERT INTO tb_client (co_seq_client, nm_name, nm_nick_name, dd_birthday) VALUES (2, 'Test', 'test', '1984-04-04');
INSERT INTO tb_contact (co_seq_contact, nm_cellphone, nm_email, co_seq_client) VALUES (2, '+5511940313247', 'test10@gmail.com', 2);
INSERT INTO tb_address (co_seq_address, nm_address, nm_address_number, nm_address_city_code, nm_address_zip_code, co_seq_client) VALUES (2, '2 E. Wagon Dr.', '10701', 'Bronx', '01597', 2);