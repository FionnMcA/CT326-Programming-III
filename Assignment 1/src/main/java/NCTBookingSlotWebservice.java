import java.time.LocalDateTime;

//An interface that defines a method to provide the user with a date and time (if they don''t enter one) based on the test centre
public interface NCTBookingSlotWebservice {
    public LocalDateTime getBookingDateTime(TestCentre testCentre);
}
