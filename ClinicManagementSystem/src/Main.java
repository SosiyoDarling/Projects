import java.util.*;

public class Main {

        public static void main(String[] args) {

                

                Scanner sc = new Scanner(System.in);
                int choice, sub_choice;

                while (true) {

                        System.out.println("=========Clinic Management System===========");
                        System.out.println("===================Menu=====================");
                        System.out.println("1. Manage Patient");
                        System.out.println("2. Manage Doctor");
                        System.out.println("3. Manage Appointment");
                        System.out.println("4. Payment");
                        System.out.println("5. Exit");
                        System.out.print("Enter your choise: ");
                        choice = sc.nextInt();
                        System.out.println("");

                        switch (choice) {
                                case 1:

                                        System.out.println("=========Clinic Management System===========");
                                        System.out.println("===============Manage Patient===============");
                                        System.out.println("1. Add Patient");
                                        System.out.println("2. Remove Patient");
                                        System.out.println("3. Update Patient");
                                        System.out.println("4. View All Patient");
                                        System.out.println("5. Go Back to Main Menu");
                                        System.out.print("Enter Your Choice: ");
                                        sub_choice = sc.nextInt();

                                        switch (sub_choice) {
                                                case 1:
                                                        new Patient().addPatient();
                                                        break;
                                                case 2:
                                                        new Patient().removePatient();
                                                        break;
                                                case 3:
                                                        new Patient().updatePatient();
                                                        break;
                                                case 4:
                                                        new Patient().displayPatient();
                                                        break;
                                                case 5:
                                                        System.out.println("Returning to Main Menu...");
                                                        System.out.println("");
                                                        break;
                                                default:
                                                        continue;
                                        }
                                        if (sub_choice == 5)
                                                break;
                                        break;

                                case 2:

                                        System.out.println("=========Clinic Management System===========");
                                        System.out.println("===============Manage Doctor================");
                                        System.out.println("1. Add Doctor");
                                        System.out.println("2. Remove Doctor");
                                        System.out.println("3. Update Doctor");
                                        System.out.println("4. View All Doctor");
                                        System.out.println("5. Go Back to Main Menu");
                                        System.out.print("Enter Your Choice: ");
                                        sub_choice = sc.nextInt();

                                        switch (sub_choice) {
                                                case 1:
                                                        new Doctor().addDoctor();
                                                        break;
                                                case 2:
                                                        new Doctor().removeDoctor();
                                                        break;
                                                case 3:
                                                        new Doctor().updateDoctor();
                                                        break;
                                                case 4:
                                                        new Doctor().displayDoctor();
                                                        break;
                                                case 5:
                                                        System.out.println("Returning to Main Menu...");
                                                        System.out.println("");
                                                        break;
                                                default:
                                                        continue;
                                        }
                                        if (sub_choice == 5)
                                                break;
                                        break;

                                case 3:

                                        System.out.println("=========Clinic Management System===========");
                                        System.out.println("============ Manage Appointment=============");
                                        System.out.println("1. New Appointment");
                                        System.out.println("2. Cancel Appointment");
                                        System.out.println("3. Update Appointment");
                                        System.out.println("4. View All Appointment");
                                        System.out.println("5. Go Back to Main Menu");
                                        System.out.print("Enter Your Choice: ");
                                        sub_choice = sc.nextInt();

                                        switch (sub_choice) {
                                                case 1:
                                                        new Appointment().bookAppointment();
                                                        break;
                                                case 2:
                                                        new Appointment().cancelAppointment();
                                                        break;
                                                case 3:
                                                        new Appointment().updateAppointment();
                                                        break;
                                                case 4:
                                                        new Appointment().displayAppointment();
                                                        break;
                                                case 5:
                                                        System.out.println("Returning to Main Menu...");
                                                        System.out.println("");
                                                        break;
                                                default:
                                                        System.out.println("Invalid Choice! Try again.");
                                                        continue;
                                        }
                                        if (sub_choice == 5)
                                                break;
                                        break;

                                case 4:

                                        System.out.println("=========Clinic Management System===========");
                                        System.out.println("==============Payment Section===============");
                                        new Payment().patientPayment();
                                        break;

                                case 5:
                                        System.out.println("Exiting....Have a Good Day");
                                        System.exit(0);

                                default:
                                        System.out.println("Ente Valid choice...");
                                        break;
                        }
                }
        }
}