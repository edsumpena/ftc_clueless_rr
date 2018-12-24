package org.firstinspires.ftc.teamcode.autonomous.parameters;

public class Parameters {
    public StartingPosition startingPosition = StartingPosition.RED_FACING_DEPOT;
    //TODO vision
    public Mineral mineralConfiguration = Mineral.CENTER;

    @Override
    public String toString() {
        return startingPosition.name() + mineralConfiguration.name();
    }
}