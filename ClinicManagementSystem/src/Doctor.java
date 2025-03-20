import java.sql.*;
import java.util.*;

public class Doctor {

    String name, specialist, gender, availability, DoctorId, phone;
    int age;

    Scanner sc;

    public Doctor() {
        sc = new Scanner(System.in);
    }

    // ===========================================================================================================================
    // add Doctor
    public void addDoctor() {

        System.out.print("\nEnter Doctor Name: ");
        name = sc.nextLine();

        System.out.print("Enter Doctor Age: ");
        age = sc.nextInt();
        sc.nextLine(); // Consume newline left by nextInt()

        while (true) {
            System.out.println("1. Male 2. Female 3. Others");
            System.out.print("Choose Doctor Gender: ");
            gender = sc.nextLine();

            if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("1") || gender.equalsIgnoreCase("male")) {
                gender = "Male";
                break;
            } else if (gender.equalsIgnoreCase("Female") || gender.equalsIgnoreCase("2")
                    || gender.equalsIgnoreCase("female")) {
                gender = "Female"; // Fixed typo
                break;
            } else if (gender.equalsIgnoreCase("Others") || gender.equalsIgnoreCase("3")
                    || gender.equalsIgnoreCase("others")) {
                gender = "Others";
                break;
            } else {
                System.out.println("Invalid Gender Choice... Enter Valid Choice");
            }
        }

        System.out.print("Phone No: ");
        phone = sc.nextLine();

        System.out.print("Specialist: ");
        specialist = sc.nextLine();

        while (true) {
            System.out.println("1. Morning 2. Evening 3. Night");
            System.out.print("Availability: ");
            availability = sc.nextLine();

            if (availability.equalsIgnoreCase("Morning") || availability.equalsIgnoreCase("1")
                    || availability.equalsIgnoreCase("morning")) {
                availability = "Morning";
                break;
            } else if (availability.equalsIgnoreCase("Evening") || availability.equalsIgnoreCase("2")
                    || availability.equalsIgnoreCase("vening")) {
                availability = "Evening"; // Fixed typo
                break;
            } else if (availability.equalsIgnoreCase("Night") || availability.equalsIgnoreCase("3")
                    || availability.equalsIgnoreCase("night")) {
                availability = "Night";
                break;
            } else {
                System.out.println("Invalid Choice... Enter Valid Choice");
            }
        }

        Random random = new Random();
        DoctorId = "" + Math.abs((random.nextLong() % 900000L) + 100000L);

        try {
            Conn c = new Conn();
            String query = "insert into doctor values('" + DoctorId + "', '" + name + "','" + age + "','" + gender
                    + "','" + phone + "','" + availability + "','" + specialist + "')";
            c.s.executeUpdate(query);
            c.s.close();
            System.out.println("Doctor Added Successfully! ID: " + DoctorId + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===========================================================================================================================
    // remove Doctor

    public void removeDoctor() {

        System.out.print("\nEnter Doctor Id: ");
        DoctorId = sc.nextLine();

        try {
            Conn c = new Conn();
            String query = "delete from doctor where doctor_id = '" + DoctorId + "';";
            c.s.executeUpdate(query);
            int rowAffected = c.s.executeUpdate(query);

            if (rowAffected > 0) {
                System.out.println("\nDoctor Removed Successfully ID: " + DoctorId + "\n");
            } else {
                System.out.println("\n Error: Doctor Not Found !\n");
            }
            c.s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===========================================================================================================================
    // update Doctor

    public void updateDoctor() {

        System.out.print("\nEnter Doctor ID to Update: ");
        DoctorId = sc.nextLine();

        System.out.print("Enter New Doctor Name: ");
        name = sc.nextLine();

        while (true) {
            System.out.println("1. Morning 2. Evening 3. Night");
            System.out.print("Availability: ");
            availability = sc.nextLine();

            if (availability.equalsIgnoreCase("Morning") || availability.equalsIgnoreCase("1")
                    || availability.equalsIgnoreCase("morning")) {
                availability = "Morning";
                break;
            } else if (availability.equalsIgnoreCase("Evening") || availability.equalsIgnoreCase("2")
                    || availability.equalsIgnoreCase("vening")) {
                availability = "Evening";
                break;
            } else if (availability.equalsIgnoreCase("Night") || availability.equalsIgnoreCase("3")
                    || availability.equalsIgnoreCase("night")) {
                availability = "Night";
                break;
            } else {
                System.out.println("Invalid Choice... Enter Valid Choice");
            }
        }

        System.out.print("Enter New Doctor Phone: ");
        String phone = sc.nextLine();

        try {
            Conn c = new Conn();
            String Updatequery = "update doctor set name = '" + name + "', phone = '" + phone + "', availability = '"
                    + availability
                    + "' where doctor_id = '" + DoctorId + "'";
            int rowAffected = c.s.executeUpdate(Updatequery);

            if (rowAffected > 0) {
                System.out.println("\n" + DoctorId + " Doctor Updated Successfully!\n");
            } else {
                System.out.println("\nDoctor Not Found!\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===========================================================================================================================
    // display Doctor

    public void displayDoctor() {

        try {
            Conn c = new Conn();
            String query = "select * from doctor";
            ResultSet rs = c.s.executeQuery(query);

            System.out.println(
                    "==================================================MediCare - Doctor=====================================================");
            System.out.printf("%-10s %-20s %-5s %-15s %-15s %-15s\n", "Doctor ID", "Name", "Age", "Phone", "specialist",
                    "availability");
            System.out.println(
                    "========================================================================================================================");

            boolean hasDoctors = false;

            while (rs.next()) {
                hasDoctors = true;
                String DoctorId = rs.getString("doctor_id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String phone = rs.getString("phone");
                String availability = rs.getString("availability");
                String specialist = rs.getString("specialist");

                System.out.printf("%-10s %-20s %-5d %-15s %-15s %-15s\n", DoctorId, name, age, phone, specialist,
                        availability);
            }
            System.out.println(
                    "========================================================================================================================");
            System.out.println("\n");

            if (!hasDoctors) {
                System.out.println("\nNo Doctors found in the database.\n");
            }

            rs.close();
            c.s.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}