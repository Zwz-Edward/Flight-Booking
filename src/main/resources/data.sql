-- =============================================
-- 初始化数据: 大量航班数据 (30+条航线)
-- 使用 INSERT IGNORE 避免重复导入
-- =============================================

-- 用户通过注册 API 创建（BCrypt 密码哈希）
-- curl -X POST http://localhost:8080/api/auth/register ...

-- =============================================
-- 北京始发航线
-- =============================================

INSERT IGNORE INTO fb_flight (flight_no, airline, departure_city, arrival_city, departure_airport, arrival_airport, departure_time, arrival_time, price, economy_price, business_price, first_class_price, economy_seats, business_seats, first_class_seats, remaining_seats, status, created_at, updated_at) VALUES
-- 北京 → 上海 (多条)
('CA1234', '中国国航', '北京', '上海', '首都国际机场', '虹桥国际机场', DATE_ADD(CURDATE(), INTERVAL 8 HOUR), DATE_ADD(CURDATE(), INTERVAL 10 HOUR), 80000, 80000, 180000, 300000, 50, 10, 5, 65, 'SCHEDULED', NOW(), NOW()),
('CA1236', '中国国航', '北京', '上海', '首都国际机场', '浦东国际机场', DATE_ADD(CURDATE(), INTERVAL 12 HOUR), DATE_ADD(CURDATE(), INTERVAL 14 HOUR), 85000, 85000, 190000, 320000, 45, 8, 4, 57, 'SCHEDULED', NOW(), NOW()),
('MU5102', '东方航空', '北京', '上海', '大兴国际机场', '虹桥国际机场', DATE_ADD(CURDATE(), INTERVAL 7 HOUR), DATE_ADD(CURDATE(), INTERVAL 9 HOUR), 78000, 78000, 170000, 290000, 55, 12, 6, 73, 'SCHEDULED', NOW(), NOW()),
('MU5108', '东方航空', '北京', '上海', '大兴国际机场', '浦东国际机场', DATE_ADD(CURDATE(), INTERVAL 15 HOUR), DATE_ADD(CURDATE(), INTERVAL 17 HOUR), 82000, 82000, 185000, 310000, 48, 10, 5, 63, 'SCHEDULED', NOW(), NOW()),
('HU7601', '海南航空', '北京', '上海', '首都国际机场', '虹桥国际机场', DATE_ADD(CURDATE(), INTERVAL 6 HOUR), DATE_ADD(CURDATE(), INTERVAL 8 HOUR), 75000, 75000, 160000, 280000, 60, 14, 7, 81, 'SCHEDULED', NOW(), NOW()),

-- 北京 → 广州
('CA3456', '中国国航', '北京', '广州', '首都国际机场', '白云国际机场', DATE_ADD(CURDATE(), INTERVAL 8 HOUR), DATE_ADD(CURDATE(), INTERVAL 11 HOUR), 120000, 120000, 250000, 400000, 40, 8, 3, 51, 'SCHEDULED', NOW(), NOW()),
('CZ3102', '南方航空', '北京', '广州', '大兴国际机场', '白云国际机场', DATE_ADD(CURDATE(), INTERVAL 14 HOUR), DATE_ADD(CURDATE(), INTERVAL 17 HOUR), 115000, 115000, 240000, 390000, 42, 9, 4, 55, 'SCHEDULED', NOW(), NOW()),
('CA1831', '中国国航', '北京', '广州', '首都国际机场', '白云国际机场', DATE_ADD(CURDATE(), INTERVAL 6 HOUR), DATE_ADD(CURDATE(), INTERVAL 9 HOUR), 125000, 125000, 260000, 410000, 38, 7, 3, 48, 'SCHEDULED', NOW(), NOW()),

