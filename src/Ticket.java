import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Ticket {
    private String row;
    private int seat;
    private int price;
    private Person person;

    public Ticket(String row, int seat, int price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    // Getters and setters
    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getFilePath() {
        return "ticket_" + row + "_" + seat + ".txt";
    }

    // Method to print ticket information
    public void printInfo() {
        System.out.println("----------------------------");
        System.out.println("Ticket Information:");
        System.out.println("Row: " + row);
        System.out.println("Seat: " + seat);
        System.out.println("Price: £" + price);
        person.print_passenger_info();
    }


    /*
    Method to save ticket information to a file
    Reference - Java PrintWriter class:
      - Adopted from code at javapoint - https://www.javatpoint.com/java-printwriter-class
    */
    public void save() {
        // Create a file with the name "ticket_row_seat.txt"
        String filename = "ticket_" + row + "_" + seat + ".txt";
        try (PrintWriter writer = new PrintWriter(filename)) {
            writer.println("Ticket Information:");
            writer.println("Row no: " +row);
            writer.println("Seat no: " +seat);
            writer.println("Price: £" +price);
            writer.println("Passenger Information:");
            writer.println("First name: " +person.getName());
            writer.println("Surname: " +person.getSurname());
            writer.println("Email: " +person.getEmail());
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }
    }

}
