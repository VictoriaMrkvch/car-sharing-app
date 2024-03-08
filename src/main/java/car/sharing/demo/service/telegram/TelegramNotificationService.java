package car.sharing.demo.service.telegram;

import car.sharing.demo.model.MyTelegramBot;
import car.sharing.demo.model.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelegramNotificationService implements NotificationService {
    private final MyTelegramBot telegramBot;

    @Autowired
    public TelegramNotificationService(MyTelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void sendNotification(String message) {
        telegramBot.sendMessage(message);
    }

    @Override
    public String buildMessage(Rental rental) {
        return "New rental added: car id = " + rental.getCar().getId()
                + ", user id = " + rental.getUser().getId();
    }

    @Override
    public String buildMessageForOverdueRentals(Rental rental) {
        return "Rental ID: " + rental.getId() + "\n"
                + "Customer: " + rental.getUser().getId() + "\n"
                + "Car: " + rental.getCar().getId() + "\n"
                + "Return Date: " + rental.getReturnDate();
    }
}
