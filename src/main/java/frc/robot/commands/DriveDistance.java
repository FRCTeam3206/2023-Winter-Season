package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DriveDistance extends CommandBase {
    double distance, speed;
    Drivetrain drive;
    boolean opposite;

    public DriveDistance(Drivetrain drive, double distance, double speed) {
        this.distance = distance;
        this.speed = speed;
        this.drive = drive;
        if (distance < 0)
            opposite = true;
        addRequirements(drive);
    }

    public DriveDistance opposite() {
        this.opposite = true;
        return this;
    }

    double origonalLocation;

    public void initialize() {
        origonalLocation = drive.getRawEncoderDistance();
    }

    public void execute() {
        drive.tankDrive(speed, speed);
    }

    public boolean isFinished() {
        SmartDashboard.putNumber("drive_location", drive.getRawEncoderDistance());
        SmartDashboard.putNumber("des location", origonalLocation + distance);
        if (!opposite)
            return origonalLocation + distance < drive.getRawEncoderDistance();
        else
            return origonalLocation + distance > drive.getRawEncoderDistance();
    }
}