-- 北京 → 深圳
('CA1383', '中国国航', '北京', '深圳', '首都国际机场', '宝安国际机场', DATE_ADD(CURDATE(), INTERVAL 9 HOUR), DATE_ADD(CURDATE(), INTERVAL 12 HOUR), 130000, 130000, 270000, 430000, 35, 7, 3, 45, 'SCHEDULED', NOW(), NOW()),
('CZ3152', '南方航空', '北京', '深圳', '大兴国际机场', '宝安国际机场', DATE_ADD(CURDATE(), INTERVAL 16 HOUR), DATE_ADD(CURDATE(), INTERVAL 19 HOUR), 128000, 128000, 265000, 420000, 40, 8, 4, 52, 'SCHEDULED', NOW(), NOW()),

-- 北京 → 成都
('CA4198', '中国国航', '北京', '成都', '首都国际机场', '天府国际机场', DATE_ADD(CURDATE(), INTERVAL 7 HOUR), DATE_ADD(CURDATE(), INTERVAL 10 HOUR), 100000, 100000, 220000, 360000, 50, 10, 5, 65, 'SCHEDULED', NOW(), NOW()),
('3U8882', '四川航空', '北京', '成都', '首都国际机场', '双流国际机场', DATE_ADD(CURDATE(), INTERVAL 13 HOUR), DATE_ADD(CURDATE(), INTERVAL 16 HOUR), 95000, 95000, 210000, 340000, 52, 11, 5, 68, 'SCHEDULED', NOW(), NOW()),
('CA4102', '中国国航', '北京', '成都', '大兴国际机场', '天府国际机场', DATE_ADD(CURDATE(), INTERVAL 17 HOUR), DATE_ADD(CURDATE(), INTERVAL 20 HOUR), 105000, 105000, 230000, 370000, 45, 9, 4, 58, 'SCHEDULED', NOW(), NOW()),

-- 北京 → 重庆
('CA9851', '中国国航', '北京', '重庆', '首都国际机场', '江北国际机场', DATE_ADD(CURDATE(), INTERVAL 11 HOUR), DATE_ADD(CURDATE(), INTERVAL 14 HOUR), 98000, 98000, 210000, 350000, 48, 10, 4, 62, 'SCHEDULED', NOW(), NOW()),
('3U8830', '四川航空', '北京', '重庆', '首都国际机场', '江北国际机场', DATE_ADD(CURDATE(), INTERVAL 6 HOUR), DATE_ADD(CURDATE(), INTERVAL 9 HOUR), 92000, 92000, 200000, 330000, 55, 12, 6, 73, 'SCHEDULED', NOW(), NOW()),

-- 北京 → 杭州
('CA1702', '中国国航', '北京', '杭州', '首都国际机场', '萧山国际机场', DATE_ADD(CURDATE(), INTERVAL 10 HOUR), DATE_ADD(CURDATE(), INTERVAL 12 HOUR), 70000, 70000, 150000, 260000, 55, 12, 5, 72, 'SCHEDULED', NOW(), NOW()),
('MF8148', '厦门航空', '北京', '杭州', '大兴国际机场', '萧山国际机场', DATE_ADD(CURDATE(), INTERVAL 15 HOUR), DATE_ADD(CURDATE(), INTERVAL 17 HOUR), 68000, 68000, 145000, 250000, 58, 13, 6, 77, 'SCHEDULED', NOW(), NOW()),

-- 北京 → 西安
('CA1202', '中国国航', '北京', '西安', '首都国际机场', '咸阳国际机场', DATE_ADD(CURDATE(), INTERVAL 9 HOUR), DATE_ADD(CURDATE(), INTERVAL 11 HOUR), 55000, 55000, 130000, 220000, 60, 12, 6, 78, 'SCHEDULED', NOW(), NOW()),
('HU7137', '海南航空', '北京', '西安', '首都国际机场', '咸阳国际机场', DATE_ADD(CURDATE(), INTERVAL 14 HOUR), DATE_ADD(CURDATE(), INTERVAL 16 HOUR), 52000, 52000, 120000, 210000, 65, 14, 7, 86, 'SCHEDULED', NOW(), NOW()),

