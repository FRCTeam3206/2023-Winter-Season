package frc.robot.commands;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class AlignCommand extends CommandBase {
    Drivetrain drive;
    Translation3d desiredLocation;

    public AlignCommand(Drivetrain drive, Translation3d desiredLocation) {
        this.drive = drive;
        this.desiredLocation = desiredLocation;
        addRequirements(drive);
    }

    @Override
    public void execute() {
        Translation2d difference = desiredLocation.toTranslation2d().minus(drive.getPose().getTranslation());
        Rotation2d desiredAngle = new Rotation2d(Math.atan(difference.getX() / difference.getY()));
        double angleDifference = desiredAngle.plus(drive.getPose().getRotation()).getRadians() - Math.PI / 2;
        SmartDashboard.putNumber("Des Angle", desiredAngle.getDegrees());
        SmartDashboard.putNumber("Curr Angle", drive.getPose().getRotation().getDegrees());
        SmartDashboard.putNumber("Diff Angle", angleDifference);
        drive.arcadeDrive(0, -angleDifference);
    }
}
