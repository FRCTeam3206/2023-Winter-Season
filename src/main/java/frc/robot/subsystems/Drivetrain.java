package frc.robot.subsystems;

import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CANIDs;

import static frc.robot.Constants.*;

public class Drivetrain extends SubsystemBase {
    CANSparkMax frontLeftDrive = new CANSparkMax(CANIDs.FLD, MotorType.kBrushless);
    CANSparkMax frontRightDrive = new CANSparkMax(CANIDs.FRD, MotorType.kBrushless);
    CANSparkMax rearLeftDrive = new CANSparkMax(CANIDs.RLD, MotorType.kBrushless);
    CANSparkMax rearRightDrive = new CANSparkMax(CANIDs.RRD, MotorType.kBrushless);
    CANCoder leftEncoder = new CANCoder(CANIDs.LEFT_ENCODER);
    CANCoder rightEncoder = new CANCoder(CANIDs.LEFT_ENCODER);
    DifferentialDrive drive = new DifferentialDrive(frontLeftDrive, frontRightDrive);
    boolean flipped = false;
    Gyro gyro = new Gyro();
    IntegratedOdometry odometry = new IntegratedOdometry(gyro, rightEncoder, leftEncoder);
    Pose2d pose = new Pose2d();
    Solenoid shifter = new Solenoid(PneumaticsModuleType.REVPH, Ports.SOLENOID_SHIFTER);

    public Drivetrain() {
        frontLeftDrive.restoreFactoryDefaults();
        frontRightDrive.restoreFactoryDefaults();
        rearLeftDrive.restoreFactoryDefaults();
        rearRightDrive.restoreFactoryDefaults();

        leftEncoder.setPosition(0);
        rightEncoder.setPosition(0);

        frontRightDrive.setInverted(true);
        rearLeftDrive.follow(frontLeftDrive);
        rearRightDrive.follow(frontRightDrive);
    }

    public void calibrateGyro() {
        gyro.raw_gyro.calibrate();
    }

    public void arcadeDrive(double xaxisSpeed, double zaxisRotate) {
        drive.arcadeDrive(xaxisSpeed, zaxisRotate);
    }

    public void tankDrive(double left_speed, double right_speed) {
        drive.tankDrive(left_speed, right_speed);
    }

    public void flip() {
        flipped = !flipped;
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    public void setShift(boolean shift) {
        shifter.set(shift);
    }

    public void reset_position() {
        leftEncoder.setPosition(0);
        rightEncoder.setPosition(0);
    }

    public double pitch() {
        return gyro.getRawGyroAngleZ();
    }

    public Pose2d getPose() {
        return pose;
    }

    @Override
    public void periodic() {
        gyro.periodic();
        pose = odometry.update();
    }
}
