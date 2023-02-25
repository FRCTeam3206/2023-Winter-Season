package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DriveUntilSupplier extends CommandBase {
    private Drivetrain drive;
    private Supplier<Boolean> isDone;
    private double speed;

    public DriveUntilSupplier(Drivetrain drive, Supplier<Boolean> supplier, double speed) {
        this.drive = drive;
        isDone = supplier;
        this.speed = speed;
    }

    public void execute() {
        drive.arcadeDrive(speed, 0);
    }

    public boolean isFinished() {
        return isDone.get();
    }
}