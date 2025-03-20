import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class Payment {

    String patient_id;

    public void patientPayment() {

        System.out.println("1. View All Pending Payment");
        System.out.println("2. Mark Payment as Paid");
        System.out.print("Enter Your Choice: ");
        Scanner sc = new Scanner(System.in);
        int sub_choice = sc.nextInt();
        Conn c = new Conn();

        switch (sub_choice) {
            case 1:
                String query = "select * from appointment where payment_status = 'Pending'";
                try {
                    ResultSet rs = c.s.executeQuery(query);

                    System.out.println(
                            "==================================================MediCare - Payment====================================================");
                    System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s\n",
                            "Appointment ID", "Patient ID", "Appointment Status",
                            "Payment Amount", "Payment Pending", "Payment Status");
                    System.out.println(
                            "========================================================================================================================");

                    boolean hasAppointment = false;

                    while (rs.next()) {
                        hasAppointment = true;
                        String appointment_id = rs.getString("appointment_id");
                        String patient_id = rs.getString("patient_id");
                        String status = rs.getString("status");
                        BigDecimal payment_amount = rs.getBigDecimal("payment_amount");
                        BigDecimal payment_pending = rs.getBigDecimal("pending_amount");
                        String payment_status = rs.getString("payment_status");

                        System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s\n",
                                appointment_id, patient_id, status,
                                payment_amount.toString(), payment_pending.toString(), payment_status);
                    }
                    System.out.println(
                            "========================================================================================================================");
                    System.out.println("\n");

                    if (!hasAppointment) {
                        System.out.println("\nNo Appointments found with Pending Payments.\n");
                    }

                    rs.close();
                    c.s.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case 2:

                System.out.println("Enter Appointment ID: ");
                String appointment_id = sc.next();

                String query2 = "update appointment set payment_status = 'Paid' , pending_amount = 0.00 where appointment_id = '"
                        + appointment_id + "'";
                try {

                    int rowAffected = c.s.executeUpdate(query2);
                    if (rowAffected > 0) {
                        System.out.println("Payment Successfully of Appointment Id: " + appointment_id);
                    } else {
                        System.out.println("Appointment Not Found!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            default:
                System.out.println("Invalid Choice");
                break;
        }
    }
}