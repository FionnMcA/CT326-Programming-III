import java.util.Random;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class NCTBooking implements NCTBookingSlotWebservice {
    private TestCentre testCentre;
    Random rand = new Random();
    private String regNum;
    private String dateTime;
    private long bookingID;
    private LocalDateTime dateAndTime;
    String regPattern = "\\d{2,3}-[a-zA-Z]{1,2}-\\d{3,5}"; //To check if the reg entered is valid

    public NCTBooking(String regNum, TestCentre testCentre,  LocalDateTime dateAndTime){
        //checks if the date
        if (dateAndTime.isBefore(LocalDateTime.now())) { //check if the user entered a valid date
            throw new IllegalArgumentException("The booking date and time cannot be in the past.");
        }
        if(!Pattern.matches(regPattern, regNum)){  //check if the user entered a valid reg
            throw new IllegalArgumentException("The reg number must have 2 or 3 digits a dash 1 or 2 letters a dash and 3 or 5 digits");
        }
        else {
            this.regNum = regNum;
            this.dateTime = dateTime;
            this.testCentre = testCentre;
            this.bookingID = rand.nextInt(1000000000);
            this.dateAndTime = dateAndTime;
        }
    }

    //Overloaded constructor so the user can create a class without specifying the date and time
    public NCTBooking(String regNum, TestCentre testCentre){
        this.regNum = regNum;
        this.dateTime = dateTime;
        this.testCentre = testCentre;
        this.bookingID = rand.nextInt(100000000);
        this.dateAndTime = getBookingDateTime(testCentre); //call this method to provide the user with a date if they dont provide one
    }

    public LocalDateTime getBookingDateTime(TestCentre testCentre){
        return LocalDateTime.now().plusDays(2); //gives the user a date that's two days from the current date
    }

    //getters and setters as the user can query test centre, reg number and edit the reg number of the booking
    //and as well to check the values of the variables during the tests
    public String getRegNum(){
        return regNum;
    }

    public void setRegNum(String regNum){
        this.regNum = regNum;
    }

    public TestCentre getTestCentre() {
        return testCentre;
    }

    public long getBookingID(){
        return bookingID;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    @Override
    //toString method to return all the booking info
    public String toString() {
        return "Booking ID Number: " + bookingID + "\n" +
                "Registration Number: " + regNum + "\n" +
                "Centre: " + testCentre.getName() + "\n" +
                "Address: " + testCentre.getAddress() + "\n" +
                "Date & Time: On " + dateAndTime;
    }
}
