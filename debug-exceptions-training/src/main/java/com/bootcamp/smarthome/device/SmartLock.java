package com.bootcamp.smarthome.device;

/**
 * A PIN-protected smart door lock.
 *
 * The lock can be locked or unlocked via a 4-digit PIN.
 * Every failed unlock attempt is treated as a security event.
 */
public class SmartLock extends Device {

    private boolean isLocked;
    private final String storedPin;

    public SmartLock(String deviceId, String name, boolean isOnline, String pin) {
        super(deviceId, name, isOnline);
        this.isLocked = true;
        this.storedPin = pin;
    }

    // -------------------------------------------------------------------------
    // Device-specific behaviour
    // -------------------------------------------------------------------------

    /**
     * Validates the supplied PIN against the stored PIN.
     */
    public void validatePin(String pin) {
        if (pin.equals(storedPin)) {
            isLocked = false;
            System.out.println(getName() + " unlocked successfully.");
        } else {
            System.out.println("SECURITY ALERT: Incorrect PIN entered for " + getName() + ".");
        }
    }

    public void lock() {
        isLocked = true;
        System.out.println(getName() + " locked.");
    }

    @Override
    public void executeCommand(String command) {
        if (command.startsWith("UNLOCK")) {
            String[] parts = command.split(" ");
            String pin = (parts.length > 1) ? parts[1] : null;
            validatePin(pin);
        } else if (command.equals("LOCK")) {
            lock();
        } else if (command.equals("TURN_ON")) {
            turnOn();
        } else if (command.equals("TURN_OFF")) {
            turnOff();
        } else {
            System.out.println("Unknown command for SmartLock '" + getName() + "': " + command);
        }
    }

    // -------------------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------------------

    public boolean isLocked() {
        return isLocked;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | locked=%b", isLocked);
    }
}
