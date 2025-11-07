USE LeaveManagement
CREATE TABLE Department (
    id INT IDENTITY PRIMARY KEY,
    name NVARCHAR(100) NOT NULL
);

CREATE TABLE [Role] (
    id INT IDENTITY PRIMARY KEY,
    name NVARCHAR(100) NOT NULL
);

CREATE TABLE [User] (
    id INT IDENTITY PRIMARY KEY,
    username NVARCHAR(50) NOT NULL UNIQUE,
    password NVARCHAR(200) NOT NULL,
    fullname NVARCHAR(200),
    department_id INT NULL,
    manager_id INT NULL,
    FOREIGN KEY (department_id) REFERENCES Department(id),
    FOREIGN KEY (manager_id) REFERENCES [User](id)
);

CREATE TABLE Feature (
    id INT IDENTITY PRIMARY KEY,
    name NVARCHAR(100),
    url NVARCHAR(200)
);

CREATE TABLE Role_Feature (
    role_id INT,
    feature_id INT,
    PRIMARY KEY(role_id, feature_id),
    FOREIGN KEY (role_id) REFERENCES [Role](id),
    FOREIGN KEY (feature_id) REFERENCES Feature(id)
);

CREATE TABLE User_Role (
    user_id INT,
    role_id INT,
    PRIMARY KEY(user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES [User](id),
    FOREIGN KEY (role_id) REFERENCES [Role](id)
);

CREATE TABLE LeaveRequest (
    id INT IDENTITY PRIMARY KEY,
    title NVARCHAR(200),
    from_date DATE,
    to_date DATE,
    reason NVARCHAR(1000),
    status NVARCHAR(50) DEFAULT 'InProgress',
    created_by INT,
    processed_by INT NULL,
    created_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (created_by) REFERENCES [User](id),
    FOREIGN KEY (processed_by) REFERENCES [User](id)
);
GO
--======================================== SAMPLE DATA  ========================================

-- PHÒNG BAN
INSERT INTO Department (name) VALUES ('IT'), ('HR'), ('Sale');
GO

-- VAI TRÒ (ROLES)
INSERT INTO [Role] (name) VALUES ('Employee'), ('Manager'), ('DepartmentHead'), ('Admin');
GO
-- IDs: 1=Employee, 2=Manager, 3=DepartmentHead, 4=Admin

-- CHỨC NĂNG (FEATURES)
INSERT INTO Feature (name, url) VALUES
('createRequest', 'action=createRequest'),
('listRequest', 'action=listRequest'),
('approveRequest', 'action=approveRequest'),
('agenda', 'action=agenda'),
('home', 'action=home');
GO

-- PHÂN QUYỀN CHO CÁC ROLE
INSERT INTO Role_Feature (role_id, feature_id) VALUES
(1,1),(1,2),                                  -- Employee: create, list
(2,1),(2,2),(2,3),(2,5),                      -- Manager: create, list, approve, home
(3,1),(3,2),(3,3),(3,4),(3,5),                -- DepartmentHead: full access + agenda
(4,1),(4,2),(4,3),(4,4),(4,5);                -- Admin: all
GO

-- ==========================================================
-- NGƯỜI DÙNG (USERS)
-- ==========================================================
-- Cấp cao nhất: Division Leader (Mr A)
INSERT INTO [User] (username, password, fullname, department_id, manager_id)
VALUES ('mrA','passA','Mr A (Division Leader)',1,NULL); -- ID=1

-- Team Leaders (Mr B, Mr C) - báo cáo cho Mr A
INSERT INTO [User] (username, password, fullname, department_id, manager_id)
VALUES ('mrB','passB','Mr B (Team Leader)',1,1),
       ('mrC','passC','Mr C (Team Leader)',1,1); -- ID=2,3

-- Employees dưới quyền Mr B
INSERT INTO [User] (username, password, fullname, department_id, manager_id)
VALUES ('mrD','passD','Mr D (Employee)',1,2),
       ('mrE','passE','Mr E (Employee)',1,2),
       ('mrF','passF','Mr F (Employee)',1,2);

-- Employees dưới quyền Mr C
INSERT INTO [User] (username, password, fullname, department_id, manager_id)
VALUES ('mrG','passG','Mr G (Employee)',1,3),
       ('mrH','passH','Mr H (Employee)',1,3);

-- Các user khác
INSERT INTO [User] (username, password, fullname, department_id, manager_id)
VALUES ('adminUser','admin','Admin Toàn Năng',NULL,NULL),    -- ID=9
       ('hrManager','passHR','Ms. Chi (HR Manager)',2,NULL), -- ID=10
       ('saleEmp','passSale','Mr. Long (Sales)',3,NULL),     -- ID=11
       ('itIntern','passIT','Mr. Bach (IT Intern)',1,2);     -- ID=12
GO

-- ==========================================================
-- PHÂN QUYỀN (USER_ROLES)
-- ==========================================================
INSERT INTO User_Role (user_id, role_id) VALUES
(1, 3), -- Mr A là DepartmentHead
(2, 2), -- Mr B là Manager
(3, 2), -- Mr C là Manager
(4, 1), (5, 1), (6, 1), (7, 1), (8, 1), -- nhân viên
(9, 4), -- adminUser là Admin
(10, 2), -- HR Manager
(11, 1), -- sale employee
(12, 1); -- IT intern
GO

--=============================== TEST DATA ===============================

INSERT INTO LeaveRequest (title, from_date, to_date, reason, status, created_by, processed_by)
VALUES
('Xin nghỉ cưới', '2025-01-01', '2025-01-03', 'Cưới vợ', 'Approved', 5, 2),
('Nghỉ phép đi chơi', '2025-01-02', '2025-01-04', 'Du lịch Đà Lạt', 'InProgress', 4, NULL),
('Nghỉ chăm con', '2025-01-03', '2025-01-05', 'Con ốm', 'Rejected', 7, 3);
GO
