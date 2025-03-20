import java.time.*;
import java.time.format.*;
import java.util.*;

import java.sql.*;

public class Appointment {

    Scanner sc;

    String appointment_id, patient_id, doctor_id, date, time, status;
    long random;

    Appointment() {
        sc = new Scanner(System.in);
    }

    // ================================================================================================================================
    // validate date
    public String getValidDate() {
        while (true) {
            System.out.print("Enter Date (DD-MM-YYYY): ");
            String inputDate = sc.nextLine();

            if (!inputDate.matches("\\d{2}-\\d{2}-\\d{4}")) {
                System.out.println("Invalid Date Format. Please enter in DD-MM-YYYY format.\n");
                continue;
            }

            try {
                // Convert DD-MM-YYYY to LocalDate
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate parsedDate = LocalDate.parse(inputDate, inputFormatter);

                // Ensure year is 2021 or later
                if (parsedDate.getYear() >= 2021) {
                    // Convert to MySQL format YYYY-MM-DD
                    DateTimeFormatter mysqlFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    return parsedDate.format(mysqlFormatter);
                } else {
                    System.out.println("Year must be 2021 or later.\n");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date! Please enter a correct date.\n");
            }
        }
    }

    // ================================================================================================================================
    // validate time
    public String getValidTime() {
        while (true) {
            System.out.print("Enter Time (HH:MM): ");
            String inputTime = sc.nextLine();

            if (inputTime.matches("\\d{2}:\\d{2}")) {
                String[] parts = inputTime.split(":");
                int hours = Integer.parseInt(parts[0]);
                int minutes = Integer.parseInt(parts[1]);

                if (hours >= 9 && hours < 18 && minutes >= 0 && minutes < 60) {
                    return inputTime + ":00"; // Convert to HH:MM:SS for MySQL
                } else {
                    System.out.println("Time must be between 09:00 and 17:59.\n");
                }
            } else {
                System.out.println("Invalid Time Format. Please enter in HH:MM format.\n");
            }
        }
    }

    // ================================================================================================================================
    // book appointment
    public void bookAppointment() {
        System.out.print("\nEnter Patient ID: ");
        patient_id = sc.nextLine();

        System.out.print("Enter Doctor ID: ");
        doctor_id = sc.nextLine();

        date = getValidDate();
        time = getValidTime();

        while (true) {
            Random random = new Random();
            appointment_id = "" + Math.abs((random.nextLong() % 900000L) + 100000L);

            Conn c = new Conn();
            String appointment_id_query = "select * from appointment where appointment_id = '" + appointment_id + "'";

            try {
                ResultSet rs = c.s.executeQuery(appointment_id_query);
                if (!rs.next()) {
                    rs.close();
                    c.s.close();
                    break;
                } else {
                    System.out.println("Your Appointment Id is: " + appointment_id);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        status = "Pending";
        String payment_status = "Pending";
        double payment_amount, pending_amount;

        System.out.print("Enter Appointment Fee Amount: ");
        payment_amount = sc.nextDouble();
        sc.nextLine(); // Consume newline
        pending_amount = payment_amount;

        try {

            Conn c = new Conn();

            String checkPatintQuery = "select * from patient where patient_id = '" + patient_id + "'";
            ResultSet rs_p = c.s.executeQuery(checkPatintQuery);
            if (!rs_p.next()) {
                System.out.println("Error: Patient Id " + patient_id + "does not exist !");
                return;
            }

            String checkDoctorQuery = "select * from doctor where doctor_id = '" + doctor_id + "'";
            ResultSet rs_d = c.s.executeQuery(checkDoctorQuery);
            if (!rs_d.next()) {
                System.out.println("Error: Doctor Id " + doctor_id + "does not exist !");
                return;
            }

            String query = "insert into appointment values('" + appointment_id + "', '" + patient_id + "', '"
                    + doctor_id + "', '" + date + "', '" + time + "', '" + status + "' , '" + payment_status + "', '"
                    + payment_amount + "', '" + pending_amount + "')";
            c.s.executeUpdate(query);
            System.out.println(
                    "Your appointment-id " + appointment_id + " has been booked on " + date + " at " + time);
            System.out.println("Payment Status: " + payment_status + " | Total Amount: $" + payment_amount
                    + " | Pending Amount: $" + pending_amount + "\n");
            c.s.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================================================================================================================================
    // cancel appointment
    public void cancelAppointment() {

        System.out.print("Enter Appointment Id: ");
        String appointment_id = sc.nextLine();

        try {
            Conn c = new Conn();
            String query = "delete from appointment where appointment_id = '" + appointment_id + "'";
            int rowAffected = c.s.executeUpdate(query);

            if (rowAffected > 0) {
                System.out.println("\nAppointment Canceled Successfully ID: " + appointment_id + "\n");
            } else {
                System.out.println("\n Error: Appointment Not Found !\n");
            }
            c.s.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // ================================================================================================================================
    // update appointment
    public void updateAppointment() {

        System.out.print("\nEnter Appointment Id: ");
        String appointment_id = sc.nextLine();
        System.out.println("");

        System.out.println("1. Update Status");
        System.out.println("2. Update Details");
        System.out.print("Enter Choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        Conn c = new Conn();

        switch (choice) {
            case 1:
                String Statusquery = "update appointment set status = 'Complate' where appointment_id = '"
                        + appointment_id
                        + "'";
                try {
                    int rowAffected = c.s.executeUpdate(Statusquery);

                    if (rowAffected > 0) {
                        System.out.println("\n" + appointment_id + " Appointment Updated Successfully!\n");
                    } else {
                        System.out.println("\nAppointment Not Found!\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case 2:

                date = getValidDate();
                time = getValidTime();

                String Datailsquery = "update appointment set date = '" + date + "', time = '" + time
                        + "' where appointment_id = '" + appointment_id + "'";
                try {
                    int rowAffected = c.s.executeUpdate(Datailsquery);

                    if (rowAffected > 0) {
                        System.out.println("\n" + appointment_id + " Appointment Updated Successfully!\n");
                    } else {
                        System.out.println("\nAppointment Not Found!\n");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            default:
                System.out.println("Enter valid choice...");
                break;
        }
    }

    // ================================================================================================================================
    // display appointment
    public void displayAppointment() {
        try {
            Conn c = new Conn();
            String query = "select * from appointment";
            ResultSet rs = c.s.executeQuery(query);

            System.out.println(
                    "==================================================MediCare - Appointment================================================");
            System.out.printf("%-20s %-15s %-15s %-15s %-15s %-15s\n", "Appointment ID", "Patient ID", "Doctor ID",
                    "Date", "Time",
                    "Satus");
            System.out.println(
                    "========================================================================================================================");

            boolean hasAppointment = false;

            while (rs.next()) {
                hasAppointment = true;
                String appointment_id = rs.getString("appointment_id");
                String patient_id = rs.getString("patient_id");
                int doctor_id = rs.getInt("doctor_id");
                String date = rs.getString("date");
                String time = rs.getString("time");
                String status = rs.getString("status");

                System.out.printf("%-20s %-15s %-15d %-15s %-15s %-15s\n", appointment_id, patient_id, doctor_id, date,
                        time,
                        status);
            }
            System.out.println(
                    "========================================================================================================================");
            System.out.println("\n");

            if (!hasAppointment) {
                System.out.println("\nNo appointment found in the database.\n");
            }

            rs.close();
            c.s.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}