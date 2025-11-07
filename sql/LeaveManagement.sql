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
