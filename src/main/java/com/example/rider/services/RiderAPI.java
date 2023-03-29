package rider.services;
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

public class RiderAPI extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String CountryCode = request.getParameter("countryCode");
        String PhoneNumber = request.getParameter("phoneNumber");  

        String UserId = CountryCode + PhoneNumber;

        Rider rider = App.riderService.getRider(UserId);
        response.getWriter().println("{ \"status\": \"Success\"}");
        
        if(rider == null)
        {
             response.getWriter().println("{ \"status\": \"Failed\"}");
        }
        else
        {
             response.getWriter().println("{ \"status\": \"Failed\"}" +"Welcome "+ rider.getName());
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        if(App.riderService == null)
        {
            System.out.println("Null rider Service");
            return;
        }

        String name = request.getParameter("name");
        String CountryCode = request.getParameter("countryCode");
        String PhoneNumber = request.getParameter("phoneNumber");

        Rider rider = new Rider();
        rider.setName(name);
        rider.setCountryCode(CountryCode);
        rider.setPhoneNumber(PhoneNumber);
        
        Boolean isSuccess = App.riderService.register(rider);

        response.setContentType("application/json");

        if(isSuccess)
        response.getWriter().println("{ \"status\": \"Success\"}");
        else   response.getWriter().println("{ \"status\": \"Failed\"}");
    
    }

    
    
}