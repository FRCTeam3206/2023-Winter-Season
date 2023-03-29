package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DriveDistance extends CommandBase {
    double distance, speed;
    Drivetrain drive;

    public DriveDistance(Drivetrain drive, double distance, double speed) {
        this.distance = distance;
        this.speed = speed;
        this.drive = drive;
        addRequirements(drive);
    }

    double origonalLocation;

    public void initialize() {
        origonalLocation = drive.getRawEncoderDistance();
    }

    public void execute() {
        drive.tankDrive(speed, speed);
    }

    public boolean isFinished() {
        return origonalLocation + distance < drive.getRawEncoderDistance();
    }
}
