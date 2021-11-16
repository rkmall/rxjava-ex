package observerpattern.javautils;

import java.util.Observable;

public class WeatherStation extends Observable {

    private float temperature;
    private float humidity;
    private float pressure;

    public WeatherStation() {}

    public void measurementChanged() {
        setChanged();                   // setChanged() will now mark this object has been changed
        System.out.println("Measurement is changed: " + this.hasChanged());
        notifyObservers();              // notifyObservers() will call notifyObservers(null) internally and calls
    }                                   // Observers update() by passing itself from which the observers can pull the data

    public void setMeasurement(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementChanged();           // measurement has changed, notify Observers
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }
}
