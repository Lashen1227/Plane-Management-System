import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;


public class PlaneManagement {
    private static final int plane_rows = 4;  // Number of rows
    private static final int[] seats_per_row = {14, 12, 12, 14};  // Array to store the number of seats per row
    private static final int[][] seats = new int[plane_rows][];  // Array to store seat status (0- sold, 1- available)
    private static final int[][] price_per_seat = {
            {200, 200, 200, 200, 200, 150, 150, 150, 150, 180, 180, 180, 180, 180},
            {200, 200, 200, 200, 200, 150, 150, 150, 150, 180, 180, 180},
            {200, 200, 200, 200, 200, 150, 150, 150, 150, 180, 180, 180},
            {200, 200, 200, 200, 200, 150, 150, 150, 150, 180, 180, 180, 180, 180}
    };  // Prices for each seat

    private static Ticket[] tickets_sold = new Ticket[52];  // Array to store tickets sold
    private static int tickets_count = 0;  // Counter for the number of tickets sold
    private static int total_sales = 0;  // Total sales
    private static boolean first_time = true;  // Variable to check if it is the first time to display the seat prices table


    public static void main(String[] args) {
        System.out.println("\nWelcome to the Plane Management application");

        analyze_seats();
        Scanner scanner = new Scanner(System.in);
        int option;  // Variable to store the user's option

        do {
            user_menu();  // Display user menu
            System.out.print("Please select an option: ");
            try{
                option = scanner.nextInt();

                switch (option) {
                    case 1:
                        buy_seat(scanner);
                        break;
                    case 2:
                        cancel_seat(scanner);
                        break;
                    case 3:
                        find_first_available();
                        break;
                    case 4:
                        show_seating_plan();
                        break;
                    case 5:
                        print_tickets_info();
                        break;
                    case 6:
                        search_ticket(scanner);
                        break;
                    case 0:
                        System.out.println("Quitting the program. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            }catch (InputMismatchException e){
                System.out.println("Invalid input! Please enter a valid option");
                scanner.next();
                option = -1;  // Set option to -1 to display the menu again
            }catch (Exception e){
                System.out.println("An error occurred. Please try again.");
                scanner.next();
                option = -1;
            }
        } while (option != 0);
    }



    // Initialize the seats array with the number of seats per row
    private static void analyze_seats() {
        for (int x = 0; x < plane_rows; x++) {
            seats[x] = new int[seats_per_row[x]];  // Initialize the seats array with the number of seats per row

            // Initialize all seats as available(1) by default when the program starts running
            for (int y = 0; y < seats_per_row[x]; y++) {
                seats[x][y] = 1;
            }
        }
    }



    // Method to display main menu
    private static void user_menu() {
        String mainMenu = """
                ********************************************
                *               MENU OPTIONS               *
                ********************************************
                1) Buy a seat
                2) Cancel a seat
                3) Find first available seat
                4) Show seating plan
                5) Print tickets information and total sales
                6) Search ticket
                0) Quit
                ********************************************
                """;
        System.out.println(" ");
        System.out.println(mainMenu);
    }



    // Method to print the seat prices table for the user
    private static void seat_prices_table(){
        System.out.println("Seating plan and price: ");
        System.out.println("    1    2    3    4    5     6     7   8   9    10   11   12   13   14");
        for (int i = 0; i < plane_rows; i++) {
            char row = (char) ('A' + i);  // Convert the row number to a character
            System.out.print(row + ": ");  // Print row letters
            for (int j = 0; j < seats_per_row[i]; j++) {
                System.out.print("£"+ price_per_seat[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("\nTo buy a seat, please enter the row and seat number.");
    }



    /*
    Reference - Take character inputs:
     - Adopted from code at stackoverflow - https://stackoverflow.com/questions/22837423/how-to-take-character-input-in-java
    Reference - Validate data from user inputs using java RegEx:
     - Adopted from code at java2s - http://www.java2s.com/example/java/regular-expressions/input-and-validate-data-from-user-using-regular-expression.html
     - Adopted from code at w3schools - https://www.w3schools.com/java/java_regex.asp
    */
    /**
     * Method to buy a seat
     * @param scanner Scanner object to get user input
     */
    private static void buy_seat(Scanner scanner) {
        // Display the seat prices table for the user if it is the first time to buy a seat
        if (first_time) {
            seat_prices_table();
            first_time = false;  // Set false to not display the seat prices table again
        }

        boolean valid_seat_selection = false;  // Variable to store if the selection is valid

        // Loop until the user enters a valid seat
        while (!valid_seat_selection) {
            // Get user input for the row and seat number
            System.out.println("Enter the row (A, B, C, D): ");
            char row = scanner.next().toUpperCase().charAt(0);
            int rowNumber = row - 'A';  // Convert the row character to an integer

            // Check if the entered row is valid
            if (rowNumber < 0 || rowNumber >= plane_rows) {
                System.out.println("Invalid row selection... Please try again.");
                continue;   // Ask for row input again
            }

            System.out.println("Enter the seat number: ");
            int seatNumber = scanner.nextInt();
            int seatIndex = seatNumber - 1;  // Convert the seat number to an integer

            // Check if the seat is available or not in the seats array
            if (seatIndex < 0 || seatIndex >= seats_per_row[rowNumber]) {
                System.out.println("Invalid seat selection... Please try again.");
            } else if (seats[rowNumber][seatIndex] == 0) {
                System.out.println("Seat is already booked. Please select another seat.");
            } else {
                valid_seat_selection = true;  // Set to true to exit the loop
                scanner.nextLine();

                // Variables to store user information
                String name;
                String surname;
                String email;

                // Get user information
                while (true) {
                    System.out.println("Enter your first name: ");
                    name = scanner.nextLine();
                    // Validate the name
                    if (!Pattern.matches("^[A-Za-z]+$", name)) {
                        System.out.println("Invalid input! Please enter a valid name & Try Again");
                        continue;  // Skip the rest of the loop and restart
                    }
                    break;
                }

                while (true) {
                    System.out.println("Enter your surname: ");
                    surname = scanner.nextLine();
                    // Validate the surname
                    if (!Pattern.matches("^[A-Za-z]+$", surname)) {
                        System.out.println("Invalid input! Please enter a valid surname & Try Again");
                        continue;  // Skip the rest of the loop and restart
                    }
                    break;
                }

                while (true) {
                    System.out.println("Enter your email: ");
                    email = scanner.nextLine();
                    // Validate the email
                    if (!Pattern.matches("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", email)) {
                        System.out.println("Invalid input! Please enter a valid email & Try Again");
                        continue;  // Skip the rest of the loop and restart
                    }
                    break;
                }

                // Create Person object
                Person person = new Person(name, surname, email);
                // Create Ticket object
                Ticket ticket = new Ticket(row + "", seatNumber, price_per_seat[rowNumber][seatIndex], person);
                // Add ticket to ticketsSold array
                tickets_sold[tickets_count++] = ticket;
                // Update seat status and total sales
                seats[rowNumber][seatIndex] = 0; // Mark seat as sold
                total_sales += price_per_seat[rowNumber][seatIndex];
                System.out.println("Seat " + row + seatNumber + " purchased successfully.");
                // Save ticket information to file
                ticket.save();
                System.out.println("File ticket_" + row + "_" + seatNumber + ".txt printed.");
            }
        }
    }




    /**
     * Method to cancel a seat
     * @param scanner Scanner object to get user input
     */
    private static void cancel_seat(Scanner scanner) {
        boolean valid_seat_selection = false;  // Variable to store if the selection is valid

        // Loop until the user enters a valid seat to cancel
        while (!valid_seat_selection) {
            System.out.println("Enter the row (A, B, C, D): ");
            char row = scanner.next().toUpperCase().charAt(0);
            int rowNumber = row - 'A';  // Convert the row character to an integer

            // Check if the entered row is valid
            if (rowNumber < 0 || rowNumber >= plane_rows) {
                System.out.println("Invalid row selection.");
                continue;
            }

            System.out.println("Enter the seat number: ");
            int seatNumber = scanner.nextInt();

            int seatIndex = seatNumber - 1;  // Convert the seat number to an integer

            // Check if the seat is available or not in the seats array
            if (seatIndex < 0 || seatIndex >= seats_per_row[rowNumber]) {
                System.out.println("Invalid seat selection.");
            } else if (seats[rowNumber][seatIndex] == 1) {
                System.out.println("Seat is available. Please select a booked seat for cancellation.");
                break;
            } else {
                valid_seat_selection = true; // Set to true to exit the loop

                // Loop through ticketsSold array to search for the ticket to cancel
                for (int i = 0; i < tickets_count; i++) {
                    if (tickets_sold[i].getRow().equals(row + "") && tickets_sold[i].getSeat() == seatNumber) {

                        // Get the file path of the ticket to delete the file
                        String filePath = tickets_sold[i].getFilePath();
                        File fileToDelete = new File(filePath);  // Create a file object to delete the ticket file

                        // Delete the ticket file
                        if (fileToDelete.exists()) {
                            if (fileToDelete.delete()) {
                                System.out.println("File ticket_" + row + "_" + seatNumber + ".txt deleted successfully.");
                            } else {
                                System.out.println("Failed to delete the file.");
                            }
                        } else {
                            System.out.println("File does not exist.");
                        }

                        // Set the ticket to null to remove it from the ticketsSold array
                        tickets_sold[i] = null;
                        break;
                    }
                }

                Ticket[] temp = new Ticket[52];  // Temporary array to store non-null tickets
                int index = 0;  // Index for temp array to store non-null tickets

                // Loop through ticketsSold array to get non-null tickets
                for (Ticket ticket : tickets_sold) {
                    if (ticket != null) {
                        temp[index++] = ticket;
                    }
                }

                // Update ticketsSold array with non-null tickets
                tickets_sold = temp;
                tickets_count--;  // Decrement ticket count by 1 to remove the canceled ticket from the count
                // Update seat status and total sales
                seats[rowNumber][seatIndex] = 1; // Mark seat as available
                // Deduct the price of the canceled ticket from the total sales
                total_sales -= price_per_seat[rowNumber][seatIndex];
                System.out.println("Seat " + row + seatNumber + " cancelled successfully.");
            }
        }
    }



    // Method to find the first available seat
    private static void find_first_available() {
        // Loop through the seats array to find the first available seat
        for (int i = 0; i < plane_rows; i++) {
            for (int j = 0; j < seats_per_row[i]; j++) {
                if (seats[i][j] == 1) {
                    char row = (char) ('A' + i);  // Convert the row number to a character
                    System.out.println("First available seat: " + row + (j + 1));
                    return;
                }
            }
        }
        System.out.println("No available seats.");
    }

    
    // Method to display the seating plan
    private static void show_seating_plan() {
        System.out.println("Seating Plan:");
        // Print column numbers 1-14
        System.out.print("  ");
        for (int column = 1; column <= 14; column++) {
            System.out.print(column + " ");
        }
        System.out.println();

        // Print row letters and seat status
        for (int i = 0; i < plane_rows; i++) {
            char row = (char) ('A' + i);  // Convert the row number to a character
            System.out.print(row + " ");  // Print row letters

            for (int j = 0; j < seats_per_row[i]; j++) {
                if (seats[i][j] == 1) {
                    if (j < 9) {
                        System.out.print("O ");  // Print O for available seats
                    } else {
                        System.out.print(" O ");
                    }
                } else {
                    System.out.print("X ");  // Print X for sold seats
                }
            }
            System.out.println();
        }
    }



    // Method to print tickets information and total sales
    private static void print_tickets_info() {
        // Print all booked ticket information
        for (int i = 0; i < tickets_count; i++) {
            tickets_sold[i].printInfo();  // Print ticket information
            System.out.println(tickets_sold[i]);
            System.out.println("----------------------------");
        }
        // Print total sales
        System.out.println(" ");
        System.out.println("Total Sales: £" + total_sales);
    }



    /**
     * Method to search for a ticket
     * @param scanner Scanner object to get user input
     */
    private static void search_ticket(Scanner scanner) {
        boolean valid_seat_selection = false;  // Variable to store if the selection is valid

        // Loop until the user enters a valid seat to search
        while (!valid_seat_selection) {
            System.out.println("Enter the row (A, B, C, D): ");
            char row = scanner.next().toUpperCase().charAt(0);
            int rowNumber = row - 'A';  // Convert the row character to an integer

            // Check if the entered row is valid
            if (rowNumber < 0 || rowNumber >= plane_rows) {
                System.out.println("Invalid row selection.");
                continue;  // Ask for row input again
            }

            System.out.println("Enter the seat number: ");
            int seatNumber = scanner.nextInt();
            int seatIndex = seatNumber - 1;  // Convert the seat number to an integer

            // Check if the seat is available or not in the seats array
            if (seatIndex < 0 || seatIndex >= seats_per_row[rowNumber]) {
                System.out.println("Invalid seat selection.");
            } else {
                valid_seat_selection = true;  // Set to true to exit the loop and continue with the search

                boolean found = false;  // Variable to store if the ticket is found

                // Loop through ticketsSold array to search for the ticket
                for (int i = 0; i < tickets_count; i++) {
                    Ticket ticket = tickets_sold[i];  // Get ticket from ticketsSold array
                    if (ticket.getRow().equals(row + "") && ticket.getSeat() == seatNumber) {

                        // If seat also booked print ticket information and user information
                        System.out.println("This seat is booked. Here is the ticket information:");
                        System.out.println();
                        ticket.printInfo();
                        System.out.println(ticket);
                        System.out.println("----------------------------");
                        found = true;  // Set found to true if ticket is found in ticketsSold array
                        break;
                    }
                }
                // Print message if ticket is not found
                if (!found) {
                    System.out.println("This seat is available.");
                }
            }
        }
    }

}

