package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DriveTime extends CommandBase {
    private double speed;
    private long time, startTime;
    Drivetrain drive;

    public DriveTime(Drivetrain drive, double speed, long time) {
        this.speed = speed;
        this.time = time;
        this.drive = drive;
        addRequirements(drive);
    }

    public void initialize() {
        startTime = System.currentTimeMillis();
    }

    public void execute() {
        drive.arcadeDrive(speed, 0);
    }

    public boolean isFinished() {
        return System.currentTimeMillis() - startTime > time;
    }
}