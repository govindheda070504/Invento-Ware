import java.io.*;
import java.util.*;

class Product {
    private int id;
    private String name;
    private String category;
    private double price;
    private int quantity;

    public Product(int id, String name, String category, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

class Inventory {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        boolean found = false;
        for (Product p : products) {
            if (p.getId() == product.getId()) {
                System.out.println("Id already exists.");
                found = true;
                break;
            }
        }
        if (!found) {
            products.add(product);
            System.out.println("Product added successfully.");
            System.out.println("-----------------------------------------------------------");
        }
    }

    public void removeProduct(int id) {
        boolean found = false;
        Iterator<Product> iterator = products.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getId() == id) {
                iterator.remove();
                found = true;
                System.out.println("Product removed successfully.");
                System.out.println("-----------------------------------------------------------");
                break;
            }
        }
        if (!found) {
            System.out.println("Id does not exist");
        }
    }

    public Product findProduct(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public void updateProduct(int id, String name, String category, double price, int quantity) {
        boolean found = false;
        for (Product product : products) {
            if (product.getId() == id) {
                product.setName(name);
                product.setCategory(category);
                product.setPrice(price);
                product.setQuantity(quantity);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("ID does not exist.");
        }
    }

    public void printProduct() {
        for (Product product : products) {
            System.out.println("ID : " + product.getId());
            System.out.println("Name : " + product.getName());
            System.out.println("Category : " + product.getCategory());
            System.out.println("Price : $" + product.getPrice());
            System.out.println("Quantity : " + product.getQuantity());
        }
    }

    public void saveInventoryToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            for (Product product : products) {
                writer.write(product.getId() + "," + product.getName() + "," + product.getCategory() + "," + product.getPrice() + "," + product.getQuantity());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error: Could not save to file " + filename);
        }
    }

    public void loadInventoryFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String category = parts[2];
                double price = Double.parseDouble(parts[3]);
                int quantity = Integer.parseInt(parts[4]);
                Product product = new Product(id, name, category, price, quantity);
                products.add(product);
            }
        } catch (IOException e) {
            System.out.println("Error: Could not open file " + filename);
        }
    }

    // Insertion Sort algorithm to sort products by ID
    public void sortProductsByID() {
        int n = products.size();
        for (int i = 1; i < n; i++) {
            Product key = products.get(i);
            int j = i - 1;
            while (j >= 0 && products.get(j).getId() > key.getId()) {
                products.set(j + 1, products.get(j));
                j--;
            }
            products.set(j + 1, key);
        }
        System.out.println("Products sorted by ID.");
        System.out.println("-----------------------------------------------------------");
    }
}

public class main {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        Scanner scanner = new Scanner(System.in);
        System.out.println("-----------------------------------------------------------");
        System.out.println("---------------Inventory Management System ----------------");
        System.out.println("-----------------------------------------------------------");
        System.out.println("------------------------- Welcome! ------------------------");
        System.out.println("-----------------------------------------------------------");

        char choice;
        do {
            System.out.println("Please choose an option:");
            System.out.println("1. Add a product");
            System.out.println("2. Remove a product");
            System.out.println("3. Find a product");
            System.out.println("4. Update a product");
            System.out.println("5. View all products");
            System.out.println("6. Save inventory to file");
            System.out.println("7. Load Inventory from file");
            System.out.println("8. Sort the Product by ID");
            System.out.println("Q. Quit");
            choice = scanner.next().charAt(0);

            switch (choice) {
                case '1': {
                    System.out.print("Enter ID: ");
                    int id = scanner.nextInt();
                    System.out.print("Enter product name: ");
                    String name = scanner.next();
                    System.out.print("Enter product category: ");
                    String category = scanner.next();
                    System.out.print("Enter product price: $ ");
                    double price = scanner.nextDouble();
                    System.out.print("Enter product quantity: ");
                    int quantity = scanner.nextInt();
                    Product product = new Product(id, name, category, price, quantity);
                    inventory.addProduct(product);
                    break;
                }
                case '2': {
                    System.out.print("Enter product id: ");
                    int id = scanner.nextInt();
                    inventory.removeProduct(id);
                    break;
                }
                case '3': {
                    System.out.print("Enter product id: ");
                    int id = scanner.nextInt();
                    Product product = inventory.findProduct(id);
                    if (product != null) {
                        System.out.println("Name: " + product.getName());
                        System.out.println("Category: " + product.getCategory());
                        System.out.println("Price: $ " + product.getPrice());
                        System.out.println("Quantity: " + product.getQuantity());
                        System.out.println("-----------------------------------------------------------");
                    } else {
                        System.out.println("Product not found.");
                        System.out.println("-----------------------------------------------------------");
                    }
                    break;
                }
                case '4': {
                    System.out.print("Enter the product id: ");
                    int id = scanner.nextInt();
                    System.out.print("Enter new product name: ");
                    String name = scanner.next();
                    System.out.print("Enter new product category: ");
                    String category = scanner.next();
                    System.out.print("Enter new product price: $ ");
                    double price = scanner.nextDouble();
                    System.out.print("Enter new product quantity: ");
                    int quantity = scanner.nextInt();
                    inventory.updateProduct(id, name, category, price, quantity);
                    System.out.println("Product updated successfully.");
                    System.out.println("-----------------------------------------------------------");
                    break;
                }
                case '5': {
                    inventory.printProduct();
                    break;
                }
                case '6': {
                    inventory.saveInventoryToFile("inventory1.csv");
                    System.out.println("Inventory saved to file.");
                    System.out.println("-----------------------------------------------------------");
                    break;
                }
                case '7': {
                    inventory.loadInventoryFromFile("inventory1.csv");
                    System.out.println("Inventory loaded from file.");
                    System.out.println("-----------------------------------------------------------");
                    break;
                }
                case '8': {
                    inventory.sortProductsByID();
                    System.out.println("Products sorted by ID.");
                    System.out.println("-----------------------------------------------------------");
                    break;
                }
                case 'q':
                case 'Q':
                    System.out.println("Goodbye!");
                    System.out.println("-----------------------------------------------------------");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid Choice. Please Try again");
                    System.out.println("-----------------------------------------------------------");
                    break;
            }
        } while (true);
    }
}