-- 北京 → 昆明
('CA1404', '中国国航', '北京', '昆明', '首都国际机场', '长水国际机场', DATE_ADD(CURDATE(), INTERVAL 8 HOUR), DATE_ADD(CURDATE(), INTERVAL 12 HOUR), 140000, 140000, 300000, 480000, 35, 8, 3, 46, 'SCHEDULED', NOW(), NOW()),
('3U8206', '四川航空', '北京', '昆明', '首都国际机场', '长水国际机场', DATE_ADD(CURDATE(), INTERVAL 13 HOUR), DATE_ADD(CURDATE(), INTERVAL 17 HOUR), 135000, 135000, 290000, 460000, 38, 8, 4, 50, 'SCHEDULED', NOW(), NOW()),

-- 北京 → 厦门
('CA1809', '中国国航', '北京', '厦门', '首都国际机场', '高崎国际机场', DATE_ADD(CURDATE(), INTERVAL 7 HOUR), DATE_ADD(CURDATE(), INTERVAL 10 HOUR), 95000, 95000, 210000, 340000, 42, 9, 4, 55, 'SCHEDULED', NOW(), NOW()),
('MF8102', '厦门航空', '北京', '厦门', '大兴国际机场', '高崎国际机场', DATE_ADD(CURDATE(), INTERVAL 12 HOUR), DATE_ADD(CURDATE(), INTERVAL 15 HOUR), 90000, 90000, 200000, 320000, 45, 10, 5, 60, 'SCHEDULED', NOW(), NOW()),

-- 北京 → 武汉
('CA8201', '中国国航', '北京', '武汉', '首都国际机场', '天河国际机场', DATE_ADD(CURDATE(), INTERVAL 10 HOUR), DATE_ADD(CURDATE(), INTERVAL 12 HOUR), 60000, 60000, 140000, 230000, 55, 12, 5, 72, 'SCHEDULED', NOW(), NOW()),
('CZ3138', '南方航空', '北京', '武汉', '大兴国际机场', '天河国际机场', DATE_ADD(CURDATE(), INTERVAL 16 HOUR), DATE_ADD(CURDATE(), INTERVAL 18 HOUR), 58000, 58000, 135000, 220000, 58, 13, 6, 77, 'SCHEDULED', NOW(), NOW());

-- =============================================
-- 上海始发航线
-- =============================================

INSERT IGNORE INTO fb_flight (flight_no, airline, departure_city, arrival_city, departure_airport, arrival_airport, departure_time, arrival_time, price, economy_price, business_price, first_class_price, economy_seats, business_seats, first_class_seats, remaining_seats, status, created_at, updated_at) VALUES
('MU5678', '东方航空', '上海', '北京', '浦东国际机场', '首都国际机场', DATE_ADD(CURDATE(), INTERVAL 9 HOUR), DATE_ADD(CURDATE(), INTERVAL 11 HOUR), 85000, 85000, 190000, 320000, 45, 8, 4, 57, 'SCHEDULED', NOW(), NOW()),
('MU5101', '东方航空', '上海', '北京', '虹桥国际机场', '大兴国际机场', DATE_ADD(CURDATE(), INTERVAL 14 HOUR), DATE_ADD(CURDATE(), INTERVAL 16 HOUR), 80000, 80000, 175000, 300000, 50, 10, 5, 65, 'SCHEDULED', NOW(), NOW()),

-- 上海 → 广州
('CZ3532', '南方航空', '上海', '广州', '浦东国际机场', '白云国际机场', DATE_ADD(CURDATE(), INTERVAL 8 HOUR), DATE_ADD(CURDATE(), INTERVAL 10 HOUR), 90000, 90000, 200000, 340000, 48, 10, 5, 63, 'SCHEDULED', NOW(), NOW()),
('CA1833', '中国国航', '上海', '广州', '虹桥国际机场', '白云国际机场', DATE_ADD(CURDATE(), INTERVAL 13 HOUR), DATE_ADD(CURDATE(), INTERVAL 15 HOUR), 88000, 88000, 195000, 330000, 50, 11, 5, 66, 'SCHEDULED', NOW(), NOW()),

