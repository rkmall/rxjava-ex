package observerpattern.javautils;

import java.util.Observer;

public class ObsRunner {

    public static void main(String[] args) {

        WeatherStation weatherStation = new WeatherStation();

        Observer subscriber1 = new WeatherSubscriber(weatherStation);
        Observer subscriber2 = new WeatherSubscriber(weatherStation);
        Observer subscriber3 = new WeatherSubscriber(weatherStation);

        System.out.println(weatherStation.countObservers());
        //System.out.println(weatherStation.hasChanged());

        weatherStation.setMeasurement(20.10f, 34.00f, 12.00f);

        System.out.println(weatherStation.hasChanged());
    }
}
