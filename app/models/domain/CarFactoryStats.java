package models.domain;

/**
 * Created by harshitha.suresh on 04/07/2015.
 */
public class CarFactoryStats {
    private Long coachworksCreated;
    private Long wheelsCreated;
    private Long enginesCreated;
    private Long carsCreated;
    private Long faultlessWheels;
    private Long faultlessEngines;
    private Long faultlessCoachworks;
    private Long redCars;
    private Long greenCars;
    private Long blueCars;

    public long getCoachworksCreated() {
        return coachworksCreated;
    }

    public void setCoachworksCreated(long coachworksCreated) {
        this.coachworksCreated = coachworksCreated;
    }

    public long getWheelsCreated() {
        return wheelsCreated;
    }

    public void setWheelsCreated(long wheelsCreated) {
        this.wheelsCreated = wheelsCreated;
    }

    public long getEnginesCreated() {
        return enginesCreated;
    }

    public void setEnginesCreated(long enginesCreated) {
        this.enginesCreated = enginesCreated;
    }

    public long getCarsCreated() {
        return carsCreated;
    }

    public void setCarsCreated(long carsCreated) {
        this.carsCreated = carsCreated;
    }

    public long getFaultlessWheels() {
        return faultlessWheels;
    }

    public void setFaultlessWheels(long faultlessWheels) {
        this.faultlessWheels = faultlessWheels;
    }

    public long getFaultlessEngines() {
        return faultlessEngines;
    }

    public void setFaultlessEngines(long faultlessEngines) {
        this.faultlessEngines = faultlessEngines;
    }

    public long getFaultlessCoachworks() {
        return faultlessCoachworks;
    }

    public void setFaultlessCoachworks(long faultlessCoachworks) {
        this.faultlessCoachworks = faultlessCoachworks;
    }

    public long getRedCars() {
        return redCars;
    }

    public void setRedCars(long redCars) {
        this.redCars = redCars;
    }

    public long getBlueCars() {
        return blueCars;
    }

    public void setBlueCars(long blueCars) {
        this.blueCars = blueCars;
    }

    public long getGreenCars() {
        return greenCars;
    }

    public void setGreenCars(long greenCars) {
        this.greenCars = greenCars;
    }

    public boolean isComplete(){
        return coachworksCreated != null && wheelsCreated != null
                && enginesCreated != null && carsCreated != null
                && faultlessWheels != null && faultlessEngines != null
                && faultlessCoachworks != null && redCars != null
                && greenCars != null && blueCars != null;
    }

    @Override
    public String toString() {
        return "CarFactoryStats{" +
                "coachworksCreated=" + coachworksCreated +
                ", wheelsCreated=" + wheelsCreated +
                ", enginesCreated=" + enginesCreated +
                ", faultlessWheels=" + faultlessWheels +
                ", faultlessEngines=" + faultlessEngines +
                ", faultlessCoachworks=" + faultlessCoachworks +
                ", redCars=" + redCars +
                ", greenCars=" + greenCars +
                ", blueCars=" + blueCars +
                ", carsCreated=" + carsCreated +
                '}';
    }
}