-- 上海 → 深圳
('CZ9502', '南方航空', '上海', '深圳', '浦东国际机场', '宝安国际机场', DATE_ADD(CURDATE(), INTERVAL 11 HOUR), DATE_ADD(CURDATE(), INTERVAL 13 HOUR), 95000, 95000, 210000, 350000, 45, 9, 4, 58, 'SCHEDULED', NOW(), NOW()),
('MU5311', '东方航空', '上海', '深圳', '虹桥国际机场', '宝安国际机场', DATE_ADD(CURDATE(), INTERVAL 16 HOUR), DATE_ADD(CURDATE(), INTERVAL 18 HOUR), 92000, 92000, 205000, 340000, 48, 10, 5, 63, 'SCHEDULED', NOW(), NOW()),

-- 上海 → 成都
('MU7890', '东方航空', '上海', '成都', '浦东国际机场', '天府国际机场', DATE_ADD(CURDATE(), INTERVAL 7 HOUR), DATE_ADD(CURDATE(), INTERVAL 10 HOUR), 90000, 90000, 200000, 350000, 55, 10, 5, 70, 'SCHEDULED', NOW(), NOW()),
('CA4590', '中国国航', '上海', '成都', '虹桥国际机场', '双流国际机场', DATE_ADD(CURDATE(), INTERVAL 14 HOUR), DATE_ADD(CURDATE(), INTERVAL 17 HOUR), 95000, 95000, 210000, 360000, 50, 9, 4, 63, 'SCHEDULED', NOW(), NOW()),
('3U8962', '四川航空', '上海', '成都', '浦东国际机场', '天府国际机场', DATE_ADD(CURDATE(), INTERVAL 18 HOUR), DATE_ADD(CURDATE(), INTERVAL 21 HOUR), 87000, 87000, 190000, 330000, 52, 11, 5, 68, 'SCHEDULED', NOW(), NOW()),

-- 上海 → 昆明
('MU5801', '东方航空', '上海', '昆明', '浦东国际机场', '长水国际机场', DATE_ADD(CURDATE(), INTERVAL 8 HOUR), DATE_ADD(CURDATE(), INTERVAL 12 HOUR), 120000, 120000, 260000, 420000, 40, 8, 4, 52, 'SCHEDULED', NOW(), NOW()),
('CZ3679', '南方航空', '上海', '昆明', '虹桥国际机场', '长水国际机场', DATE_ADD(CURDATE(), INTERVAL 15 HOUR), DATE_ADD(CURDATE(), INTERVAL 19 HOUR), 125000, 125000, 270000, 430000, 38, 7, 3, 48, 'SCHEDULED', NOW(), NOW()),

-- 上海 → 西安
('MU2161', '东方航空', '上海', '西安', '虹桥国际机场', '咸阳国际机场', DATE_ADD(CURDATE(), INTERVAL 10 HOUR), DATE_ADD(CURDATE(), INTERVAL 12 HOUR), 65000, 65000, 150000, 250000, 55, 12, 6, 73, 'SCHEDULED', NOW(), NOW()),
('CA1216', '中国国航', '上海', '西安', '浦东国际机场', '咸阳国际机场', DATE_ADD(CURDATE(), INTERVAL 18 HOUR), DATE_ADD(CURDATE(), INTERVAL 20 HOUR), 62000, 62000, 140000, 240000, 58, 13, 6, 77, 'SCHEDULED', NOW(), NOW());

-- =============================================
-- 广深始发航线
-- =============================================

