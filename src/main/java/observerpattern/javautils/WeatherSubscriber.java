package observerpattern.javautils;

import java.util.Observable;
import java.util.Observer;

public class WeatherSubscriber implements Observer, DisplayInfo {

    Observable observable;
    private float temperature;
    private float humidity;
    private float pressure;

    public WeatherSubscriber(Observable observable) {
        this.observable = observable;
        observable.addObserver(this);
    }


    @Override
    public void update(Observable obs, Object arg) {
        if(obs instanceof WeatherStation) {
            WeatherStation weatherStation = (WeatherStation) obs;
            this.temperature = weatherStation.getTemperature();
            this.humidity = weatherStation.getHumidity();
            this.pressure = weatherStation.getPressure();
            display();
        }
    }

    @Override
    public void display() {
        System.out.println("Current weather: " +
                temperature + "F degrees " +
                humidity + " humidity " +
                pressure + " pressure ");
    }
}
