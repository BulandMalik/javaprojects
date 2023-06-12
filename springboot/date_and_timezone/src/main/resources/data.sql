insert into fl_car (fc_id,fc_reg_number, fc_chasis_color, fc_chasis_number, fc_status) values (1001, 'C123', 'Green', '131dasd', 1);
insert into fl_car (fc_id,fc_reg_number, fc_chasis_color, fc_chasis_number, fc_status) values (1002, 'C124', 'Red', '456fdfa', 1);
insert into fl_car (fc_id,fc_reg_number, fc_chasis_color, fc_chasis_number, fc_status) values (1003, 'C125', 'Blue', '456gsd', 1);
insert into fl_car (fc_id,fc_reg_number, fc_chasis_color, fc_chasis_number, fc_status) values (1004, 'C126', 'Black', '565sa', 1);
insert into fl_car (fc_id,fc_reg_number, fc_chasis_color, fc_chasis_number, fc_status) values (1005, 'C127', 'White', 'fdsf56', 1);
insert into fl_car (fc_id,fc_reg_number, fc_chasis_color, fc_chasis_number, fc_status) values (1006, 'C128', 'Yellow', 'fasf566', 1);
insert into fl_car (fc_id,fc_reg_number, fc_chasis_color, fc_chasis_number, fc_status) values (1007, 'C129', 'Orange', 'fcefzaf546', 1);
insert into fl_car (fc_id,fc_reg_number, fc_chasis_color, fc_chasis_number, fc_status) values (1008, 'C120', 'Pink', 'gsgsa545', 1);
insert into fl_car (fc_id,fc_reg_number, fc_chasis_color, fc_chasis_number, fc_status) values (1009, 'C131', 'Silver', 'dhgs654', 1);

insert into FL_ENGINE values (1001, '1L','jfkjolp45984sf', 1);
insert into FL_ENGINE values (1002, '2L','jkpoij156sdf56', 1);
insert into FL_ENGINE values (1003, '4L','ijulkl561sda55', 1);
insert into FL_ENGINE values (1004, '3L','hienxnsj149515', 1);
insert into FL_ENGINE values (1005, '1L','iijonkjs256985', 1);
insert into FL_ENGINE values (1006, '2L','kpmmkn1456sdsf', 1);
insert into FL_ENGINE values (1007, '2.5L','jia45456wswd56', 1);
insert into FL_ENGINE values (1008, '3L','4564sdasda4565', 1);
insert into FL_ENGINE values (1009, '1.5L','dasdax5465a445', 1);

insert into FL_DRIVER values (1001, 'Joseph', 'Cooray', 'JS48464', 1);
insert into FL_DRIVER values (1002, 'Joseph', 'Cooray', 'JS48464', 1);
insert into FL_DRIVER values (1003, 'Joseph', 'Cooray', 'JS48464', 1);
insert into FL_DRIVER values (1004, 'Joseph', 'Cooray', 'JS48464', 1);
insert into FL_DRIVER values (1005, 'Joseph', 'Cooray', 'JS48464', 1);

insert into FL_TRIP (ft_id, ft_trip_date, ft_distance, ft_number, ft_status, fc_id, fd_id) values (1001, '2020-12-01', 200.50, '1001T', 1, 1001, 1002);
insert into FL_TRIP (ft_id, ft_trip_date, ft_distance, ft_number, ft_status, fc_id, fd_id) values (1002, '2020-11-02', 300.50, '1002T', 1, 1003, 1003);
insert into FL_TRIP (ft_id, ft_trip_date, ft_distance, ft_number, ft_status, fc_id, fd_id) values (1003, '2021-02-01', 400.50, '1003T', 1, 1002, 1005);
insert into FL_TRIP (ft_id, ft_trip_date, ft_distance, ft_number, ft_status, fc_id, fd_id) values (1004, '2021-03-01', 500.50, '1004T', 1, 1004, 1004);
insert into FL_TRIP (ft_id, ft_trip_date, ft_distance, ft_number, ft_status, fc_id, fd_id) values (1005, '2020-10-01', 600.50, '1005T', 1, 1006, 1005);
insert into FL_TRIP (ft_id, ft_trip_date, ft_distance, ft_number, ft_status, fc_id, fd_id) values (1006, '2020-12-12', 700.50, '1006T', 1, 1007, 1003);
insert into FL_TRIP (ft_id, ft_trip_date, ft_distance, ft_number, ft_status, fc_id, fd_id) values (1007, '2021-01-01', 800.50, '1007T', 1, 1008, 1002);
insert into FL_TRIP (ft_id, ft_trip_date, ft_distance, ft_number, ft_status, fc_id, fd_id) values (1008, '2021-01-05', 250.50, '1008T', 1, 1009, 1001);
insert into FL_TRIP (ft_id, ft_trip_date, ft_distance, ft_number, ft_status, fc_id, fd_id) values (1009, '2020-12-15', 260.50, '1009T', 1, 1002, 1004);
insert into FL_TRIP (ft_id, ft_trip_date, ft_distance, ft_number, ft_status, fc_id, fd_id) values (1010, '2021-02-25', 270.50, '1010T', 1, 1004, 1004);


