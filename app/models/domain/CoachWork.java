package models.domain;

/**
 * Created by harshitha.suresh on 27/06/2015.
 */
public class CoachWork {
    private final String serialNumber;
    private final boolean faulty;

    public CoachWork(String serialNumber, boolean faulty) {
        this.serialNumber = serialNumber;
        this.faulty = faulty;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public boolean isFaulty() {
        return faulty;
    }

    @Override
    public String toString() {
        return "Wheel{" +
                "serialNumber=" + serialNumber +
                ", faulty=" + faulty +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoachWork coachWork = (CoachWork) o;

        if (faulty != coachWork.faulty) return false;
        if (serialNumber != coachWork.serialNumber) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = serialNumber != null ? serialNumber.hashCode() : 0;
        result = 31 * result + (faulty ? 1 : 0);
        return result;
    }
}
