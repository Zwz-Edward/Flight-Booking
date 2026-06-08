-- =============================================
-- 初始化数据: 示例用户和航班
-- =============================================

-- 管理员用户 (密码: admin123)
INSERT INTO fb_user (username, password, email, phone, real_name, id_card, balance, role, created_at, updated_at)
VALUES ('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'admin@flight.com', '13800000000', '管理员', '110101199001011234', 99999999, 'ADMIN', NOW(), NOW());

-- 普通用户 (密码: 123456)
INSERT INTO fb_user (username, password, email, phone, real_name, id_card, balance, role, created_at, updated_at)
VALUES ('zhangsan', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'zhangsan@test.com', '13900001111', '张三', '11010119900315001X', 10000000, 'USER', NOW(), NOW());

-- 普通用户 (密码: 123456)
INSERT INTO fb_user (username, password, email, phone, real_name, id_card, balance, role, created_at, updated_at)
VALUES ('lisi', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'lisi@test.com', '13900002222', '李四', '11010119900520002X', 5000000, 'USER', NOW(), NOW());

-- =============================================
-- 示例航班数据
-- =============================================

-- 北京→上海
INSERT INTO fb_flight (flight_no, airline, departure_city, arrival_city, departure_airport, arrival_airport, departure_time, arrival_time, price, economy_price, business_price, first_class_price, economy_seats, business_seats, first_class_seats, remaining_seats, status, created_at, updated_at)
VALUES ('CA1234', '中国国航', '北京', '上海', '首都国际机场', '虹桥国际机场', TIMESTAMPADD(HOUR, 8, CURRENT_TIMESTAMP()), TIMESTAMPADD(HOUR, 10, CURRENT_TIMESTAMP()), 80000, 80000, 180000, 300000, 50, 10, 5, 65, 'SCHEDULED', NOW(), NOW());

-- 上海→北京
INSERT INTO fb_flight (flight_no, airline, departure_city, arrival_city, departure_airport, arrival_airport, departure_time, arrival_time, price, economy_price, business_price, first_class_price, economy_seats, business_seats, first_class_seats, remaining_seats, status, created_at, updated_at)
VALUES ('MU5678', '东方航空', '上海', '北京', '浦东国际机场', '首都国际机场', TIMESTAMPADD(HOUR, 10, CURRENT_TIMESTAMP()), TIMESTAMPADD(HOUR, 12, CURRENT_TIMESTAMP()), 85000, 85000, 190000, 320000, 45, 8, 4, 57, 'SCHEDULED', NOW(), NOW());

-- 广州→深圳
INSERT INTO fb_flight (flight_no, airline, departure_city, arrival_city, departure_airport, arrival_airport, departure_time, arrival_time, price, economy_price, business_price, first_class_price, economy_seats, business_seats, first_class_seats, remaining_seats, status, created_at, updated_at)
VALUES ('CZ9012', '南方航空', '广州', '深圳', '白云国际机场', '宝安国际机场', TIMESTAMPADD(HOUR, 9, CURRENT_TIMESTAMP()), TIMESTAMPADD(HOUR, 10, CURRENT_TIMESTAMP()), 30000, 30000, 80000, 150000, 60, 12, 6, 78, 'SCHEDULED', NOW(), NOW());

-- 北京→广州
INSERT INTO fb_flight (flight_no, airline, departure_city, arrival_city, departure_airport, arrival_airport, departure_time, arrival_time, price, economy_price, business_price, first_class_price, economy_seats, business_seats, first_class_seats, remaining_seats, status, created_at, updated_at)
VALUES ('CA3456', '中国国航', '北京', '广州', '首都国际机场', '白云国际机场', TIMESTAMPADD(HOUR, 14, CURRENT_TIMESTAMP()), TIMESTAMPADD(HOUR, 17, CURRENT_TIMESTAMP()), 120000, 120000, 250000, 400000, 40, 8, 3, 51, 'SCHEDULED', NOW(), NOW());

-- 上海→成都
INSERT INTO fb_flight (flight_no, airline, departure_city, arrival_city, departure_airport, arrival_airport, departure_time, arrival_time, price, economy_price, business_price, first_class_price, economy_seats, business_seats, first_class_seats, remaining_seats, status, created_at, updated_at)
VALUES ('MU7890', '东方航空', '上海', '成都', '浦东国际机场', '天府国际机场', TIMESTAMPADD(HOUR, 7, CURRENT_TIMESTAMP()), TIMESTAMPADD(HOUR, 10, CURRENT_TIMESTAMP()), 90000, 90000, 200000, 350000, 55, 10, 5, 70, 'SCHEDULED', NOW(), NOW());

-- 深圳→北京
INSERT INTO fb_flight (flight_no, airline, departure_city, arrival_city, departure_airport, arrival_airport, departure_time, arrival_time, price, economy_price, business_price, first_class_price, economy_seats, business_seats, first_class_seats, remaining_seats, status, created_at, updated_at)
VALUES ('CZ1111', '南方航空', '深圳', '北京', '宝安国际机场', '首都国际机场', TIMESTAMPADD(HOUR, 12, CURRENT_TIMESTAMP()), TIMESTAMPADD(HOUR, 15, CURRENT_TIMESTAMP()), 110000, 110000, 230000, 380000, 35, 6, 3, 44, 'SCHEDULED', NOW(), NOW());

-- 杭州→西安
INSERT INTO fb_flight (flight_no, airline, departure_city, arrival_city, departure_airport, arrival_airport, departure_time, arrival_time, price, economy_price, business_price, first_class_price, economy_seats, business_seats, first_class_seats, remaining_seats, status, created_at, updated_at)
VALUES ('MF2222', '厦门航空', '杭州', '西安', '萧山国际机场', '咸阳国际机场', TIMESTAMPADD(HOUR, 6, CURRENT_TIMESTAMP()), TIMESTAMPADD(HOUR, 8, CURRENT_TIMESTAMP()), 60000, 60000, 140000, 250000, 50, 8, 4, 62, 'SCHEDULED', NOW(), NOW());

-- 成都→重庆 (短途)
INSERT INTO fb_flight (flight_no, airline, departure_city, arrival_city, departure_airport, arrival_airport, departure_time, arrival_time, price, economy_price, business_price, first_class_price, economy_seats, business_seats, first_class_seats, remaining_seats, status, created_at, updated_at)
VALUES ('CA3333', '中国国航', '成都', '重庆', '天府国际机场', '江北国际机场', TIMESTAMPADD(HOUR, 11, CURRENT_TIMESTAMP()), TIMESTAMPADD(HOUR, 12, CURRENT_TIMESTAMP()), 20000, 20000, 50000, 100000, 70, 15, 8, 93, 'SCHEDULED', NOW(), NOW());
