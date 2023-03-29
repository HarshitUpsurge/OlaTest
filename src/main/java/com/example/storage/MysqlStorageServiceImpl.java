package storage;

import booking.models.Booking;
import driver.models.Driver;
import rider.models.Rider;
import vehicle.models.Vehicle;
import storage.IStorageService;
import storage.MysqlStorageHelper;
import java.sql.*;
import java.util.*;

public class MysqlStorageServiceImpl implements IStorageService{


    
    private static Connection dbDriver = null;
    private static String url = "jdbc:mysql://localhost:3306/ola";
    private static String userName = "root";
    private static String password = "Harshit";
    public static MysqlStorageHelper mysqlStorageHelper;
    public MysqlStorageServiceImpl()
    { 
        synchronized (MysqlStorageServiceImpl.class) 
        {
            if(dbDriver == null)
            {
                try 
                {
                    dbDriver = DriverManager.getConnection(url, userName, password);
                    mysqlStorageHelper = new MysqlStorageHelper(dbDriver);
                    System.err.println("Connection Succesfull!");
        
                } catch (SQLException e) {
                    System.err.println("Connection failed!");
                    e.printStackTrace();
                }
            }

        }   

    }


    public Boolean saveRider(Rider rider) {
        StringBuffer sb = new StringBuffer();
        sb.append(rider.getCountryCode()).append(rider.getPhoneNumber());
        String riderUniqueId = sb.toString();
        if(!mysqlStorageHelper.findRiderByRiderUniqueId(riderUniqueId).isNull()){
            throw new RuntimeException("Rider already exist in the system");
        }
        mysqlStorageHelper.addRider(rider);
        System.out.println("******* Rider Added Successfully******");
        return true;
    }


    public Boolean saveDriver(Driver driver) {
        StringBuffer sb = new StringBuffer();
        sb.append(driver.getCountryCode()).append(driver.getPhoneNumber());
        String driverUniqueId = sb.toString();
        if(!mysqlStorageHelper.findDriverByDriverUniqueId(driverUniqueId).isNull()){
            throw new RuntimeException("Driver already exist in the system");
        }
        mysqlStorageHelper.addDriver(driver);
        System.out.println("******* Driver added Successfully******");
        return true;
    }

    public Boolean saveVehicle(Vehicle vehicle) {
        if(!mysqlStorageHelper.findVehicleByCarNumber(vehicle.getCarNumber()).isNull()){
            throw new RuntimeException("Vehicle already exist in the system");
        }
        mysqlStorageHelper.addVehicle(vehicle);
        System.out.println("******* Vehicle added successfully ******");
        return true;
    }


    public Boolean updateLocation(Vehicle vehicle) {
        if(mysqlStorageHelper.findVehicleByCarNumber(vehicle.getCarNumber()).isNull()){
            throw new RuntimeException("Vehicle does not exist in the system");
        }
        Vehicle vehicleInDb = mysqlStorageHelper.findVehicleByCarNumber(vehicle.getCarNumber());
        vehicleInDb.setLat(vehicle.getLat());
        vehicleInDb.setLon(vehicle.getLon());
        mysqlStorageHelper.updateVehicleLocation(vehicleInDb);
        System.out.println("******* vehicle Updating successfully******");
        return true;
    }

    public Boolean book(Booking booking) {
        mysqlStorageHelper.saveBooking(booking);
        Rider rider = mysqlStorageHelper.findRiderByUserId(booking.getRiderUserId());
        List<String> bookingHistory = rider.getBookingHistory();
        if(bookingHistory == null){
            bookingHistory = new ArrayList<>();
        }
        bookingHistory.add(booking.getBookingId());
        rider.setBookingHistory(bookingHistory);
        mysqlStorageHelper.addBookingHistory(booking, rider);
        System.out.println("******* Rider history added usccessfully ******");
        return true;
    }


    public Vehicle find(Double lat, Double lon, Double maxDistance) {
        List<String> vehicles = mysqlStorageHelper.getAllVehicle();
        TreeMap<Double, Vehicle> distanceVehicleMap = new TreeMap<>();
        for(String vehicleId : vehicles){
            Vehicle vehicle = mysqlStorageHelper.findVehicleByCarNumber(vehicleId);
            Double distance = Math.sqrt((lon)*(vehicle.getLon()) +(lat)*(vehicle.getLat()));
            if(distance < maxDistance) {
                distanceVehicleMap.put(distance, vehicle);
            }
        }
        return distanceVehicleMap.pollFirstEntry().getValue();
    }


    public List<Booking> rideHistory(String riderUserId) {
        Rider rider = mysqlStorageHelper.findRiderByUserId(riderUserId);
        List<String> riderBookingIdsHistory = mysqlStorageHelper.getBookingHistoryByRiderId(rider);
        List<Booking> bookingHistory = new ArrayList<>();
        for(String bookingId : riderBookingIdsHistory){
            Booking booking = mysqlStorageHelper.findBookingById(bookingId);
            bookingHistory.add(booking);
        }
        return bookingHistory;
    }


    public Boolean endTrip(Long timeStamp, String bookingId) {
        Booking booking = mysqlStorageHelper.findBookingById(bookingId);
        if(booking == null){
            throw new RuntimeException("No trip by this Id");
        }
        if(booking.getStatus() != null){
            throw new RuntimeException("Booking already ended");
        }
        booking.setEndTime(timeStamp);
        booking.setStatus("COMPLETED");
        mysqlStorageHelper.updateBooking(booking);
        return true;
    }

    public Rider getRider(String UserId)
    {
        return mysqlStorageHelper.findRiderByUserId(UserId);
    }

    public Driver getDriver(String UserId)
    {
        return mysqlStorageHelper.findDriverByUserId(UserId);
    }


    
}