INSERT IGNORE INTO fb_flight (flight_no, airline, departure_city, arrival_city, departure_airport, arrival_airport, departure_time, arrival_time, price, economy_price, business_price, first_class_price, economy_seats, business_seats, first_class_seats, remaining_seats, status, created_at, updated_at) VALUES
-- 广州 → 深圳
('CZ9012', '南方航空', '广州', '深圳', '白云国际机场', '宝安国际机场', DATE_ADD(CURDATE(), INTERVAL 8 HOUR), DATE_ADD(CURDATE(), INTERVAL 9 HOUR), 30000, 30000, 80000, 150000, 60, 12, 6, 78, 'SCHEDULED', NOW(), NOW()),
('CZ9016', '南方航空', '广州', '深圳', '白云国际机场', '宝安国际机场', DATE_ADD(CURDATE(), INTERVAL 14 HOUR), DATE_ADD(CURDATE(), INTERVAL 15 HOUR), 28000, 28000, 75000, 140000, 65, 14, 7, 86, 'SCHEDULED', NOW(), NOW()),

-- 广州 → 成都
('CZ3437', '南方航空', '广州', '成都', '白云国际机场', '天府国际机场', DATE_ADD(CURDATE(), INTERVAL 9 HOUR), DATE_ADD(CURDATE(), INTERVAL 12 HOUR), 85000, 85000, 190000, 320000, 50, 10, 5, 65, 'SCHEDULED', NOW(), NOW()),
('CA4306', '中国国航', '广州', '成都', '白云国际机场', '双流国际机场', DATE_ADD(CURDATE(), INTERVAL 16 HOUR), DATE_ADD(CURDATE(), INTERVAL 19 HOUR), 82000, 82000, 185000, 310000, 52, 11, 5, 68, 'SCHEDULED', NOW(), NOW()),

-- 深圳 → 北京
('CZ1111', '南方航空', '深圳', '北京', '宝安国际机场', '首都国际机场', DATE_ADD(CURDATE(), INTERVAL 11 HOUR), DATE_ADD(CURDATE(), INTERVAL 14 HOUR), 110000, 110000, 230000, 380000, 35, 6, 3, 44, 'SCHEDULED', NOW(), NOW()),
('ZH9101', '深圳航空', '深圳', '北京', '宝安国际机场', '大兴国际机场', DATE_ADD(CURDATE(), INTERVAL 7 HOUR), DATE_ADD(CURDATE(), INTERVAL 10 HOUR), 105000, 105000, 220000, 370000, 40, 8, 4, 52, 'SCHEDULED', NOW(), NOW()),
('CA1356', '中国国航', '深圳', '北京', '宝安国际机场', '首都国际机场', DATE_ADD(CURDATE(), INTERVAL 17 HOUR), DATE_ADD(CURDATE(), INTERVAL 20 HOUR), 108000, 108000, 225000, 375000, 38, 7, 3, 48, 'SCHEDULED', NOW(), NOW()),

-- 深圳 → 成都
('ZH9423', '深圳航空', '深圳', '成都', '宝安国际机场', '天府国际机场', DATE_ADD(CURDATE(), INTERVAL 10 HOUR), DATE_ADD(CURDATE(), INTERVAL 13 HOUR), 88000, 88000, 190000, 320000, 48, 10, 5, 63, 'SCHEDULED', NOW(), NOW()),
('3U8706', '四川航空', '深圳', '成都', '宝安国际机场', '双流国际机场', DATE_ADD(CURDATE(), INTERVAL 18 HOUR), DATE_ADD(CURDATE(), INTERVAL 21 HOUR), 85000, 85000, 185000, 310000, 50, 11, 5, 66, 'SCHEDULED', NOW(), NOW()),

