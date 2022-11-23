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

	public void processCommand(int command) {
		String customerName;
		switch ( command ) {
			//case 0: quit = true ; break ;
			case 1: listCustomers() ; break ;
			case 2: listVideos() ; break ;
			case 3: rentalService.register("customer") ; break ;
			case 4: rentalService.register("video") ; break ;
			case 5:
				customerName = inputCustomer();
				rentalService.rentVideo(customerName);
				break;
			case 6:
				customerName = inputCustomer();
				rentalService.returnVideo(customerName);
				break;
			case 7:
				customerName = inputCustomer();
				rentalService.getCustomerReport(customerName);
				break;
			case 8:
				customerName = inputCustomer();
				rentalService.clearRentals(customerName);
				break ;
			case -1: rentalService.init() ; break ;
			default: break ;
		}
	}
}
