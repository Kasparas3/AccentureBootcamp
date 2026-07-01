# Smart Home Controller

A Java console application that simulates a smart home controller managing multiple device types (lights, thermostats, and locks). The application intentionally contains bugs and missing features that you must implement across four tasks.

## Prerequisites

- Java 21+
- Maven 3.8+

## Running the Application

```bash
mvn compile exec:java -Dexec.mainClass="com.bootcamp.smarthome.Main"
```

## Project Structure

```
src/main/java/com/bootcamp/smarthome/
├── Main.java                    # Entry point and demo scenarios
├── controller/
│   ├── CommandParser.java       # Parses command strings
│   └── HomeController.java      # Manages registered devices
└── device/
    ├── Device.java              # Abstract base class
    ├── SmartLight.java          # Dimmable light bulb
    ├── SmartThermostat.java     # Programmable thermostat
    └── SmartLock.java           # PIN-protected door lock
```

---

## Task 1 — Custom Exception Hierarchy

Create the following exception classes in a new package `com.bootcamp.smarthome.exception`:

| Class | Type | Extends |
|---|---|---|
| `HomeAutomationException` | checked | `Exception` |
| `DeviceOfflineException` | checked | `HomeAutomationException` |
| `InvalidCommandException` | checked | `HomeAutomationException` |
| `DeviceNotFoundException` | unchecked | `RuntimeException` |
| `InvalidValueException` | checked | `HomeAutomationException` |

`InvalidValueException` must provide a constructor with the following signature:

```java
public InvalidValueException(String field, Object value, String constraint)
```

No changes to `Main` are required for this task.

---

## Task 2 — Exception Handling

Add validation and proper exception throwing to the following methods:

- **`SmartLight.setBrightness(int level)`** — throw `InvalidValueException` when `level` is outside `[0, 100]`
- **`SmartThermostat.setTemperature(double temp)`** — throw `InvalidValueException` when `temp` is outside `[10.0, 35.0]`
- **`SmartLock.validatePin(String pin)`** — throw `InvalidCommandException` when the PIN is `null` or does not match
- **`HomeController.sendCommand(String fullCommand)`** — wrap the method body in `try-catch-finally`:
  - Catch `HomeAutomationException`, then throw a **new** `HomeAutomationException` whose message includes the `deviceId` and the original `fullCommand` string (e.g. `"Command '" + fullCommand + "' failed for device '" + deviceId + "'"`) and pass the caught exception as the `cause` argument — do **not** re-throw the original exception unchanged
  - The `finally` block must always print: `Command processing ended for device [id]`

Update `Main` to handle or declare all checked exceptions where needed.

---

## Task 3 — SLF4J + Logback Logging

- Add a `private static final Logger` field to `HomeController` and `SmartLock` — use **SLF4J** (`org.slf4j.Logger` / `org.slf4j.LoggerFactory`): `private static final Logger logger = LoggerFactory.getLogger(ClassName.class);`
- Log the following events in `HomeController`:
  - `DEBUG` — a command is received (include device ID and raw command)
  - `INFO` — a command executed successfully
  - `WARN` — the target device is offline and the command is skipped
  - `ERROR` — an exception is caught during command processing
- **Replace** the corresponding `System.out.println` statements in `HomeController.sendCommand` with these logger calls; remove the old print statements rather than keeping both
- `SmartLock` must log at `ERROR` every failed unlock attempt (security audit trail) — replace the existing `System.out.println("SECURITY ALERT: ...")` line with the logger call
- In `logback.xml`, uncomment the `FILE` appender and register it in the root logger
- **Demonstration:** change the root logger level to `WARN`, run the app, and observe which log lines no longer appear in the output

---

## Task 4 — Debug & Fix Planted Bugs

The codebase contains four deliberate bugs. Use the debugger and stack traces to find and fix each one.

### Stack trace bugs
Run `Main` and read the exception output to locate the exact line:

1. **`NullPointerException`** in `SmartLock.validatePin()` — triggered by scenario 6 (null PIN passed in)
2. **`ArrayIndexOutOfBoundsException`** in `HomeController.findDevice()` — triggered by scenario 7 (array is full, device not found)

Fix bug 1 first; bug 2 only becomes reachable after bug 1 is resolved.

### Logic bugs
The application does not crash, but produces incorrect output. Use breakpoints and the Variables / Watch panel to find the mistake:

3. **`SmartThermostat.setTemperature()`** silently accepts values outside the valid range — triggered by scenario 3 (temperature `99.0` should be rejected)
4. **`CommandParser.extractCommand()`** drops the value part of commands that have one — triggered by scenario 2 (brightness should be set to `80`, not `50`)
