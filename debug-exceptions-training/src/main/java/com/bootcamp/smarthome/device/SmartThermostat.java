package com.bootcamp.smarthome.device;

/**
 * A programmable smart thermostat.
 *
 * Controls the target temperature in Celsius.
 * The valid operating range is 10.0 °C to 35.0 °C (inclusive).
 */
public class SmartThermostat extends Device {

    /** Current target temperature in Celsius. Valid range: 10.0–35.0 inclusive. */
    private double temperature;

    public SmartThermostat(String deviceId, String name, boolean isOnline) {
        super(deviceId, name, isOnline);
        this.temperature = 20.0;
    }

    // -------------------------------------------------------------------------
    // Device-specific behaviour
    // -------------------------------------------------------------------------

    /**
     * Sets the target temperature in Celsius.
     *
     * Valid range: 10.0–35.0 inclusive.
     */
    public void setTemperature(double temp) {
        if (temp < 10.0 && temp > 35.0) {
            System.out.println("Invalid temperature: " + temp + ". Must be between 10.0 and 35.0.");
            return;
        }
        this.temperature = temp;
        System.out.println(getName() + " temperature set to " + temp + " °C");
    }

    @Override
    public void executeCommand(String command) {
        if (command.startsWith("SET_TEMP")) {
            String[] parts = command.split(" ");
            double temp = (parts.length > 1) ? Double.parseDouble(parts[1]) : 20.0;
            setTemperature(temp);
        } else if (command.equals("TURN_ON")) {
            turnOn();
        } else if (command.equals("TURN_OFF")) {
            turnOff();
        } else {
            System.out.println("Unknown command for SmartThermostat '" + getName() + "': " + command);
        }
    }

    // -------------------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------------------

    public double getTemperature() {
        return temperature;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | temp=%.1f °C", temperature);
    }
}
