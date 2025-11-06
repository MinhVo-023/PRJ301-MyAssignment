-- Create DB (optional)
-- CREATE DATABASE LeaveManagement;
USE LeaveManagement;

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
    password NVARCHAR(200) NOT NULL, -- store hashed in prod; plain for demo
    fullname NVARCHAR(200),
    department_id INT,
    manager_id INT NULL,
    FOREIGN KEY (department_id) REFERENCES Department(id),
    FOREIGN KEY (manager_id) REFERENCES [User](id)
);

CREATE TABLE Feature (
    id INT IDENTITY PRIMARY KEY,
    name NVARCHAR(100),
    url NVARCHAR(200) -- uri or action mapping to check
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

-- Sample data
INSERT INTO Department (name) VALUES ('IT'), ('HR'), ('Sale');

INSERT INTO [Role] (name) VALUES ('Employee'), ('Manager'), ('DepartmentHead'), ('Admin');

INSERT INTO Feature (name, url) VALUES
('createRequest', 'action=createRequest'),
('listRequest', 'action=listRequest'),
('approveRequest', 'action=approveRequest'),
('agenda', 'action=agenda'),
('home', 'action=home');

-- assign features to roles
INSERT INTO Role_Feature (role_id, feature_id) VALUES
(1,1),(1,2), -- Employee: create, list
(2,2),(2,3),(2,1),(2,5), -- Manager
(3,1),(3,2),(3,3),(3,4),(3,5); -- DepartmentHead

-- sample users
INSERT INTO [User] (username, password, fullname, department_id, manager_id) VALUES
('user1','pass1','Mr A',1,NULL),
('mgr1','pass2','Mr B',1, NULL),
('head1','pass3','Mr C',1,NULL);

-- roles for users
INSERT INTO User_Role (user_id, role_id) VALUES (1,1),(2,2),(3,3);
