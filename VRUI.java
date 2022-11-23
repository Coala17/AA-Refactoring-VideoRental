import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class VRUI {
	private static Scanner scanner = new Scanner(System.in) ;

	private RentalService rentalService = new RentalService();

	public static void main(String[] args) {
		VRUI ui = new VRUI();

		boolean quit = false ;
		while ( ! quit ) {
			ui.showCommand();
			int command = ui.inputCommand();
			quit = ui.isQuit(command);
			if(!quit) {
				ui.processCommand(command);
			}
		}
		System.out.println("Bye");
	}


	public void showCommand() {
		System.out.println("\nSelect a command !");
		System.out.println("\t 0. Quit");
		System.out.println("\t 1. List customers");
		System.out.println("\t 2. List videos");
		System.out.println("\t 3. Register customer");
		System.out.println("\t 4. Register video");
		System.out.println("\t 5. Rent video");
		System.out.println("\t 6. Return video");
		System.out.println("\t 7. Show customer report");
		System.out.println("\t 8. Show customer and clear rentals");
	}
	public int inputCommand() {
		int command = scanner.nextInt() ;
		return command ;
	}

	public boolean isQuit(int command) {
		return (command == 0);
	}

	private String inputCustomer() {
		System.out.println("Enter customer name: ") ;
		String customerName = scanner.next() ;
		return customerName;
	}

	private void listVideos() {
		System.out.println("List of videos");
		List<String> videoListStr = rentalService.listVideos();
		for ( String videoStr: videoListStr ) {
			System.out.println(videoStr) ;
		}
		System.out.println("End of list");
	}
	private void listCustomers() {
		System.out.println("List of customers");
		List<String> customerListStr = rentalService.listCustomers();
		for ( String customerList: customerListStr ) {
			System.out.println(customerList) ;
		}
		System.out.println("End of list");
	}

	private void getCustomerReport() {
		String customerName = inputCustomer();
		String result = rentalService.getCustomerReport(customerName);
		System.out.println(result);
	}

	private void rentVideo() {
		String customerName = inputCustomer();

		System.out.println("Enter video title to rent: ") ;
		String videoTitle = scanner.next() ;

		rentalService.rentVideo(customerName, videoTitle);
	}

	private void returnVideo() {
		String customerName = inputCustomer();

		System.out.println("Enter video title to return: ") ;
		String videoTitle = scanner.next() ;

		rentalService.returnVideo(customerName, videoTitle);
	}

	private void registerCustomer() {
		System.out.println("Enter customer name: ") ;
		String name = scanner.next();
		rentalService.registerCustomer(name);
	}

	private void registerVideo() {
		System.out.println("Enter video title to register: ") ;
		String title = scanner.next() ;

		System.out.println("Enter video type( 1 for VHD, 2 for CD, 3 for DVD ):") ;
		int videoType = scanner.nextInt();

		System.out.println("Enter price code( 1 for Regular, 2 for New Release ):") ;
		int priceCode = scanner.nextInt();

		rentalService.registerVideo(title, videoType, priceCode);
	}
	private void clearRentals() {
		String customerName = inputCustomer();
		List<String> rentalListStr = rentalService.clearRentals(customerName);
		for ( String rentalStr: rentalListStr ) {
			System.out.println(rentalStr) ;
		}
	}

	public void processCommand(int command) {
		switch ( command ) {
			//case 0: quit = true ; break ;
			case 1: listCustomers(); break;
			case 2: listVideos(); break;
			case 3: registerCustomer(); break;
			case 4: registerVideo(); break;
			case 5: rentVideo(); break;
			case 6: returnVideo(); break;
			case 7: getCustomerReport(); break;
			case 8: clearRentals(); break;
			case -1: rentalService.init() ; break ;
			default: break ;
		}
	}
}
