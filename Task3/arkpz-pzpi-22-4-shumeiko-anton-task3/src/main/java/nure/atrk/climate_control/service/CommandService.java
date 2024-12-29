package nure.atrk.climate_control.service;

import nure.atrk.climate_control.entity.ClimateSystem;
import nure.atrk.climate_control.entity.Command;
import nure.atrk.climate_control.entity.Device;
import nure.atrk.climate_control.entity.User;
import nure.atrk.climate_control.repository.CommandRepository;
import nure.atrk.climate_control.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CommandService {
    @Autowired
    private CommandRepository commandRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private JavaMailSender mailSender;

    public void adjustDevice(ClimateSystem system, String type, double difference) {
        String modifiedType = type;
        if (difference > 0) {
            modifiedType += "_up";
        } else {
            modifiedType += "_down";
        }
        List<Device> devices = deviceRepository.findAllBySystemIdAndType(system.getSystemId(), modifiedType);
        System.out.println("Devices adjusted");

        if (Math.abs(difference) < 1) {
            for (Device device : devices) {
                if (device.getMode().equals("eco")) {
                    Command command = new Command();
                    command.setDeviceId(device.getDeviceId());
                    command.setValue(0);
                    command.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                    commandRepository.save(command);
                }
            }
            return;
        }

        System.out.println("Adjusting devices");

        for (Device device : devices) {
            System.out.println("Adjusting device: " + device.getDeviceId());
            double currentPower = device.getPower();
            double adjustmentFactor = 10;
            double newPower = currentPower + adjustmentFactor * Math.abs(difference);

            newPower = Math.max(0, Math.min(100, newPower));
            System.out.println("New power: " + newPower);

            Command command = new Command();
            command.setDeviceId(device.getDeviceId());
            command.setValue(newPower);
            command.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            commandRepository.save(command);

            if (newPower == 100) {
                System.out.println("Sending notification");
                sendNotificationToUsers(system.getSystemId(), device);
            }
        }

        modifiedType = type;
        if (difference > 0) {
            modifiedType += "_down";
        } else {
            modifiedType += "_up";
        }

        List<Device> oppositeDevices = deviceRepository.findAllBySystemIdAndType(system.getSystemId(), modifiedType);

        for (Device device : oppositeDevices) {
            Command command = new Command();
            command.setDeviceId(device.getDeviceId());
            command.setValue(0);
            command.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            commandRepository.save(command);
        }
    }

    private void sendNotificationToUsers(int systemId, Device device) {
        System.out.println(systemId);
        List<User> users = systemUserService.getUsersBySystemId(systemId);
        for (User user : users) {
            System.out.println("Sending email to " + user.getEmail());
            sendEmail(user.getEmail(), device.getDeviceId());
        }
    }

    private void sendEmail(String to, int deviceId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("shumejko.sasha@gmail.com");
        message.setTo(to);
        message.setSubject("Device Load Notification");
        message.setText("The device with ID " + deviceId + " has reached 100% load.");
        mailSender.send(message);
    }
}
