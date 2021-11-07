create table tags
(
    id   BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(75) NOT NULL UNIQUE
);

create table gift_certificates
(
    id               BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(75)  NOT NULL,
    description      TINYTEXT(255) NOT NULL,
    price            DECIMAL(5,2) UNSIGNED NOT NULL,
    create_date      TIMESTAMP NOT NULL,
    last_update_date TIMESTAMP NOT NULL,
    duration         TINYINT UNSIGNED NOT NULL
);

create table tags_certificates
(
    gift_certificate_id BIGINT UNSIGNED NOT NULL,
    tag_id              BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificates (id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags (ID) ON DELETE CASCADE
);