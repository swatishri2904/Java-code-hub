package itemCalculator;

import java.util.*;

public class ItemCalculator {
    private static final double SALES_TAX_RATE_RAW = 0.125; // 12.5% sales tax rate for Raw items
    private static final double SALES_TAX_RATE_MANUFACTURED = 0.125; // 12.5% sales tax rate for Manufactured items

    // List to store item objects
    private List<Item> itemList;

    // Constructor
    public ItemCalculator() {
        itemList = new ArrayList<>();
    }

    // Method to calculate sales tax based on item type
    private double calculateSalesTax(String type, double price) {
        double salesTaxRate = 0.0;
        
        if (type.equalsIgnoreCase("Raw")) {
            salesTaxRate = SALES_TAX_RATE_RAW;
        } else if (type.equalsIgnoreCase("Manufactured")) {
            salesTaxRate = SALES_TAX_RATE_MANUFACTURED;
        }
        
        return price * salesTaxRate;
    }

    // Method to add an item to the itemList
    public void addItem(String name, double price, int quantity, String type) {
        Item item = new Item(name, price, quantity, type);
        itemList.add(item);
    }

    // Method to display all items entered
    public void displayItems() {
        System.out.println("\nItems Entered:");
        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    // Method to calculate and display total cost including taxes
    public void calculateTotalCost() {
        double totalCost = 0.0;

        System.out.println("\nCalculating Total Cost for Items:");

        for (Item item : itemList) {
            double itemTotal = item.getPrice() * item.getQuantity();
            double salesTax = calculateSalesTax(item.getType(), item.getPrice());
            itemTotal += salesTax;
            totalCost += itemTotal;

            System.out.println("- Item: " + item.getName());
            System.out.println("  Price per unit: $" + item.getPrice());
            System.out.println("  Quantity: " + item.getQuantity());
            System.out.println("  Type: " + item.getType());
            System.out.println("  Sales Tax: $" + String.format("%.2f", salesTax));
            System.out.println("  Total Cost (including tax): $" + String.format("%.2f", itemTotal));
            System.out.println();
        }

        System.out.println("Total Cost of all items: $" + String.format("%.2f", totalCost));
    }

    // Scanner to read input from the user
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char choice;
        ItemCalculator itemCalculator = new ItemCalculator();

        do {
            System.out.println("Enter details of the item:");
            System.out.print("- Name: ");
            String name = scanner.nextLine();
            double price = 0;
            int quantity = 0;
            try {
                System.out.print("- Price: ");
                price = scanner.nextDouble();
                if (price <= 0) {
                    throw new IllegalArgumentException("Price must be greater than zero.");
                }

                System.out.print("- Quantity: ");
                quantity = scanner.nextInt();
                if (quantity <= 0) {
                    throw new IllegalArgumentException("Quantity must be greater than zero.");
                }
                scanner.nextLine(); // Consume newline
                System.out.print("- Type (Raw or Manufactured): ");
                String type = scanner.nextLine();
                if (!type.equalsIgnoreCase("Raw") && !type.equalsIgnoreCase("Manufactured")) {
                    throw new IllegalArgumentException("Invalid item type. Type must be Raw or Manufactured.");
                }

                // Add item to ItemCalculator instance
                itemCalculator.addItem(name, price, quantity, type);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input format. Please enter a valid number.");
                scanner.nextLine(); // Consume invalid input
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

            System.out.print("Do you want to enter details of any other item (y/n): ");
            choice = scanner.nextLine().charAt(0);
        } while (choice == 'y' || choice == 'Y');

        // Display all items entered
        itemCalculator.displayItems();

        // Calculate and display total cost including taxes
        itemCalculator.calculateTotalCost();

        // Close the scanner
        scanner.close();
    }
}
