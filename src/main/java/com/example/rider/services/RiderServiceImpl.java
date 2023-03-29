package rider.services;
import storage.MysqlStorageServiceImpl;
import rider.models.Rider;
import storage.IStorageService;

public class RiderServiceImpl implements  IRiderService{
    private IStorageService storageService;

    public RiderServiceImpl(IStorageService storageService) {
        this.storageService = storageService;
    }

    public Boolean register(Rider rider) {
        this.storageService.saveRider(rider);
        return true;
    }

    public Rider getRider(String phoneNumberWithCountryCode)
    {
        return this.storageService.getRider(phoneNumberWithCountryCode);
    }
    
}
