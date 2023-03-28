package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DriveUntilSupplier extends CommandBase {
    private Drivetrain drive;
    private Supplier<Boolean> isDone;
    private double speed;
    private long timeout = 3000;
    private long start;

    public DriveUntilSupplier(Drivetrain drive, Supplier<Boolean> supplier, double speed) {
        this.drive = drive;
        isDone = supplier;
        this.speed = speed;
    }

    public void initialize() {
        start = System.currentTimeMillis();
    }

    public DriveUntilSupplier setTimeout(long timeout) {
        this.timeout = timeout;
        return this;
    }

    public void execute() {
        drive.tankDrive(speed, speed);
    }

    public boolean isFinished() {
        return isDone.get() || System.currentTimeMillis() - start > timeout;
    }
}