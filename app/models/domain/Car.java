package models.domain;

import models.domain.CoachWork;
import models.domain.Colour;
import models.domain.Engine;
import models.domain.Wheel;

import java.util.List;

/**
 * Created by harshitha.suresh on 27/06/2015.
 */
public class Car {
    private final Long serialNumber;
    private final Engine engine;
    private final CoachWork coachWork;
    private final List<Wheel> wheels;
    private Colour colour;

    public Car(Long serialNumber, Engine engine, CoachWork coachWork, List<Wheel> wheels) {
        if(wheels == null || wheels.size() != 4){
            throw new IllegalArgumentException("Please pass 4 wheels");
        }
        this.serialNumber = serialNumber;
        this.engine = engine;
        this.coachWork = coachWork;
        this.wheels = wheels;
    }

    public Long getSerialNumber() {
        return serialNumber;
    }

    public Engine getEngine() {
        return engine;
    }

    public List<Wheel> getWheels() {
        return wheels;
    }

    public CoachWork getCoachWork() {
        return coachWork;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        if (coachWork != null ? !coachWork.equals(car.coachWork) : car.coachWork != null) return false;
        if (colour != car.colour) return false;
        if (engine != null ? !engine.equals(car.engine) : car.engine != null) return false;
        if (serialNumber != null ? !serialNumber.equals(car.serialNumber) : car.serialNumber != null) return false;
        if (wheels != null ? !wheels.equals(car.wheels) : car.wheels != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = serialNumber != null ? serialNumber.hashCode() : 0;
        result = 31 * result + (engine != null ? engine.hashCode() : 0);
        result = 31 * result + (coachWork != null ? coachWork.hashCode() : 0);
        result = 31 * result + (wheels != null ? wheels.hashCode() : 0);
        result = 31 * result + (colour != null ? colour.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Car{" +
                "serialNumber=" + serialNumber +
                ", engine=" + engine +
                ", coachWork=" + coachWork +
                ", wheels=" + wheels +
                ", colour=" + colour +
                '}';
    }
}