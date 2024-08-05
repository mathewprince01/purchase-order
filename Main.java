package com.Ordermanagment1;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class Main {
     

    public static void main(String[] args) {
        try {
        	Main main = new Main();
        	Scanner scanner = new Scanner(System.in);
        	List<OrderDAO> listOrderDAO = new ArrayList<>();

            Connection connection = DBConnection.getConnection();
            if (connection != null) {
                System.out.println("Connected to database.");
            }

            while (true) {
                System.out.println("\nChoose an option:");
                System.out.println("1. View ");
                System.out.println("2. Insert");
                System.out.println("3. Update");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                    	System.out.println("Choose an option:");
                        System.out.println("\n1. View Order");
                        System.out.println("\n2. View Orderline");
                        System.out.println("\n3. View Order by id");
                        System.out.println("\n4. Exit");
                    	int viewChoice = scanner.nextInt();
                    	switch (viewChoice) {
	                        case 1:
		                    	main.viewOrders(scanner,main);
		                    	break;
	                        
	                        case 2:
	                        	main.viewOrderLines(scanner,main);
	                        	break;
	                        
	                        case 3:
	                        	main.vieworderById(scanner,main);
	                        	break;
	                        case 4:
	                        	break;
	                        default:
	                        	break;	
                    	}	
                        break;
                    case 2:
                        main.insertOrder1(scanner,listOrderDAO,main);
                        break;  
                    case 3:
                    	boolean updateEntry = true;
                    	while (updateEntry) {  
                    	System.out.println("Choose an option:");
                        System.out.println("\n1. Update Order");
                        System.out.println("\n2. Update Orderline");
                        System.out.println("\n3.Exiting..");
                        int updateChoice = scanner.nextInt();
                    	switch (updateChoice) {
                        case 1:
	                    	main.updateorder(scanner,main);
	                    	break;
                        
                        case 2:
                        	main.updateorderline(scanner,main);
                        break;
                        case 3:
                        	System.out.println("Exiting..");
                        	updateEntry = false;
                        	break;
                        default:
                        	System.out.println("Invalid choice. Please enter a number from 1 to 3.");
                        	break;	
                	}
                        }
                    break;
                    case 4:
                        System.out.println("Exiting...");
                        scanner.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number from 1 to 4.");
                        break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }

    private void updateorder(Scanner scanner,Main main) {
    	showAllOrders( scanner,main);
    	
    }
    private void updateorderline(Scanner scanner,Main main) {
    	showAllOrderLines( scanner,main);
    	
    }
    private void vieworderById(Scanner scanner, Main main) throws SQLException {
    	getCOrderId();
    	System.out.println("enter the orderid");
   	    String c_order_id= scanner.next();
   	    
		 main.showordertable(c_order_id);
		 main.showordertableline(c_order_id);
	}
    

	private void getCOrderId() throws SQLException {
	try(Connection connection = DBConnection.getConnection()) {
		String query="SELECT c_order_id,vendor FROM c_order";
		try(PreparedStatement pstmt = connection.prepareStatement(query)){
			ResultSet rs = pstmt.executeQuery();
			System.out.println("c_order_id"+"      "+"vendor");
			while(rs.next()) {
				System.out.println(rs.getString("c_order_id")+"      "+rs.getString("vendor"));
			}
		}
		
	}
	
		
	}

	private void showordertableline(String c_order_id) {
		String query = "SELECT * FROM c_orderline WHERE c_order_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, c_order_id);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("Order Lines for Order ID: " + c_order_id);
            while (rs.next()) {
                // Assuming your order_lines table has columns: line_id, order_id, product_name, quantity, price
            	System.out.println(
                        rs.getString("c_orderline_id") + " " +
                        rs.getString("isactive") + " " + 
                        rs.getTimestamp("created").toLocalDateTime() + " " +
                        rs.getString("createdBy") + " " +
                        rs.getTimestamp("updated").toLocalDateTime() + " " +
                        rs.getString("updatedBy") + " " +
                        rs.getString("issale").charAt(0) + " " +
                        rs.getString("product") + " " +
                        rs.getString("umo") + " " +
                        rs.getDouble("quantity") + " " +
                        rs.getDouble("price")+" "+
                        rs.getString("c_order_id")
                    );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


		

		
	

	private void showordertable(String c_order_id) {
		String query = "SELECT * FROM c_order WHERE c_order_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, c_order_id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Assuming your orders table has columns: order_id, customer_name, order_date
            	 System.out.println(rs.getString("c_order_id")+""+
     					rs.getString("isactive")+" "+ 
     					rs.getTimestamp("created").toLocalDateTime()+" "+
     					rs.getString("createdBy")+" "+
     					rs.getTimestamp("updated").toLocalDateTime()+" "+
     					rs.getString("updatedBy")+" "+
     					rs.getString("isSale").charAt(0)+""+
     					rs.getDate("order_Date").toLocalDate()+""+
     					rs.getString("vendor")+""+
     					rs.getDate("edd").toLocalDate()+""+
     					rs.getString("shipping_address"));
            } else {
                System.out.println("Order not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
		
		
	

	private void viewOrderLines(Scanner scanner, Main main) {
    	System.out.println("enter the limit");
    	int limit=scanner.nextInt();
    	System.out.println("enter the offset");
    	int offset= scanner.nextInt();
//    	System.out.println("enter the orderid");
//    	 String c_order_id= scanner.next();
		 
    	main.viewOrderlines(limit,offset);
		
	}

	private void viewOrders(Scanner scanner, Main main) {
    	System.out.println("enter the limit");
    	int limit=scanner.nextInt();
    	System.out.println("enter the offset");
    	int offset= scanner.nextInt();

		 
    	main.viewOrder(limit,offset);
	}

	private void insertOrder1(Scanner scanner,List<OrderDAO> listOrderDAO,Main main) throws SQLException {
		LocalDateTime currentDateTime = LocalDateTime.now();
        LocalTime currentLocalTime = currentDateTime.toLocalTime();
        LocalTime adjustedTime = currentLocalTime.minusHours(5).minusMinutes(30);
        LocalDateTime adjustedDateTime = currentDateTime.with(adjustedTime);
 

		
        System.out.print("Enter order ID: ");
        String orderId = scanner.nextLine();
        if (orderId.isEmpty()) {
            System.out.println("OrderDAO ID is mandatory.");
            return;
        }

        System.out.print("Is active (Y/N): ");
        char isActive = scanner.nextLine().charAt(0);
        
        LocalDateTime created=adjustedDateTime;

        System.out.print("Created by: ");
        String createdBy = scanner.nextLine();
        if (createdBy.isEmpty()) {
            System.out.println("Created by is mandatory.");
            return;
        }
        
        LocalDateTime updated=adjustedDateTime;

        System.out.print("Updated by: ");
        String updatedBy = scanner.nextLine();
        if (updatedBy.isEmpty()) {
            System.out.println("Updated by is mandatory.");
            return;
        }

        System.out.print("Is this a Sale? (Y/N): ");
        char isSale = scanner.nextLine().charAt(0);

        System.out.print("Enter OrderDAO Date (YYYY-MM-DD): ");
        LocalDate orderDate = LocalDate.parse(scanner.nextLine());

        System.out.print("Enter Estimated Delivery Date (YYYY-MM-DD): ");
        LocalDate edd = LocalDate.parse(scanner.nextLine());

        System.out.print("Enter Vendor: ");
        String vendor = scanner.nextLine();
        if (vendor.isEmpty()) {
            System.out.println("Vendor is mandatory.");
            return;
        }

        System.out.print("Enter Shipping Address: ");
        String shippingAddress = scanner.nextLine();
        if (shippingAddress.isEmpty()) {
            System.out.println("Shipping Address is mandatory.");
            return;
        }
        
        ////// insert orderlines input
        List<OrderlineDAO> orderLines = new ArrayList<>();
        System.out.print("Enter number of order lines: ");
        
       boolean orderlineEntry=true;
        while(orderlineEntry) {
            System.out.print("Enter Order Line ID: ");
            String orderLineId = scanner.nextLine();
            System.out.print("Is active (Y/N): ");
            char orderLineIsActive = scanner.nextLine().charAt(0);
            System.out.print("Created by: ");
            String orderLineCreatedBy = scanner.nextLine();
            System.out.print("Updated by: ");
            String orderLineUpdatedBy = scanner.nextLine();
            System.out.print("Is this a Sale? (Y/N): ");
            char orderLineIsSale = scanner.nextLine().charAt(0);
            System.out.print("Enter Product: ");
            String product = scanner.nextLine();
            System.out.print("Enter UMO: ");
            String umo = scanner.nextLine();
            System.out.print("Enter Quantity: ");
            double quantity = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter Price: ");
            double price = Double.parseDouble(scanner.nextLine());
            OrderlineDAO orderLine = new OrderlineDAO (orderLineId, orderLineIsActive, created, orderLineCreatedBy, updated, orderLineUpdatedBy, orderLineIsSale, product, umo, quantity, price,orderId);
            orderLines.add(orderLine);
            System.out.println();
            System.out.print("do you want to  another orderline Y/N : ");
            String orderlineReEntry=scanner.next();
            if("Y".equalsIgnoreCase(orderlineReEntry))
            	orderlineEntry=true;
            else
            	orderlineEntry = false;
            	break;
        }    

     
         	
         	        OrderDAO orderDAO = new OrderDAO(orderId, isActive, created, createdBy, updated, updatedBy, isSale, orderDate, vendor, edd, shippingAddress);
        listOrderDAO.add(orderDAO);

        main.createOrder(listOrderDAO);
        main.createOrderline(orderLines);
        System.out.println("Order added to collection.");

    
}
	
	
	 

			public void createOrderline(List<OrderlineDAO> orderLines) throws SQLException {
	        String sql = "INSERT INTO c_orderline (c_orderline_id, isactive, created, createdby, updated, updatedby, issale, product, umo, quantity, price,c_order_id)" + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
	        try (Connection connection = DBConnection.getConnection();
	             PreparedStatement stmt = connection.prepareStatement(sql)) {
	            for (OrderlineDAO orderLine : orderLines) {
	                stmt.setString(1, orderLine.getOrderLineId());
	                stmt.setString(2, String.valueOf(orderLine.getIsActive()).toUpperCase());
	                stmt.setTimestamp(3, Timestamp.valueOf(orderLine.getCreated()));
	                stmt.setString(4, orderLine.getCreatedBy());
	                stmt.setTimestamp(5, Timestamp.valueOf(orderLine.getUpdated()));
	                stmt.setString(6, orderLine.getUpdatedBy());
	                stmt.setString(7, String.valueOf(orderLine.getIsSale()).toUpperCase());
	                stmt.setString(8, orderLine.getProduct());
	                stmt.setString(9, orderLine.getUmo());
	                stmt.setDouble(10, orderLine.getQuantity());
	                stmt.setDouble(11, orderLine.getPrice());
	                stmt.setString(12, orderLine.getOrderId());
	                stmt.executeUpdate();
	            }
	        }
	    }
	
		
		
	

	public void createOrder( List<OrderDAO> orderDAO) throws SQLException {
        String sql = "INSERT INTO c_order (c_order_id, isactive, created, createdby, updated, updatedby, issale, order_date, vendor, edd, shipping_address)" + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
        	for (OrderDAO order1:orderDAO) {
            stmt.setString(1, order1.getOrderId());
            stmt.setString(2, String.valueOf(order1.getIsActive()).toUpperCase());
            stmt.setTimestamp(3, Timestamp.valueOf(order1.getCreated()));
            stmt.setString(4, order1.getCreatedBy());
            stmt.setTimestamp(5, Timestamp.valueOf(order1.getUpdated()));
            stmt.setString(6, order1.getUpdatedBy());
            stmt.setString(7, String.valueOf(order1.getIsSale()).toUpperCase());
            stmt.setDate(8, Date.valueOf(order1.getOrderDate()));
            stmt.setString(9, order1.getVendor());
            stmt.setDate(10, Date.valueOf(order1.getEdd()));
            stmt.setString(11, order1.getShippingAddress());
            stmt.executeUpdate();
        }
        	
        }
    }

    // Method to update an order in the database
    public static void updateOrder(OrderDAO orderDAO) throws SQLException {
    	 ZonedDateTime now=ZonedDateTime.now(ZoneId.of("UTC"));
        String sql = "UPDATE orders SET isActive=?, created=?, createdBy=?, updated=?, updatedBy=?, isSale=?, orderDate=?, vendor=?, edd=?, shippingAddress=? " +
                     "WHERE orderId=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, String.valueOf(orderDAO.getIsActive()));
            stmt.setTimestamp(2, Timestamp.valueOf(now.toLocalDateTime()));
            stmt.setString(3, orderDAO.getCreatedBy());
            stmt.setTimestamp(4, Timestamp.valueOf(orderDAO.getUpdated()));
            stmt.setString(5, orderDAO.getUpdatedBy());
            stmt.setString(6, String.valueOf(orderDAO.getIsSale()));
            stmt.setDate(7, Date.valueOf(orderDAO.getOrderDate()));
            stmt.setString(8, orderDAO.getVendor());
            stmt.setDate(9, Date.valueOf(orderDAO.getEdd()));
            stmt.setString(10, orderDAO.getShippingAddress());
            stmt.setString(11, orderDAO.getOrderId());
            stmt.executeUpdate();
        }
    }

    // Method to view orders with pagination (limit and offset)
    public void viewOrder(int limit, int offset){
       
        String sql = "SELECT * FROM c_order LIMIT ? OFFSET ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println(rs.getString("c_order_id")+""+
                    					rs.getString("isactive")+" "+ 
                    					rs.getTimestamp("created").toLocalDateTime()+" "+
                    					rs.getString("createdBy")+" "+
                    					rs.getTimestamp("updated").toLocalDateTime()+" "+
                    					rs.getString("updatedBy")+" "+
                    					rs.getString("isSale").charAt(0)+""+
                    					rs.getDate("order_Date").toLocalDate()+""+
                    					rs.getString("vendor")+""+
                    					rs.getDate("edd").toLocalDate()+""+
                    					rs.getString("shipping_address"));
                    
                   
                }
            }catch (SQLException e) {
    			
    			e.printStackTrace();
    		}
        } catch (SQLException e) {
			
			e.printStackTrace();
		}
        
    }

    // Method to fetch order lines by order ID
    public void viewOrderlines(int limit, int offset  ) {
        String sql = "SELECT * FROM c_orderline LIMIT ? OFFSET ?";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
        	stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("Order Lines ");
                while (rs.next()) {
                    System.out.println(
                        rs.getString("c_orderline_id") + " " +
                        rs.getString("isactive") + " " + 
                        rs.getTimestamp("created").toLocalDateTime() + " " +
                        rs.getString("createdBy") + " " +
                        rs.getTimestamp("updated").toLocalDateTime() + " " +
                        rs.getString("updatedBy") + " " +
                        rs.getString("issale").charAt(0) + " " +
                        rs.getString("product") + " " +
                        rs.getString("umo") + " " +
                        rs.getDouble("quantity") + " " +
                        rs.getDouble("price")+" "+
                        rs.getString("c_order_id")
                    );
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    /////////// update code////////////
    private void showAllOrders(Scanner scanner, Main main) {
        String query = "SELECT * FROM c_order";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("Orders:");
            while (rs.next()) {
                System.out.println("Order ID: " + rs.getString("c_order_id") + ", Vendor: " + rs.getString("vendor") + ", EDD: " + rs.getDate("edd"));
            }

            System.out.println("Enter the order ID to update:");
            String orderId = scanner.next();
            updateOrder(scanner, orderId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAllOrderLines(Scanner scanner,Main main) {
        String query = "SELECT * FROM c_orderline";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("Order Lines:");
            while (rs.next()) {
                System.out.println("Order Line ID: " + rs.getString("c_orderline_id") + ", Product: " + rs.getString("product") + ", Quantity: " + rs.getDouble("quantity") + ", Price: " + rs.getDouble("price"));
            }

            System.out.println("Enter the order line ID to update:");
            String lineId = scanner.next();
            updateOrderLine(scanner, lineId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateOrder(Scanner scanner, String orderId) {
//    	LocalDateTime currentDateTime = LocalDateTime.now();
//        LocalTime currentLocalTime = currentDateTime.toLocalTime();
//        LocalTime adjustedTime = currentLocalTime.minusHours(5).minusMinutes(30);
//        LocalDateTime adjustedDateTime = currentDateTime.with(adjustedTime);
//  	
 
 
        showOrder(orderId);
        ZonedDateTime now=ZonedDateTime.now(ZoneId.of("UTC"));
        System.out.println("Enter updateby :");
        String updatedby = scanner.next();

        System.out.println("Enter new vendor:");
        String newVendor = scanner.next();

        System.out.println("Enter new EDD (YYYY-MM-DD):");
        String newEdd = scanner.next();

        System.out.println("Enter new shipping address:");
        scanner.nextLine();
        String newShippingAddress = scanner.nextLine();

        String query = "UPDATE public.c_order SET  updated=?, updatedby=?,vendor = ?, edd = ?, shipping_address = ? WHERE c_order_id = ?";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            
            pstmt.setTimestamp(1, Timestamp.valueOf(now.toLocalDateTime()));
            pstmt.setString(2, updatedby);
            pstmt.setString(3, newVendor);
            pstmt.setDate(4, java.sql.Date.valueOf(newEdd));
            pstmt.setString(5, newShippingAddress);
            pstmt.setString(6, orderId);
            
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Order updated successfully.");
            } else {
                System.out.println("Order ID not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateOrderLine(Scanner scanner, String lineId) {

        showOrderLine(lineId);
   	 ZonedDateTime now=ZonedDateTime.now(ZoneId.of("UTC"));
   	 System.out.println("Enter updateby:");
     String updatedby = scanner.next();
        
        System.out.println("Enter new product name:");
        String newProductName = scanner.next();

        System.out.println("Enter new quantity:");
        double newQuantity = scanner.nextDouble();

        System.out.println("Enter new price:");
        double newPrice = scanner.nextDouble();

        String query = "UPDATE public.c_orderline SET updated=?,updatedby=?, product = ?, quantity = ?, price = ? WHERE c_orderline_id = ?";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
        	 
        	 pstmt.setTimestamp(1, Timestamp.valueOf(now.toLocalDateTime()));
        	 pstmt.setString(2, updatedby);
	         pstmt.setString(3, newProductName);
	         pstmt.setDouble(4, newQuantity);
	         pstmt.setDouble(5, newPrice);
	         pstmt.setString(6, lineId);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Order line updated successfully.");
            } else {
                System.out.println("Order line ID not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showOrder(String orderId) {
        String query = "SELECT * FROM public.c_order WHERE c_order_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, orderId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Current Order Details:");
                System.out.println("Order ID: " + rs.getString("c_order_id"));
                System.out.println("Vendor: " + rs.getString("vendor"));
                System.out.println("EDD: " + rs.getDate("edd"));
                System.out.println("Shipping Address: " + rs.getString("shipping_address"));
            } else {
                System.out.println("Order not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showOrderLine(String lineId) {
        String query = "SELECT * FROM c_orderline WHERE c_orderline_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, lineId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Current Order Line Details:");
                System.out.println("Line ID: " + rs.getString("c_orderline_id"));
                System.out.println("Product: " + rs.getString("product"));
                System.out.println("Quantity: " + rs.getDouble("quantity"));
                System.out.println("Price: " + rs.getDouble("price"));
            } else {
                System.out.println("Order line not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
   
    
    
 