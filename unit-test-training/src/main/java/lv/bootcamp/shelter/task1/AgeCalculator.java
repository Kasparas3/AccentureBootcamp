package lv.bootcamp.shelter.task1;

/**
 * Simple utility class for age-related calculations.
 * Used in Task 1 for practising basic assertions and AAA pattern.
 */
public class AgeCalculator {

    /**
     * Converts years to months.
     *
     * @param years number of years (must be zero or positive)
     * @return equivalent number of months
     * @throws IllegalArgumentException if years is negative
     */
    public int toMonths(int years) {
        if (years < 0) {
            throw new IllegalArgumentException("Years must not be negative, got: " + years);
        }
        return years * 12;
    }

    /**
     * Calculates approximate "human years" equivalent for a dog.
     * Uses the common simplified formula:
     * - First year = 15 human years
     * - Second year = 9 human years
     * - Each subsequent year = 5 human years
     *
     * @param dogAge the dog's actual age in years
     * @return approximate human-equivalent age
     * @throws IllegalArgumentException if dogAge is negative
     */
    public int dogToHumanYears(int dogAge) {
        if (dogAge < 0) {
            throw new IllegalArgumentException("Dog age must not be negative, got: " + dogAge);
        }
        if (dogAge == 0) {
            return 0;
        }
        if (dogAge == 1) {
            return 15;
        }
        if (dogAge == 2) {
            return 24;
        }
        return 24 + (dogAge - 2) * 5;
    }

    /**
     * Returns true if the animal is considered a baby (age 0).
     */
    public boolean isBaby(int age) {
        return age == 0;
    }
}
