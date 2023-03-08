package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import java.util.Random;

import static ru.netology.geo.GeoServiceImpl.*;

class GeoServiceImplTest {
    GeoService geoService = new GeoServiceImpl();

    @Test
    void byIpRussian() {
        Random r = new Random();
        String ipAddress = "172." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256);
        Location location = new Location("Moscow", Country.RUSSIA, null, 0);
        Assertions.assertEquals(location, geoService.byIp(ipAddress));
    }

    @Test
    void byIpUsa() {
        Random r = new Random();
        String ipAddress = "96." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256);
        Location location = new Location("New York", Country.USA, null, 0);
        Assertions.assertEquals(location, geoService.byIp(ipAddress));
    }

    @Test
    void byIpMoscow() {
        Location location = new Location("Moscow", Country.RUSSIA, "Lenina", 15);
        Assertions.assertEquals(location, geoService.byIp(MOSCOW_IP));
    }

    @Test
    void byIpNewYork() {
        Location location = new Location("New York", Country.USA, " 10th Avenue", 32);
        Assertions.assertEquals(location, geoService.byIp(NEW_YORK_IP));
    }

    @Test
    void byIpLocalhost() {
        Location location = new Location(null, null, null, 0);
        Assertions.assertEquals(location, geoService.byIp(LOCALHOST));
    }

    @Test
    void byCoordinates() {
        Random r = new Random();
        RuntimeException exception = Assertions.assertThrowsExactly(RuntimeException.class, () -> {
            geoService.byCoordinates(r.nextDouble(), r.nextDouble());
        });
        Assertions.assertEquals("Not implemented", exception.getMessage());
    }
}