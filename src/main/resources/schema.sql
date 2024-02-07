CREATE TABLE IF NOT EXISTS zip_code (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        zip_code VARCHAR(255) NOT NULL,
    latitude DECIMAL(9,6) NOT NULL,
    longitude DECIMAL(9,6) NOT NULL
    );

INSERT INTO zip_code (zip_code, latitude, longitude) VALUES ('518001', 15.8281, 78.0373);
