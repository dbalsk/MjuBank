## 데이터베이스 설계 프로젝트

프로젝트 : **명지은행** (지점 및 고객 업무 관리 데이터베이스 시스템)

## ERD
**논리 다이어그램**   
<img height="400" alt="image" src="https://github.com/user-attachments/assets/a26d9beb-921d-4c83-b5b0-9574a90d7e34" /> <br>
**물리 다이어그램**    
<img height="400" alt="image" src="https://github.com/user-attachments/assets/a748aa49-fbe5-40ba-b592-57085a8b31ac" />

## 릴레이션 스키마
<img width="487" height="254" alt="Relation Schema" src="https://github.com/user-attachments/assets/861608fc-b20c-4cd2-8776-15554ec08354" />

## 사용자 인터페이스
<img width="361" height="352" alt="image" src="https://github.com/user-attachments/assets/754792b7-a28a-49e3-8e2d-849556fbde48" />
<img width="791" height="264" alt="image" src="https://github.com/user-attachments/assets/0a59eb1f-b920-47de-859d-9b1ba44819c5" />


## 기능 명세
<img height="700" alt="image" src="https://github.com/user-attachments/assets/aac485b2-1a3b-4686-8a8b-76fd34fb782d" />


## 테이블 생성 SQL
```
DROP TABLE IF EXISTS `card`;
DROP TABLE IF EXISTS `transaction`;
DROP TABLE IF EXISTS `account`;
DROP TABLE IF EXISTS `employee`;
DROP TABLE IF EXISTS `branch`;
DROP TABLE IF EXISTS `customer`;

-- 1. BRANCH (지점)
CREATE TABLE `branch` (
	    `branch_id`       BIGINT       NOT NULL AUTO_INCREMENT COMMENT '지점ID',
    `branch_name`     VARCHAR(50)  NOT NULL COMMENT '지점명',
    `branch_address`  VARCHAR(100) NULL     COMMENT '지점주소',
    `branch_phone`    VARCHAR(20)  NULL     COMMENT '지점전화번호',
    `branch_manager`  VARCHAR(50)  NULL     COMMENT '지점장명',
    PRIMARY KEY (`branch_id`)
);

-- 2. CUSTOMER (고객) 
CREATE TABLE `customer` (
    `customer_id`      BIGINT       NOT NULL AUTO_INCREMENT COMMENT '고객ID',
    `customer_name`    VARCHAR(50)  NOT NULL COMMENT '고객명',
    `customer_address` VARCHAR(100) NULL     COMMENT '고객주소',
    `customer_phone`   VARCHAR(20)  NULL     COMMENT '전화번호',
    `customer_job`     VARCHAR(50)  NULL     COMMENT '직업',
    `customer_email`   VARCHAR(100) NULL     COMMENT '이메일',
    `customer_birth`   DATE         NULL     COMMENT '생년월일',
    PRIMARY KEY (`customer_id`)
);

-- 3. EMPLOYEE (직원)
CREATE TABLE `employee` (
    `employee_id`        BIGINT       NOT NULL AUTO_INCREMENT COMMENT '직원ID',
    `employee_name`      VARCHAR(50)  NOT NULL COMMENT '직원명',
    `employee_phone`     VARCHAR(20)  NULL     COMMENT '직원전화번호',
    `employee_hire_date` DATE         NOT NULL COMMENT '입사일자', 
    `employee_salary`    BIGINT       NULL     COMMENT '급여',
    `branch_id`          BIGINT       NULL     COMMENT '소속지점ID',
    PRIMARY KEY (`employee_id`),
    FOREIGN KEY (`branch_id`) REFERENCES `branch` (`branch_id`)
);

-- 4. ACCOUNT (계좌) 
CREATE TABLE `account` (
    `account_id`        BIGINT       NOT NULL AUTO_INCREMENT COMMENT '계좌ID(PK)',
    `account_type`      VARCHAR(20)  NOT NULL COMMENT '예금종류',
    `account_balance`   BIGINT       NOT NULL DEFAULT 0 COMMENT '예금잔고',
    `card_applied`      CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '카드신청여부',
    `account_open_date` DATE         NOT NULL COMMENT '개설일자',
    `depositor_name`    VARCHAR(50)  NULL     COMMENT '예금자명',
    `depositor_phone`   VARCHAR(20)  NULL     COMMENT '예금자전화번호',
    `depositor_email`   VARCHAR(100) NULL     COMMENT '예금자이메일',
    `customer_id`       BIGINT       NOT NULL COMMENT '고객ID',
    `branch_id`         BIGINT       NULL     COMMENT '개설지점ID',
    `employee_id`       BIGINT       NULL     COMMENT '담당직원ID',
    PRIMARY KEY (`account_id`),
    FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
    FOREIGN KEY (`branch_id`) REFERENCES `branch` (`branch_id`),
    FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`)
);

-- 5. TRANSACTION (거래내역)
CREATE TABLE `transaction` (
    `account_id`         BIGINT       NOT NULL COMMENT '계좌ID(PK,FK)',
    `transaction_date`   DATETIME     NOT NULL COMMENT '거래일시(PK)',
    `transaction_no`     INT          NOT NULL COMMENT '거래순번(PK)',
    `transaction_type`   VARCHAR(10)  NOT NULL COMMENT '입/출금 구분',
    `transaction_desc`   VARCHAR(100) NULL     COMMENT '거래내용',
    `transaction_amount` BIGINT       NOT NULL DEFAULT 0 COMMENT '거래금액',
    `balance_after`      BIGINT       NOT NULL DEFAULT 0 COMMENT '거래후잔액',
    PRIMARY KEY (`account_id`, `transaction_date`, `transaction_no`),
    FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`)
);

-- 6. CARD (카드)
CREATE TABLE `card` (
    `card_id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '카드ID(PK)',
    `card_apply_date`  DATE         NOT NULL COMMENT '신청일자',
    `card_limit`       BIGINT       NULL     COMMENT '한도금액',
    `card_payment_day` VARCHAR(10)  NULL     COMMENT '결제일',
    `card_type`        VARCHAR(20)  NULL     COMMENT '카드종류',
    `customer_id`      BIGINT       NOT NULL COMMENT '고객ID',
    `account_id`       BIGINT       NOT NULL COMMENT '결제계좌ID',
    PRIMARY KEY (`card_id`),
    FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
    FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`)
);
