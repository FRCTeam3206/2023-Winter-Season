package frc.robot.commands;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

public class AlignCommand extends CommandBase {
    Drivetrain drive;
    Translation3d desiredLocation;
    int x, y = -10;

    private void construct(Drivetrain drive, Translation3d desiredLocation) {
        this.drive = drive;
        this.desiredLocation = desiredLocation;
        addRequirements(drive);
    }

    public AlignCommand(Drivetrain drive, Translation3d desiredLocation) {
        construct(drive, desiredLocation);
    }

    public AlignCommand(Drivetrain drive, int x, int y) {
        this.x = x;
        this.y = y;
        construct(drive, new Translation3d());
    }

    public void initialize() {
        if (x == -10)
            return;
        double[] distances = new double[9];
        int i = 0;
        for (double d : distances) {
            distances[i] = Double.MAX_VALUE - 10;
            i++;
        }
        for (i = 1; i <= 3; i++) {
            distances[i] = drive.getPose().getTranslation()
                    .getDistance(Constants.Vision.aprilTags[i].toTranslation2d());
        }
        for (i = 6; i <= 8; i++) {
            distances[i] = drive.getPose().getTranslation()
                    .getDistance(Constants.Vision.aprilTags[i].toTranslation2d());
        }
        i = 0;
        int bestIndex = -1;
        double bestDistance = Double.MAX_VALUE;
        for (double distance : distances) {
            if (distance < bestDistance) {
                bestDistance = distance;
                bestIndex = i;
            }
            i++;
        }
        if (bestIndex > -1) {
            desiredLocation = Constants.Vision.getScoreArea(bestIndex, x, y);
            SmartDashboard.putNumber("Nearest Tag", bestIndex);
            SmartDashboard.putNumber("X", x);
            SmartDashboard.putNumber("Y", y);
        }
    }

    private double clipAngle(double angle) {
        if (angle > Math.PI / 2) {
            return angle - Math.PI;
        } else if (angle < -Math.PI / 2) {
            return angle + Math.PI;
        } else {
            return angle;
        }
    }

    private boolean hasPassedTolerance = false;
    private double turnSpeed = 0;
    private boolean finisher = false;

    @Override
    public void execute() {
        double deltaPosX = desiredLocation.getX() - drive.getPose().getX();
        double deltaPosY = desiredLocation.getY() - drive.getPose().getY();
        double desiredAngle = Math.atan(deltaPosY / deltaPosX);
        double currentAngle = drive.getPose().getRotation().getRadians();
        desiredAngle = clipAngle(desiredAngle);
        currentAngle = clipAngle(currentAngle);
        double deltaTheta = -(currentAngle - desiredAngle);
        // double speed = -Constants.ALIGN_DAMPENER * (deltaTheta);
        // if (deltaTheta < ((4 * 3.14) / 360)) {
        // hasPassedTolerance = true;
        // }
        // if (hasPassedTolerance) {
        // SmartDashboard.putNumber("Tolerance", 1.0);
        // speed = 0;
        // }
        if ((deltaTheta * 180 / Math.PI) > 2.0) {
            turnSpeed = deltaTheta * Constants.ALIGN_DAMPENER;
            if (turnSpeed < Constants.ALIGN_FLOOR) {
                turnSpeed = Constants.ALIGN_FLOOR;
            }
        } else if ((deltaTheta * 180 / Math.PI) < -2.0) {
            turnSpeed = deltaTheta * Constants.ALIGN_DAMPENER;
            if (turnSpeed > -Constants.ALIGN_FLOOR) {
                turnSpeed = -Constants.ALIGN_FLOOR;
            }
        } else {
            turnSpeed = 0;
        }
        drive.arcadeDrive(0, turnSpeed);
        SmartDashboard.putNumber("TurnSpeed", turnSpeed);
        SmartDashboard.putNumber("DX", deltaPosX);
        SmartDashboard.putNumber("DY", deltaPosY);
        SmartDashboard.putNumber("Des Angle", desiredAngle * 180 / 3.14);
        SmartDashboard.putNumber("Current Angle", currentAngle * 180 / 3.14);
        SmartDashboard.putNumber("Angle To Move", deltaTheta * 180 / 3.14);
        // Translation2d difference =
        // desiredLocation.toTranslation2d().minus(drive.getPose().getTranslation());
        // Rotation2d desiredAngle = new Rotation2d(Math.atan(difference.getX() /
        // difference.getY()));
        // double angleDifference =
        // desiredAngle.plus(drive.getPose().getRotation()).getRadians() - Math.PI / 2;
        // SmartDashboard.putNumber("Des Angle", desiredAngle.getDegrees());
        // SmartDashboard.putNumber("Curr Angle",
        // drive.getPose().getRotation().getDegrees());
        // SmartDashboard.putNumber("Diff Angle", angleDifference);
        // drive.arcadeDrive(0, -angleDifference);
    }

    @Override
    public boolean isFinished() {
        return finisher;
    }

    @Override
    public void end(boolean interrupted) {
        this.hasPassedTolerance = false;
    }

}
