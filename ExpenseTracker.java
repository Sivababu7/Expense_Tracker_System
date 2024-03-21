import java.util.*;

class ExpenseTracker {
    private HashMap<String, List<Expense>> expenses;
    private HashMap<String, Double> categories;

    public ExpenseTracker() {
        this.expenses = new HashMap<>();
        this.categories = new HashMap<>();
    }

    public void addExpense(String date, String description, double amount, String category) {
        Expense expense = new Expense(date, description, amount, category);
        expenses.computeIfAbsent(date, k -> new ArrayList<>()).add(expense);
        categories.put(category, categories.getOrDefault(category, 0.0) + amount);
        System.out.println("Expense added successfully!");
    }

    public void editExpense(String date, int index, String newDescription, double newAmount, String newCategory) {
        List<Expense> expenseList = expenses.get(date);
        if (expenseList != null && index >= 0 && index < expenseList.size()) {
            Expense expense = expenseList.get(index);
            categories.put(expense.getCategory(), categories.get(expense.getCategory()) - expense.getAmount());
            expense.setDescription(newDescription);
            expense.setAmount(newAmount);
            expense.setCategory(newCategory);
            categories.put(newCategory, categories.getOrDefault(newCategory, 0.0) + newAmount);
            System.out.println("Expense edited successfully!");
        } else {
            System.out.println("Invalid date or index!");
        }
    }

    public void deleteExpense(String date, int index) {
        List<Expense> expenseList = expenses.get(date);
        if (expenseList != null && index >= 0 && index < expenseList.size()) {
            Expense expense = expenseList.get(index);
            categories.put(expense.getCategory(), categories.get(expense.getCategory()) - expense.getAmount());
            expenseList.remove(index);
            System.out.println("Expense deleted successfully!");
        } else {
            System.out.println("Invalid date or index!");
        }
    }

    public void viewExpenses(String date) {
        if (expenses.containsKey(date)) {
            double totalAmount = 0;
            System.out.println("Expenses for " + date + ":");
            System.out.printf("%-5s %-20s %-30s %-10s %-10s%n", "Index", "Date", "Description", "Amount", "Category");
            List<Expense> expenseList = expenses.get(date);
            for (int i = 0; i < expenseList.size(); i++) {
                Expense expense = expenseList.get(i);
                System.out.printf("%-5d %-20s %-30s RS %-9.2f %-10s%n", i, expense.getDate(), expense.getDescription(),
                        expense.getAmount(), expense.getCategory());
                totalAmount += expense.getAmount();
            }
            System.out.println("Total expenses for " + date + " =  RS " + String.format("%.2f", totalAmount));
        } else {
            System.out.println("No expenses recorded for " + date);
        }
    }

    public void viewAllExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded yet.");
        } else {
            System.out.println("All Expenses:");
            System.out.printf("%-5s %-20s %-30s %-10s %-10s%n", "Index", "Date", "Description", "Amount", "Category");
            int index = 0;
            for (List<Expense> expenseList : expenses.values()) {
                for (Expense expense : expenseList) {
                    System.out.printf("%-5d %-20s %-30s  RS  %-9.2f %-10s%n", index, expense.getDate(),
                            expense.getDescription(), expense.getAmount(), expense.getCategory());
                    index++;
                }
            }
        }
    }

    public void viewExpenseSummary() {
        if (categories.isEmpty()) {
            System.out.println("No expenses recorded yet.");
        } else {
            System.out.println("Expense Summary:");
            System.out.printf("%-20s %-10s%n", "Category", "Total Amount");
            for (Map.Entry<String, Double> entry : categories.entrySet()) {
                System.out.printf("%-20s RS %-9.2f%n", entry.getKey(), entry.getValue());
            }
        }
    }

    // Other methods like editCategory, deleteCategory, viewMonthlyExpenseReport can be added here.

    private static class Expense {
        private String date;
        private String description;
        private double amount;
        private String category;

        public Expense(String date, String description, double amount, String category) {
            this.date = date;
            this.description = description;
            this.amount = amount;
            this.category = category;
        }

        // Getters and setters for the Expense class

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }

    public static void main(String[] args) {
        ExpenseTracker expenseTracker = new ExpenseTracker();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nExpense Tracker Menu:");
            System.out.println("1. Add Expense");
            System.out.println("2. Edit Expense");
            System.out.println("3. Delete Expense");
            System.out.println("4. View Expenses");
            System.out.println("5. View All Expenses");
            System.out.println("6. View Expense Summary");
            System.out.println("7. Exit");

            System.out.print("Enter your choice (1/2/3/4/5/6/7): ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter the date (MM/DD/YYYY): ");
                    String date = scanner.next();
                    System.out.print("Enter a brief description: ");
                    scanner.nextLine();
                    String description = scanner.nextLine();
                    System.out.print("Enter the expense amount: ");
                    double amount = scanner.nextDouble();
                    System.out.print("Enter the category: ");
                    scanner.nextLine();
                    String category = scanner.nextLine();
                    expenseTracker.addExpense(date, description, amount, category);
                    break;
                case 2:
                    System.out.print("Enter the date (MM/DD/YYYY): ");
                    date = scanner.next();
                    System.out.print("Enter the index of the expense to edit: ");
                    int index = scanner.nextInt();
                    System.out.print("Enter a new description: ");
                    scanner.nextLine();
                    description = scanner.nextLine();
                    System.out.print("Enter a new expense amount: ");
                    amount = scanner.nextDouble();
                    System.out.print("Enter a new category: ");
                    scanner.nextLine();
                    category = scanner.nextLine();
                    expenseTracker.editExpense(date, index, description, amount, category);
                    break;
                case 3:
                    System.out.print("Enter the date (MM/DD/YYYY): ");
                    date = scanner.next();
                    System.out.print("Enter the index of the expense to delete: ");
                    index = scanner.nextInt();
                    expenseTracker.deleteExpense(date, index);
                    break;
                case 4:
                    System.out.print("Enter the date (MM/DD/YYYY): ");
                    date = scanner.next();
                    expenseTracker.viewExpenses(date);
                    break;
                case 5:
                    expenseTracker.viewAllExpenses();
                    break;
                case 6:
                    expenseTracker.viewExpenseSummary();
                    break;
                case 7:
                    System.out.println("Exiting Expense Tracker...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
