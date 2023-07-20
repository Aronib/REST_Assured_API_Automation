import org.apache.commons.configuration.ConfigurationException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class RunTest {
    @Test()
    public void login() throws ConfigurationException, IOException {
        booking booking=new booking();
        booking.callingLoginAPI();
    }

    /*@Test()
    public void bookingLST() throws ConfigurationException, IOException {
        booking booking=new booking();
        booking.bookingList();
    }*/
    @Test()
    public void newBooking() throws ConfigurationException, IOException {
        booking booking=new booking();
        booking.createBooking();
    }
    @Test()
    public void delBooking() throws ConfigurationException, IOException {
        booking booking=new booking();
        booking.deleteBooking();
    }
}