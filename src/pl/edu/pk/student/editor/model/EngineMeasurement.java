package pl.edu.pk.student.editor.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EngineMeasurement {
    private final StringProperty time;
    private final StringProperty thrust;


    public EngineMeasurement() {
        this(null, null);
    }

    public EngineMeasurement(String time, String newtons) {
        this.time = new SimpleStringProperty(time);
        this.thrust = new SimpleStringProperty(newtons);
    }

    /**
     * @return the time
     */
    public StringProperty getTimeProperty() {
        return time;
    }

    /**
     * @return the thrust
     */
    public StringProperty getThrustProperty() {
        return thrust;
    }

    /**
     * @param thrust the thrust to set
     */
    public void setThrust(String thrust) {
        this.thrust.set(thrust);
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time.set(time);
    }

    /**
     * @return thrust as String
     */
    public String getThrust() {
        return thrust.get();
    }

    /**
     * @return time as String
     */
    public String getTime() {
        return time.get();
    }
}

