import java.sql.*;
import java.util.*;

public class Patient {

    String name, address, gender, problem, PatientId, phone;
    int age;

    Scanner sc;

    Patient() {
        sc = new Scanner(System.in);
    }

    // ===========================================================================================================================
    // add patient
    public void addPatient() {

        System.out.print("\nEnter Patient Name: ");
        name = sc.nextLine();

        System.out.print("Enter Patient Age: ");
        age = sc.nextInt();
        sc.nextLine(); // Consume newline left by nextInt()

        while (true) {
            System.out.println("1. Male 2. Female 3. Others");
            System.out.print("Choose Patient Gender: ");
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

        System.out.print("Enter Patient Phone No: ");
        phone = sc.nextLine();

        System.out.print("Enter Patient Address: ");
        address = sc.nextLine();

        System.out.print("Enter Patient Problem: ");
        problem = sc.nextLine();

        Random random = new Random();
        PatientId = "" + Math.abs((random.nextLong() % 900000L) + 100000L);

        try {
            Conn c = new Conn();
            String query = "insert into patient values('" + PatientId + "', '" + name + "','" + age + "','" + gender
                    + "','" + phone + "','" + address + "','" + problem + "')";
            c.s.executeUpdate(query);
            c.s.close();
            System.out.println("Patient Added Successfully! ID: " + PatientId + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===========================================================================================================================
    // remove patient

    public void removePatient() {

        System.out.print("\nEnter Patient Id: ");
        PatientId = sc.nextLine();

        try {
            Conn c = new Conn();
            String query = "delete from patient where patient_id = '" + PatientId + "';";
            int rowAffected = c.s.executeUpdate(query);

            if (rowAffected > 0) {
                System.out.println("\nPatient Removed Successfully ID: " + PatientId + "\n");
            } else {
                System.out.println("\n Error: Patient Not Found !\n");
            }

            c.s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===========================================================================================================================
    // update patient

    public void updatePatient() {

        System.out.print("\nEnter Patient ID to Update: ");
        PatientId = sc.nextLine();

        System.out.print("Enter New Patient Name: ");
        name = sc.nextLine();

        System.out.print("Enter New Patient Address: ");
        address = sc.nextLine();

        System.out.print("Enter New Patient Phone: ");
        String phone = sc.nextLine();

        try {
            Conn c = new Conn();
            String query = "update patient set name = '" + name + "', phone = '" + phone + "', address = '" + address
                    + "' where patient_id = '" + PatientId + "'";
            int rowAffected = c.s.executeUpdate(query);

            if (rowAffected > 0) {
                System.out.println("\n" + PatientId + " Patient Updated Successfully!\n");
            } else {
                System.out.println("\nPatient Not Found!\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===========================================================================================================================
    // display patient

    public void displayPatient() {

        try {
            Conn c = new Conn();
            String query = "select * from patient";
            ResultSet rs = c.s.executeQuery(query);

            System.out.println(
                    "==================================================MediCare - Patient====================================================");
            System.out.println(
                    "========================================================================================================================");
            System.out.printf("%-10s %-20s %-5s %-15s %-25s %-20s\n", "Patient ID", "Name", "Age", "Phone", "Address",
                    "Problem");
            System.out.println(
                    "========================================================================================================================");

            boolean hasPatients = false;

            while (rs.next()) {
                hasPatients = true;
                String patientId = rs.getString("patient_id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String problem = rs.getString("problem");

                System.out.printf("%-10s %-20s %-5d %-15s %-25s %-20s\n", patientId, name, age, phone, address,
                        problem);
            }
            System.out.println(
                    "========================================================================================================================");
            System.out.println("\n");

            if (!hasPatients) {
                System.out.println("\nNo patients found in the database.\n");
            }

            rs.close();
            c.s.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}