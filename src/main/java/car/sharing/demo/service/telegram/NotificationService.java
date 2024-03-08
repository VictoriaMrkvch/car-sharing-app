package car.sharing.demo.service.telegram;

import car.sharing.demo.model.Rental;

public interface NotificationService {
    void sendNotification(String message);

    String buildMessage(Rental rental);

    String buildMessageForOverdueRentals(Rental rental);
}
