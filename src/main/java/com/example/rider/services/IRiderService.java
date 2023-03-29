package rider.services;

import rider.models.Rider;

public interface IRiderService {
    Boolean register(Rider rider);
    Rider getRider(String userId);
}
