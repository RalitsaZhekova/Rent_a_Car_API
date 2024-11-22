CREATE TABLE IF NOT EXISTS td_clients (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(256),
    address VARCHAR(256),
    phone VARCHAR(256),
    age INT,
    has_accidents INT DEFAULT 0,
    is_active INT DEFAULT 1
);

CREATE TABLE IF NOT EXISTS td_cars (
    id INT PRIMARY KEY AUTO_INCREMENT,
    model VARCHAR(256),
    price_per_day DECIMAL(10, 2),
    city VARCHAR(64),
    is_active INT DEFAULT 1
);

CREATE TABLE IF NOT EXISTS td_offers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    total_price DECIMAL(10, 2),
    client_id INT,
    car_id INT,
    is_accepted INT DEFAULT 0,
    is_active INT DEFAULT 1
);