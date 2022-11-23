import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class RentalService {
    private static Scanner scanner = new Scanner(System.in) ;

    private List<Customer> customers = new ArrayList<Customer>() ;

    private List<Video> videos = new ArrayList<Video>() ;

    public void init() {
        Customer james = new Customer("James") ;
        Customer brown = new Customer("Brown") ;
        customers.add(james) ;
        customers.add(brown) ;

        Video v1 = new Video("v1", Video.CD, Video.REGULAR, new Date()) ;
        Video v2 = new Video("v2", Video.DVD, Video.NEW_RELEASE, new Date()) ;
        videos.add(v1) ;
        videos.add(v2) ;

        Rental r1 = new Rental(v1) ;
        Rental r2 = new Rental(v2) ;

        james.addRental(r1) ;
        james.addRental(r2) ;
    }

    private Customer findCustomer(String customerName) {
        Customer foundCustomer = null ;
        for ( Customer customer: customers ) {
            if ( customer.getName().equals(customerName)) {
                foundCustomer = customer ;
                break ;
            }
        }
        //if ( foundCustomer == null ) return ;
        return foundCustomer;
    }

    public void clearRentals(String customerName) {
        Customer foundCustomer = findCustomer(customerName);

        if ( foundCustomer == null ) {
            System.out.println("No customer found") ;
        } else {
            System.out.println("Name: " + foundCustomer.getName() +
                    "\tRentals: " + foundCustomer.getRentals().size()) ;
            for ( Rental rental: foundCustomer.getRentals() ) {
                System.out.print("\tTitle: " + rental.getVideo().getTitle() + " ") ;
                System.out.print("\tPrice Code: " + rental.getVideo().getPriceCode()) ;
            }

            List<Rental> rentals = new ArrayList<Rental>() ;
            foundCustomer.setRentals(rentals);
        }
    }

    public void returnVideo(String customerName) {
        Customer foundCustomer = findCustomer(customerName);

        System.out.println("Enter video title to return: ") ;
        String videoTitle = scanner.next() ;

        List<Rental> customerRentals = foundCustomer.getRentals() ;
        for ( Rental rental: customerRentals ) {
            if ( rental.getVideo().getTitle().equals(videoTitle) && rental.getVideo().isRented() ) {
                rental.returnVideo();
                rental.getVideo().setRented(false);
                break ;
            }
        }
    }

    public List<String> listVideos() {
        List<String> videoListStr = new ArrayList<>();
        for ( Video video: videos ) {
            videoListStr.add("Price code: " + video.getPriceCode() +"\tTitle: " + video.getTitle()) ;
        }
        return videoListStr;
    }

    public List<String> listCustomers() {
        List<String> customerListStr = new ArrayList<>();
        for ( Customer customer: customers ) {
            customerListStr.add("Name: " + customer.getName() +
                    "\tRentals: " + customer.getRentals().size()) ;
            for ( Rental rental: customer.getRentals() ) {
                customerListStr.add("\tTitle: " + rental.getVideo().getTitle()
                        + " \tPrice Code: " + rental.getVideo().getPriceCode()) ;
            }
        }
        return customerListStr;
    }

    public void getCustomerReport(String customerName) {
        Customer foundCustomer = findCustomer(customerName);

        if ( foundCustomer == null ) {
            System.out.println("No customer found") ;
        } else {
            String result = foundCustomer.getReport() ;
            System.out.println(result);
        }
    }

    public void rentVideo(String customerName) {
        Customer foundCustomer = findCustomer(customerName);

        if ( foundCustomer == null ) return ;

        System.out.println("Enter video title to rent: ") ;
        String videoTitle = scanner.next() ;

        Video foundVideo = null ;
        for ( Video video: videos ) {
            if ( video.getTitle().equals(videoTitle) && video.isRented() == false ) {
                foundVideo = video ;
                break ;
            }
        }

        if ( foundVideo == null ) return ;

        Rental rental = new Rental(foundVideo) ;
        foundVideo.setRented(true);

        List<Rental> customerRentals = foundCustomer.getRentals() ;
        customerRentals.add(rental);
        foundCustomer.setRentals(customerRentals);
    }

    public void register(String object) {
        if ( object.equals("customer") ) {
            System.out.println("Enter customer name: ") ;
            String name = scanner.next();
            Customer customer = new Customer(name) ;
            customers.add(customer) ;
        }
        else {
            System.out.println("Enter video title to register: ") ;
            String title = scanner.next() ;

            System.out.println("Enter video type( 1 for VHD, 2 for CD, 3 for DVD ):") ;
            int videoType = scanner.nextInt();

            System.out.println("Enter price code( 1 for Regular, 2 for New Release ):") ;
            int priceCode = scanner.nextInt();

            Date registeredDate = new Date();
            Video video = new Video(title, videoType, priceCode, registeredDate) ;
            videos.add(video) ;
        }
    }

}
