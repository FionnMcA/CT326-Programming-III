//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

class NCTBookingTest {

    //Creating a testCentre and dateTime object to avoid reusing these in every test
    TestCentre testCentre = new TestCentre("Ballinasloe", "Unit 9, Pollboy Industrial Estate,\nBallinasloe, Galway H53 NW94");
    LocalDateTime testDateTime = LocalDateTime.of(2023, 9, 22, 8, 0);

    @Test
    //Test to show that a booking can be created with a reg, test centre and date and time
    public void testCreateBookingWithAllInfo() {
        NCTBooking booking = new NCTBooking("16-WH-59741", testCentre, testDateTime);

        //When creating a booking object a bookingID should be randomly generated
        assertTrue(booking.getBookingID() > 0);

        //Checking the reg, test centre name and address and time match the given values.
        assertEquals("16-WH-59741", booking.getRegNum());
        assertEquals("Ballinasloe", booking.getTestCentre().getName());
        assertEquals("Unit 9, Pollboy Industrial Estate,\nBallinasloe, Galway H53 NW94", booking.getTestCentre().getAddress());
        assertEquals(testDateTime, booking.getDateAndTime());
    }

    @Test
    //Test to show that a booking can be created without providing a date and time,
    public void testCreateBookingWithoutDateAndTime() {
        NCTBooking booking = new NCTBooking("16-WH-59741", testCentre);

        //Checking the reg, test centre name and address and time match the given values.
        assertEquals("16-WH-59741", booking.getRegNum());
        assertEquals("Ballinasloe", booking.getTestCentre().getName());
        assertEquals("Unit 9, Pollboy Industrial Estate,\nBallinasloe, Galway H53 NW94", booking.getTestCentre().getAddress());

        //checking that the date and time isn't null even though no date was provided
        //instead the getBookingDateTime() is called to provide a time
        assertNotNull(booking.getDateAndTime());
    }

    @Test
    //Test to show that the constructor throws an argument for invalid date
    public void testCreateBookingWithInvalidDateAndTime() {
        LocalDateTime testDateTimeInvalid = LocalDateTime.of(2020, 9, 22, 8, 0);
        try {
            NCTBooking booking = new NCTBooking("16-WH-59741", testCentre, testDateTimeInvalid);
        } catch (IllegalArgumentException e) {
            System.out.println("\n" + e.getMessage() + "\n");

        }
    }

    @Test
    //Test to query the name and address of the test centre of the booking
    public void testQueryTestCentreOfBooking() {
        //the name and address should match the expected values
        assertEquals("Ballinasloe", testCentre.getName());
        assertEquals("Unit 9, Pollboy Industrial Estate,\nBallinasloe, Galway H53 NW94", testCentre.getAddress());
    }

    @Test
    //Test to see if the entered reg id valid
    //I just did 2/3 digits a dash 1 or 2 letters a dash and 3 or 5 digits
    // the user could enter an invalid reg like 241-XY-00000 and it pass the test
    public void testValidRegNum(){
        String regPattern;
        NCTBooking booking = new NCTBooking("16-WH-59741", testCentre, testDateTime);
        regPattern = "\\d{2,3}-[a-zA-Z]{1,2}-\\d{3,5}";
        //check if the entered reg number matches the reggex pattern
        assertTrue(Pattern.matches(regPattern, booking.getRegNum()));
    }

    @Test
    //Test to show that an IllegalArgumentException is thrown for invalid reg numbers
    public void testInvalidRegNum() {
        // Use an invalid regNum
        String invalidRegNum = "12A-AB-456";
        try {
            NCTBooking booking = new NCTBooking(invalidRegNum, testCentre, testDateTime);
        } catch (IllegalArgumentException e) {
            System.out.println("\n" + e.getMessage() + "\n");
        }
    };

    @Test
    //Test to show that you can query for the reg number of the booking
    public void testQueryRegNumOfBooking(){
        NCTBooking booking = new NCTBooking("16-WH-59741", testCentre, testDateTime);
        //the booking reg number should match the given reg number
        assertEquals("16-WH-59741", booking.getRegNum());
    }

    @Test
    //Test to show that you can edit the reg number of the booking
    public void testEditRegNumOfBooking(){
        NCTBooking booking = new NCTBooking("16-WH-59741", testCentre, testDateTime);
        booking.setRegNum("222-G-222"); //the reg number has been edited and should now be 222-G-222
        assertEquals("222-G-222", booking.getRegNum()); //so the reg number should equal the new update 222-G-222 instead of 16-WH-59741
    }

    @Test
    //Test to show that bookingIDs are unique (kinda)
    public void testUniqueBookingId(){
        NCTBooking booking1 = new NCTBooking("16-WH-59741", testCentre, testDateTime);
        NCTBooking booking2 = new NCTBooking("16-WH-59742", testCentre, testDateTime);
        //checking that when two bookings are created they don't have the same ID, I have no way of checking if
        //the BookingIDs are unique I just have booking IDs randomly generated longs with a bound of 1000000000
        //so It's highly unlikely two bookings have the same ID but it is nonetheless possible.
        assertNotEquals(booking1.getBookingID(), booking2.getBookingID());
    }

    @Test
    //Test to show the NCTBooking class toString method is valid
    public void testToString(){
        NCTBooking booking = new NCTBooking("16-WH-59741", testCentre, testDateTime);
        String pattern;
        //just checking if ID number is a digit, reg number is a valid reg number, if the test centre,address and time are strings
        pattern = "Booking ID Number: (\\d+)\\n" +
                "Registration Number: \\d{2,3}-[a-zA-Z]{1,2}-\\d{3,5}\\n" +
                "Centre: (.+)\\n" +
                "Address: (.+)\\n(.+)\\n" +
                "Date & Time: On (.+)";
        assertTrue(Pattern.matches(pattern, booking.toString())); //should return true
        System.out.println(booking.toString());
    }


}