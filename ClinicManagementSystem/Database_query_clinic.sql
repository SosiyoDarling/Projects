create database clinicmanagementsystem;

use clinicmanagementsystem;

create table patient(patient_id varchar(15) not null primary key, name varchar(20), age varchar(15), gender varchar(15), phone varchar(15), address varchar(100), problem varchar(100));

desc patient;

select * from patient;

create table doctor(doctor_id varchar(15) not null primary key, name varchar(20), age varchar(15), gender varchar(15), phone varchar(15), availability varchar(15), specialist varchar(50));

desc doctor;

select * from doctor;

create table appointment (appointment_id varchar(20) not null primary key, patient_id varchar(15), doctor_id varchar(15), date date, time time, status varchar(15), payment_status varchar(10) default 'pending',
    payment_amount decimal(10,2) default 0.00, pending_amount decimal(10,2) default 0.00, foreign key (patient_id) references patient(patient_id), foreign key (doctor_id) references doctor(doctor_id));

desc appointment;

select * from appointment;
