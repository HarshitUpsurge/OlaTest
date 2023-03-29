package com.example;

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
import booking.services.BookingAPI;
import rider.services.RiderAPI;
import driver.services.DriverAPI;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;

import java.util.List;
import rider.models.Rider;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {

    private static StorageFactory storageFactory;

    static {
        try {
            storageFactory = new StorageFactory();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static IStorageService storageService = StorageFactory.getStorageInstance("Mysql");
    //private static IStorageService storageService = StorageFactory.getStorageInstance("InMemory"); 
    public static IRiderService riderService = new RiderServiceImpl(storageService);
    public static IDriverService driverService = new DriverServiceImpl(storageService);
    public static IVehicleService vehicleService = new VehicleServiceImpl(storageService);
    public static IBookingService bookingService = new BookingServiceImpl(vehicleService, storageService);
   
    public static void start() throws Exception {
        Server server = new Server(8090);
//        ServerConnector connector = new ServerConnector(server);
//        connector.setPort(8090);
//        server.setConnectors(new Connector[] {connector});
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(BookingAPI.class, "/booking/*");
        handler.addServletWithMapping(RiderAPI.class, "/rider/*");
        handler.addServletWithMapping(DriverAPI.class, "/driver/*");
        server.start();
        server.join();
    }
    public static void main(String args[]) throws Exception
    {
        start();

        if(bookingService == null) System.out.println("pplpp");
        // Rider rider = new Rider();
        // rider.setName("harsh13443442");
        // rider.setCountryCode("+91323332243");
        // rider.setPhoneNumber("910333323432");
        // riderService.register(rider);


        // Driver driver = new Driver();
        // driver.setName("harsh Driver");
        // driver.setCountryCode("+91");
        // driver.setPhoneNumber("9431");
        // driverService.register(driver);

        // Vehicle vehicle = new Vehicle();
        // vehicle.setCarNumber("KA01HK");
        // vehicle.setLat(1D);
        // vehicle.setLon(1D);
        // vehicleService.registerVehicle(vehicle);

        // vehicle.setLat(2D);
        // vehicle.setLon(2D);
        // vehicleService.updateLocation(vehicle);

       // bookingService.book("+91323332243910333323432", 1D, 2D);

        // List<Booking> bookingHistory = bookingService.history("+91323332243910333323432");
        // System.out.println("bookingHistory"+bookingHistory);

        

        

    }
}