-- 深圳 → 杭州
('ZH9887', '深圳航空', '深圳', '杭州', '宝安国际机场', '萧山国际机场', DATE_ADD(CURDATE(), INTERVAL 12 HOUR), DATE_ADD(CURDATE(), INTERVAL 14 HOUR), 65000, 65000, 150000, 260000, 52, 11, 5, 68, 'SCHEDULED', NOW(), NOW()),
('MF8392', '厦门航空', '深圳', '杭州', '宝安国际机场', '萧山国际机场', DATE_ADD(CURDATE(), INTERVAL 6 HOUR), DATE_ADD(CURDATE(), INTERVAL 8 HOUR), 62000, 62000, 140000, 250000, 55, 12, 6, 73, 'SCHEDULED', NOW(), NOW());

-- =============================================
-- 其他城市航线
-- =============================================

INSERT IGNORE INTO fb_flight (flight_no, airline, departure_city, arrival_city, departure_airport, arrival_airport, departure_time, arrival_time, price, economy_price, business_price, first_class_price, economy_seats, business_seats, first_class_seats, remaining_seats, status, created_at, updated_at) VALUES
-- 成都 → 重庆 (短途)
('CA3333', '中国国航', '成都', '重庆', '天府国际机场', '江北国际机场', DATE_ADD(CURDATE(), INTERVAL 10 HOUR), DATE_ADD(CURDATE(), INTERVAL 11 HOUR), 20000, 20000, 50000, 100000, 70, 15, 8, 93, 'SCHEDULED', NOW(), NOW()),
('3U8633', '四川航空', '成都', '重庆', '双流国际机场', '江北国际机场', DATE_ADD(CURDATE(), INTERVAL 16 HOUR), DATE_ADD(CURDATE(), INTERVAL 17 HOUR), 18000, 18000, 45000, 90000, 75, 16, 8, 99, 'SCHEDULED', NOW(), NOW()),

-- 成都 → 西安
('3U8311', '四川航空', '成都', '西安', '天府国际机场', '咸阳国际机场', DATE_ADD(CURDATE(), INTERVAL 8 HOUR), DATE_ADD(CURDATE(), INTERVAL 10 HOUR), 45000, 45000, 110000, 200000, 55, 12, 6, 73, 'SCHEDULED', NOW(), NOW()),
('CA4202', '中国国航', '成都', '西安', '双流国际机场', '咸阳国际机场', DATE_ADD(CURDATE(), INTERVAL 14 HOUR), DATE_ADD(CURDATE(), INTERVAL 16 HOUR), 42000, 42000, 105000, 190000, 58, 13, 6, 77, 'SCHEDULED', NOW(), NOW()),

-- 成都 → 昆明
('3U8651', '四川航空', '成都', '昆明', '天府国际机场', '长水国际机场', DATE_ADD(CURDATE(), INTERVAL 9 HOUR), DATE_ADD(CURDATE(), INTERVAL 11 HOUR), 40000, 40000, 100000, 180000, 55, 12, 6, 73, 'SCHEDULED', NOW(), NOW()),
('CA4521', '中国国航', '成都', '昆明', '双流国际机场', '长水国际机场', DATE_ADD(CURDATE(), INTERVAL 17 HOUR), DATE_ADD(CURDATE(), INTERVAL 19 HOUR), 38000, 38000, 95000, 170000, 58, 13, 6, 77, 'SCHEDULED', NOW(), NOW()),

-- 杭州 → 西安
('MF2222', '厦门航空', '杭州', '西安', '萧山国际机场', '咸阳国际机场', DATE_ADD(CURDATE(), INTERVAL 7 HOUR), DATE_ADD(CURDATE(), INTERVAL 9 HOUR), 60000, 60000, 140000, 250000, 50, 8, 4, 62, 'SCHEDULED', NOW(), NOW()),
('CA1774', '中国国航', '杭州', '西安', '萧山国际机场', '咸阳国际机场', DATE_ADD(CURDATE(), INTERVAL 13 HOUR), DATE_ADD(CURDATE(), INTERVAL 15 HOUR), 58000, 58000, 135000, 240000, 52, 10, 5, 67, 'SCHEDULED', NOW(), NOW()),

