CREATE
database climate_db;

USE
climate_db;

CREATE TABLE systems
(
    system_id  INT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    profile_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (profile_id) REFERENCES profiles (profile_id)
);

CREATE TABLE users
(
    user_id    INT AUTO_INCREMENT PRIMARY KEY,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE devices
(
    device_id  INT AUTO_INCREMENT PRIMARY KEY,
    serial     VARCHAR(100)   NOT NULL,
    name       VARCHAR(100)   NOT NULL,
    power      DECIMAL(10, 2) NOT NULL,
    mode       VARCHAR(25)    NOT NULL,
    type       VARCHAR(25)    NOT NULL,
    system_id  INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (system_id) REFERENCES systems (system_id)
);

CREATE TABLE sensors
(
    sensor_id  INT AUTO_INCREMENT PRIMARY KEY,
    serial     VARCHAR(100) NOT NULL,
    name       VARCHAR(100) NOT NULL,
    type       VARCHAR(25)  NOT NULL,
    system_id  INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_sync  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (system_id) REFERENCES systems (system_id)
);

CREATE TABLE measurements
(
    measurement_id INT AUTO_INCREMENT PRIMARY KEY,
    value          DECIMAL(10, 2) NOT NULL,
    sensor_id      INT            NOT NULL,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sensor_id) REFERENCES sensors (sensor_id)
);

CREATE TABLE commands
(
    command_id INT AUTO_INCREMENT PRIMARY KEY,
    device_id  INT            NOT NULL,
    value      DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (device_id) REFERENCES devices (device_id)
);

CREATE TABLE messages
(
    message_id INT AUTO_INCREMENT PRIMARY KEY,
    message    TEXT NOT NULL,
    user_id    INT  NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE profiles
(
    profile_id INT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    user_id    INT          NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE schedules
(
    schedule_id INT AUTO_INCREMENT PRIMARY KEY,
    start_time  TIME           NOT NULL,
    end_time    TIME           NOT NULL,
    start_date  DATE           NOT NULL,
    end_date    DATE           NOT NULL,
    temperature DECIMAL(10, 2) NOT NULL,
    humidity    DECIMAL(10, 2) NOT NULL,
    profile_id  INT            NOT NULL,
    FOREIGN KEY (profile_id) REFERENCES profiles (profile_id)
);

CREATE TABLE timers
(
    timer_id   INT AUTO_INCREMENT PRIMARY KEY,
    user_id    INT NOT NULL,
    system_id  INT NOT NULL,
    duration   INT NOT NULL,
    start_time TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    end_time   TIMESTAMP AS (start_time + INTERVAL duration MINUTE) STORED,
    status     VARCHAR(25) DEFAULT 'active',
    created_at TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (system_id) REFERENCES systems (system_id)
);

CREATE TABLE system_users
(
    user_id   INT NOT NULL,
    system_id INT NOT NULL,
    role      VARCHAR(25),
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (system_id) REFERENCES systems (system_id),
    PRIMARY KEY (user_id, system_id)
);