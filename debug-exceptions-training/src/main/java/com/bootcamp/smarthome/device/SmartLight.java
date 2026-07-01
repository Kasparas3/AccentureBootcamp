package com.bootcamp.smarthome.device;

/**
 * A dimmable smart light bulb.
 *
 * Supports brightness control in the range [0, 100] where
 * 0 = fully off (dimmed) and 100 = fully bright.
 */
public class SmartLight extends Device {

    /** Brightness level. Valid range: 0–100 inclusive. */
    private int brightness;

    public SmartLight(String deviceId, String name, boolean isOnline) {
        super(deviceId, name, isOnline);
        this.brightness = 50;
    }

    // -------------------------------------------------------------------------
    // Device-specific behaviour
    // -------------------------------------------------------------------------

    /**
     * Sets the brightness of this light.
     *
     * Valid range: 0–100 inclusive.
     */
    public void setBrightness(int level) {
        this.brightness = level;
        System.out.println(getName() + " brightness set to " + level + "%");
    }

    @Override
    public void executeCommand(String command) {
        if (command.startsWith("SET_BRIGHTNESS")) {
            String[] parts = command.split(" ");
            int level = (parts.length > 1) ? Integer.parseInt(parts[1]) : 50;
            setBrightness(level);
        } else if (command.equals("TURN_ON")) {
            turnOn();
        } else if (command.equals("TURN_OFF")) {
            turnOff();
        } else {
            System.out.println("Unknown command for SmartLight '" + getName() + "': " + command);
        }
    }

    // -------------------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------------------

    public int getBrightness() {
        return brightness;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | brightness=%3d%%", brightness);
    }
}