-- 杭州 → 厦门
('MF8153', '厦门航空', '杭州', '厦门', '萧山国际机场', '高崎国际机场', DATE_ADD(CURDATE(), INTERVAL 8 HOUR), DATE_ADD(CURDATE(), INTERVAL 10 HOUR), 48000, 48000, 110000, 190000, 55, 12, 6, 73, 'SCHEDULED', NOW(), NOW()),
('CA1764', '中国国航', '杭州', '厦门', '萧山国际机场', '高崎国际机场', DATE_ADD(CURDATE(), INTERVAL 15 HOUR), DATE_ADD(CURDATE(), INTERVAL 17 HOUR), 45000, 45000, 105000, 180000, 58, 13, 6, 77, 'SCHEDULED', NOW(), NOW()),

-- 武汉 → 成都
('CZ5441', '南方航空', '武汉', '成都', '天河国际机场', '天府国际机场', DATE_ADD(CURDATE(), INTERVAL 9 HOUR), DATE_ADD(CURDATE(), INTERVAL 11 HOUR), 55000, 55000, 130000, 220000, 50, 10, 5, 65, 'SCHEDULED', NOW(), NOW()),
('CA8282', '中国国航', '武汉', '成都', '天河国际机场', '双流国际机场', DATE_ADD(CURDATE(), INTERVAL 16 HOUR), DATE_ADD(CURDATE(), INTERVAL 18 HOUR), 52000, 52000, 125000, 210000, 52, 11, 5, 68, 'SCHEDULED', NOW(), NOW()),

-- 长沙 → 北京
('CA1386', '中国国航', '长沙', '北京', '黄花国际机场', '首都国际机场', DATE_ADD(CURDATE(), INTERVAL 11 HOUR), DATE_ADD(CURDATE(), INTERVAL 13 HOUR), 70000, 70000, 160000, 270000, 48, 10, 5, 63, 'SCHEDULED', NOW(), NOW()),
('HU7636', '海南航空', '长沙', '北京', '黄花国际机场', '大兴国际机场', DATE_ADD(CURDATE(), INTERVAL 18 HOUR), DATE_ADD(CURDATE(), INTERVAL 20 HOUR), 68000, 68000, 155000, 260000, 50, 11, 5, 66, 'SCHEDULED', NOW(), NOW()),

-- 青岛 → 上海
('MU5516', '东方航空', '青岛', '上海', '胶东国际机场', '虹桥国际机场', DATE_ADD(CURDATE(), INTERVAL 10 HOUR), DATE_ADD(CURDATE(), INTERVAL 12 HOUR), 42000, 42000, 100000, 180000, 55, 12, 6, 73, 'SCHEDULED', NOW(), NOW()),
('SC4601', '山东航空', '青岛', '上海', '胶东国际机场', '浦东国际机场', DATE_ADD(CURDATE(), INTERVAL 16 HOUR), DATE_ADD(CURDATE(), INTERVAL 18 HOUR), 40000, 40000, 95000, 170000, 58, 13, 6, 77, 'SCHEDULED', NOW(), NOW()),

-- 南京 → 北京
('CA1564', '中国国航', '南京', '北京', '禄口国际机场', '首都国际机场', DATE_ADD(CURDATE(), INTERVAL 9 HOUR), DATE_ADD(CURDATE(), INTERVAL 11 HOUR), 55000, 55000, 130000, 220000, 52, 11, 5, 68, 'SCHEDULED', NOW(), NOW()),
('MU2801', '东方航空', '南京', '北京', '禄口国际机场', '大兴国际机场', DATE_ADD(CURDATE(), INTERVAL 15 HOUR), DATE_ADD(CURDATE(), INTERVAL 17 HOUR), 52000, 52000, 125000, 210000, 55, 12, 6, 73, 'SCHEDULED', NOW(), NOW()),

