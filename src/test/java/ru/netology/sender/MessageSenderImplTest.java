package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.sender.MessageSenderImpl.IP_ADDRESS_HEADER;

class MessageSenderImplTest {
    GeoService geoServiceMock = Mockito.mock(GeoService.class);
    LocalizationService localizationServiceMock = Mockito.mock(LocalizationService.class);
    MessageSender messageSender = new MessageSenderImpl(geoServiceMock, localizationServiceMock);

    @Test
    void sendRussianIp() {
        Random r = new Random();
        String ipAddress = "172." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256);
        Mockito.when(geoServiceMock.byIp(ipAddress)).thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
        Mockito.when(localizationServiceMock.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        Map<String, String> headers = Map.of(IP_ADDRESS_HEADER, ipAddress);
        Assertions.assertEquals("Добро пожаловать", messageSender.send(headers));
    }

    @Test
    void sendUsaIp() {
        Random r = new Random();
        String ipAddress = "96." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256);
        Mockito.when(geoServiceMock.byIp(ipAddress)).thenReturn(new Location("New York", Country.USA, null, 0));
        Mockito.when(localizationServiceMock.locale(Country.USA)).thenReturn("Welcome");
        Map<String, String> headers = Map.of(IP_ADDRESS_HEADER, ipAddress);
        Assertions.assertEquals("Welcome", messageSender.send(headers));
    }
}