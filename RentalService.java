import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RentalService {
    private List<Customer> customers = new ArrayList<Customer>() ;
    private List<Rental> rentals = new ArrayList<Rental>();

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
        if ( foundCustomer == null ) return null;
        return foundCustomer;
    }

    private void printCustomer(List<String> customerListStr, Customer customer) {
        customerListStr.add("Name: " + customer.getName() +
                "\tRentals: " + customer.getRentals().size()) ;
        for ( Rental rental: customer.getRentals() ) {
            customerListStr.add("\tTitle: " + rental.getVideo().getTitle()
                    + " \tPrice Code: " + rental.getVideo().getPriceCode()) ;
        }
    }

    public List<String> clearRentals(String customerName) {
        Customer foundCustomer = findCustomer(customerName);

        List<String> rentalListStr = new ArrayList<>();
        if ( foundCustomer == null ) {
            rentalListStr.add("No customer found") ;
        } else {
            printCustomer(rentalListStr, foundCustomer);
            List<Rental> rentals = new ArrayList<Rental>() ;
            foundCustomer.setRentals(rentals);
        }
        return rentalListStr;
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
            printCustomer(customerListStr, customer);
        }
        return customerListStr;
    }

    public String getCustomerReport(String customerName) {
        Customer foundCustomer = findCustomer(customerName);

        if ( foundCustomer == null ) {
            return "No customer found" ;
        } else {
            return foundCustomer.getReport() ;
        }
    }

    public void rentVideo(String customerName, String videoTitle) {
        Customer foundCustomer = findCustomer(customerName);
        if ( foundCustomer == null ) return ;

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

    public void returnVideo(String customerName, String videoTitle) {
        Customer foundCustomer = findCustomer(customerName);
        if ( foundCustomer == null ) return ;

        List<Rental> customerRentals = foundCustomer.getRentals() ;
        for ( Rental rental: customerRentals ) {
            if ( rental.getVideo().getTitle().equals(videoTitle) && rental.getVideo().isRented() ) {
                rental.returnVideo();
                rental.getVideo().setRented(false);
                break ;
            }
        }
    }

    public void registerCustomer(String name) {
        Customer customer = new Customer(name) ;
        customers.add(customer) ;
    }

    public void registerVideo(String title, int videoType, int priceCode) {
        Date registeredDate = new Date();
        Video video = new Video(title, videoType, priceCode, registeredDate) ;
        videos.add(video) ;
    }


    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public void addRental(Rental rental) {
        rentals.add(rental);
    }


    public String getReport() {
        String result = "Customer Report for " + getName() + "\n";

        List<Rental> rentals = getRentals();

        double totalCharge = 0;
        int totalPoint = 0;

        for (Rental each : rentals) {
            double eachCharge = 0;
            int eachPoint = 0 ;
            int daysRented = 0;

            if (each.getStatus() == 1) { // returned Video
                long diff = each.getReturnDate().getTime() - each.getRentDate().getTime();
                daysRented = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
            } else { // not yet returned
                long diff = new Date().getTime() - each.getRentDate().getTime();
                daysRented = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
            }

            switch (each.getVideo().getPriceCode()) {
                case Video.REGULAR:
                    eachCharge += 2;
                    if (daysRented > 2)
                        eachCharge += (daysRented - 2) * 1.5;
                    break;
                case Video.NEW_RELEASE:
                    eachCharge = daysRented * 3;
                    break;
            }

            eachPoint++;

            if ((each.getVideo().getPriceCode() == Video.NEW_RELEASE) )
                eachPoint++;

            if ( daysRented > each.getDaysRentedLimit() )
                eachPoint -= Math.min(eachPoint, each.getVideo().getLateReturnPointPenalty()) ;

            result += "\t" + each.getVideo().getTitle() + "\tDays rented: " + daysRented + "\tCharge: " + eachCharge
                    + "\tPoint: " + eachPoint + "\n";

            totalCharge += eachCharge;

            totalPoint += eachPoint ;
        }

        result += "Total charge: " + totalCharge + "\tTotal Point:" + totalPoint + "\n";


        if ( totalPoint >= 10 ) {
            System.out.println("Congrat! You earned one free coupon");
        }
        if ( totalPoint >= 30 ) {
            System.out.println("Congrat! You earned two free coupon");
        }
        return result ;
    }
}
