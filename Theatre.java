import java.util.Scanner;

public class Theatre {
    public static void main(String[] args) {
        // 5x5 grid for seats
        // false = open, true = reserved
        boolean[][] seats = new boolean[5][5];

        Scanner sc = new Scanner(System.in);
        boolean quit = false;

        while (!quit) {
            System.out.println("=== THEATRE APP ===");
            System.out.println("1. Show Seating Chart");
            System.out.println("2. Reserve a Seat");
            System.out.println("3. Cancel a Seat");
            System.out.println("4. Quit");
            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Not a number! Try again.\n");
                continue;
            }

            switch (choice) {
                case 1:
                    showSeats(seats);
                    break;
                case 2:
                    reserveSeat(seats, sc);
                    break;
                case 3:
                    cancelSeat(seats, sc);
                    break;
                case 4:
                    System.out.println("Thanks for using my theatre app! Bye!");
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid choice, try again!\n");
                    break;
            }
        }

        sc.close();
    }

    // Prints 'O' if seat open (false), 'X' if reserved (true)
    public static void showSeats(boolean[][] seats) {
        System.out.println("\nSEATING CHART:");
        for (int r = 0; r < seats.length; r++) {
            for (int c = 0; c < seats[r].length; c++) {
                System.out.print(seats[r][c] ? "X " : "O ");
            }
            System.out.println(); // move to next row
        }
        System.out.println();
    }

    // Reserves a seat if available, if not suggests another seat
    public static void reserveSeat(boolean[][] seats, Scanner sc) {
        System.out.print("Enter row # (1-5): ");
        int row = Integer.parseInt(sc.nextLine()) - 1; // convert to 0-based
        System.out.print("Enter col # (1-5): ");
        int col = Integer.parseInt(sc.nextLine()) - 1; // convert to 0-based

        // check if valid row/col
        if (row < 0 || row >= seats.length || col < 0 || col >= seats[0].length) {
            System.out.println("Oops, invalid seat. Try again!\n");
            return;
        }

        // if seat is already taken
        if (seats[row][col]) {
            System.out.println("That seat is taken, sorry!");
            int[] suggestion = findNextSeat(seats);
            if (suggestion != null) {
                System.out.println("Maybe row " + (suggestion[0] + 1)
                        + " col " + (suggestion[1] + 1) + " is open?\n");
            } else {
                System.out.println("No seats left, sorry.\n");
            }
        } else {
            // reserve it
            seats[row][col] = true;
            System.out.println("Seat reserved! Enjoy!\n");
        }
    }

    // Cancels a seat if it was reserved
    public static void cancelSeat(boolean[][] seats, Scanner sc) {
        System.out.print("Enter row # (1-5): ");
        int row = Integer.parseInt(sc.nextLine()) - 1;
        System.out.print("Enter col # (1-5): ");
        int col = Integer.parseInt(sc.nextLine()) - 1;

        // check 
        if (row < 0 || row >= seats.length || col < 0 || col >= seats[0].length) {
            System.out.println("Invalid seat coordinates!\n");
            return;
        }

        // if seat was reserved, make it free
        if (seats[row][col]) {
            seats[row][col] = false;
            System.out.println("Reservation canceled.\n");
        } else {
            System.out.println("That seat wasn't reserved.\n");
        }
    }

    // Finds the first open seat (false) in a simple left-to-right search
     
    public static int[] findNextSeat(boolean[][] seats) {
        for (int r = 0; r < seats.length; r++) {
            for (int c = 0; c < seats[r].length; c++) {
                if (!seats[r][c]) {
                    return new int[] {r, c};
                }
            }
        }
        return null; // no open seats
    }
}
