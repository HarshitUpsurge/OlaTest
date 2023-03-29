package booking.services;

import com.example.App;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import booking.models.Booking;
import booking.service.BookingServiceImpl;
import booking.service.IBookingService;
import driver.models.Driver;
import driver.services.DriverServiceImpl;
import driver.services.IDriverService;
import rider.models.Rider;
import rider.services.IRiderService;
import rider.services.RiderServiceImpl;
import storage.IStorageService;
import storage.StorageFactory;
import vehicle.models.Vehicle;
import vehicle.services.IVehicleService;
import vehicle.services.VehicleServiceImpl;

public class BookingAPI extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        if(App.bookingService == null)
        {
            System.out.println("Null booking Service");
            return;
        }
        String Userid = request.getParameter("rideUserId");
        String lat = request.getParameter("lat");
        String longp = request.getParameter("long");
        Double lati =  Double.parseDouble(lat);
        Double longi =  Double.parseDouble(longp);
        Booking booking  = App.bookingService.book(Userid,lati,longi);
        response.setContentType("application/json");
        response.getWriter().println("{ \"status\": \"Success\"}" + booking.getBookingId());
    
    }

    
    
}