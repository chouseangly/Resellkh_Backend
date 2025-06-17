CREATE TABLE users (
    user_id SERIAL PRIMARY KEY ,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    enabled BOOLEAN DEFAULT false
);

ALTER TABLE users ADD COLUMN
    enabled BOOLEAN DEFAULT false;

ALTER TABLE users ADD COLUMN
    role VARCHAR(15);
ALTER TABLE products ADD COLUMN
    discount_percent numeric(5,2);

ALTER TABLE otps ADD COLUMN
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

BEGIN;

DELETE FROM users WHERE user_id = 4;

SELECT setval('users_user_id_seq', 3);

COMMIT;

CREATE TABLE categories (
    category_id SERIAL PRIMARY KEY ,
    category_name VARCHAR(255) NOT NULL
);

CREATE TABLE products (
    product_id SERIAL PRIMARY KEY ,
    product_name VARCHAR(255) NOT NULL ,
    user_id BIGINT NOT NULL,
    category_id INT NOT NULL,
    product_condition VARCHAR(50),
    product_status VARCHAR(50),
    product_description TEXT,
    telegram_link VARCHAR(255),
    product_price DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories (category_id)
                      ON DELETE CASCADE
);
--
ALTER TABLE products
    ADD COLUMN latitude DOUBLE PRECISION,
    ADD COLUMN longitude DOUBLE PRECISION;



CREATE TABLE product_images (
    image_id SERIAL PRIMARY KEY ,
    product_id BIGINT NOT NULL ,
    image_url VARCHAR(500) NOT NULL,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products (product_id) ON DELETE CASCADE
);

ALTER TABLE product_images
    ADD COLUMN vector_data VARCHAR(8000);

ALTER TABLE product_images
    ALTER COLUMN vector_data TYPE JSONB
        USING vector_data::jsonb;

ALTER TABLE product_images ALTER COLUMN vector_data TYPE JSONB USING vector_data::text::jsonb;
-- Or if it was text: ALTER TABLE product_images ALTER COLUMN vector_data TYPE JSONB;
-- ---------------

CREATE TABLE rating (
    rating_id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    user_rated_id BIGINT NOT NULL,
    rating_value DOUBLE PRECISION NOT NULL,
    rating_description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE ,
    FOREIGN KEY (user_rated_id) REFERENCES users (user_id) ON DELETE CASCADE
);

CREATE TABLE favorites (
    favorite_id SERIAL PRIMARY KEY ,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE ,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);

CREATE TABLE notification (
    notification_id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    message TEXT NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);

CREATE TABLE product_history (
    product_history_id SERIAL PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    product_cover_url VARCHAR(255),
    product_description TEXT,
    product_price DECIMAL(10,2) NOT NULL,
    previous_status VARCHAR(50) NOT NULL,
    next_status VARCHAR(50) NOT NULL,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);

CREATE TABLE otp (
    otp_id SERIAL PRIMARY KEY ,
    user_id BIGINT NOT NULL,
    email VARCHAR(255) NOT NULL,
    otp_code INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NOT NULL,
    is_used BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE otps (
    otp_id SERIAL PRIMARY KEY ,
    email VARCHAR(255) NOT NULL,
    otp_code VARCHAR(10) NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    is_used BOOLEAN DEFAULT FALSE
);

CREATE TABLE user_profiles (
    profile_id SERIAL PRIMARY KEY,
    user_id BIGINT UNIQUE NOT NULL,
    profile_image_url VARCHAR(255),
    cover_image_url VARCHAR(255),
    user_name VARCHAR(100) UNIQUE ,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    slogan VARCHAR(255),
    location VARCHAR(255),
    telegram_url VARCHAR(255),
    phone_number VARCHAR(20),
    birth_day VARCHAR(10),
    gender VARCHAR(20),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE contact_info(
    contact_id SERIAL PRIMARY KEY ,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

UPDATE categories SET category_name = 'other' WHERE category_id = 10;

-- PostgreSQL Query to fetch ContactInfo-like data for a given user and product
SELECT
    up.profile_image_url,
    u.first_name,
    u.last_name,
    up.phone_number,
    p.telegram_link
FROM
    users u
        LEFT JOIN
    user_profiles up ON u.user_id = up.user_id
        JOIN
    products p ON p.user_id = u.user_id
WHERE p.product_id = 7;


