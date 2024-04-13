CREATE TABLE PatientTable (	
	FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    DOB VARCHAR(50) NOT NULL,
    PhoneNumber VARCHAR(50) NOT NULL,
    Email VARCHAR(50) NOT NULL,
    Username VARCHAR(50) NOT NULL,
    Password VARCHAR(50) NOT NULL,
    InsuranceProvider VARCHAR(50) NOT NULL,
    PreferredPharmacy VARCHAR(50) NOT NULL,
    PatientInfoFile BOOLEAN NOT NULL,
    PharmacyFile BOOLEAN NOT NULL,
    PhysicalFile BOOLEAN NOT NULL,
    Questionnaires BOOLEAN NOT NULL,
    Immunizations BOOLEAN NOT NULL,
    Vitals BOOLEAN NOT NULL,
    Messages BOOLEAN NOT NULL,
    Summary BOOLEAN NOT NULL,
    AssociateDoctor VARCHAR(50) NOT NULL,
    AssociateNurse VARCHAR(50) NOT NULL,
    PRIMARY KEY (Username)
);

CREATE TABLE NurseTable (	
	FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    Username VARCHAR(50) NOT NULL,
    Password VARCHAR(50) NOT NULL,
    Messages BOOLEAN NOT NULL,
    PRIMARY KEY (Username)
);

CREATE TABLE DoctorTable (	
	FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    Username VARCHAR(50) NOT NULL,
    Password VARCHAR(50) NOT NULL,
    Messages BOOLEAN NOT NULL,
    PRIMARY KEY (Username)
);

INSERT INTO NurseTable (FirstName, LastName, Username, Password, Messages) VALUES (
'Aayush', 'Bharti', 'nurse', 'nurse', '0');

INSERT INTO DoctorTable (FirstName, LastName, Username, Password, Messages) VALUES (
'Zuhayr', 'Shams', 'doctor', 'doctor', '0');

SELECT * FROM PatientTable;
SELECT * FROM NurseTable;
SELECT * FROM DoctorTable;