-- 沈阳 → 上海
('CZ6501', '南方航空', '沈阳', '上海', '桃仙国际机场', '浦东国际机场', DATE_ADD(CURDATE(), INTERVAL 8 HOUR), DATE_ADD(CURDATE(), INTERVAL 11 HOUR), 75000, 75000, 170000, 290000, 45, 9, 4, 58, 'SCHEDULED', NOW(), NOW()),
('MU5608', '东方航空', '沈阳', '上海', '桃仙国际机场', '虹桥国际机场', DATE_ADD(CURDATE(), INTERVAL 14 HOUR), DATE_ADD(CURDATE(), INTERVAL 17 HOUR), 72000, 72000, 160000, 280000, 48, 10, 5, 63, 'SCHEDULED', NOW(), NOW()),

-- 三亚 → 北京
('HU7279', '海南航空', '三亚', '北京', '凤凰国际机场', '首都国际机场', DATE_ADD(CURDATE(), INTERVAL 8 HOUR), DATE_ADD(CURDATE(), INTERVAL 12 HOUR), 150000, 150000, 320000, 500000, 35, 6, 3, 44, 'SCHEDULED', NOW(), NOW()),
('CA1346', '中国国航', '三亚', '北京', '凤凰国际机场', '大兴国际机场', DATE_ADD(CURDATE(), INTERVAL 14 HOUR), DATE_ADD(CURDATE(), INTERVAL 18 HOUR), 145000, 145000, 300000, 480000, 38, 8, 4, 50, 'SCHEDULED', NOW(), NOW()),

-- 贵阳 → 广州
('CZ3661', '南方航空', '贵阳', '广州', '龙洞堡国际机场', '白云国际机场', DATE_ADD(CURDATE(), INTERVAL 10 HOUR), DATE_ADD(CURDATE(), INTERVAL 12 HOUR), 48000, 48000, 110000, 200000, 52, 11, 5, 68, 'SCHEDULED', NOW(), NOW()),
('CA4360', '中国国航', '贵阳', '广州', '龙洞堡国际机场', '白云国际机场', DATE_ADD(CURDATE(), INTERVAL 17 HOUR), DATE_ADD(CURDATE(), INTERVAL 19 HOUR), 45000, 45000, 105000, 190000, 55, 12, 6, 73, 'SCHEDULED', NOW(), NOW()),

-- 乌鲁木齐 → 北京
('CA1292', '中国国航', '乌鲁木齐', '北京', '地窝堡国际机场', '首都国际机场', DATE_ADD(CURDATE(), INTERVAL 7 HOUR), DATE_ADD(CURDATE(), INTERVAL 12 HOUR), 180000, 180000, 380000, 600000, 30, 6, 2, 38, 'SCHEDULED', NOW(), NOW()),
('HU7146', '海南航空', '乌鲁木齐', '北京', '地窝堡国际机场', '大兴国际机场', DATE_ADD(CURDATE(), INTERVAL 14 HOUR), DATE_ADD(CURDATE(), INTERVAL 19 HOUR), 175000, 175000, 360000, 580000, 32, 7, 3, 42, 'SCHEDULED', NOW(), NOW()),

-- 哈尔滨 → 深圳
('CZ6274', '南方航空', '哈尔滨', '深圳', '太平国际机场', '宝安国际机场', DATE_ADD(CURDATE(), INTERVAL 8 HOUR), DATE_ADD(CURDATE(), INTERVAL 13 HOUR), 160000, 160000, 340000, 520000, 32, 6, 3, 41, 'SCHEDULED', NOW(), NOW()),
('ZH9624', '深圳航空', '哈尔滨', '深圳', '太平国际机场', '宝安国际机场', DATE_ADD(CURDATE(), INTERVAL 15 HOUR), DATE_ADD(CURDATE(), INTERVAL 20 HOUR), 155000, 155000, 330000, 500000, 35, 7, 4, 46, 'SCHEDULED', NOW(), NOW());
