package frc.robot.subsystems;

import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.SensorTimeBase;
import com.ctre.phoenix.sensors.WPI_CANCoder;
import com.revrobotics.CANSparkMax;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CANIDs;

import static frc.robot.Constants.*;

public class Drivetrain extends SubsystemBase {
    CANSparkMax frontLeftDrive = new CANSparkMax(CANIDs.FLD, MotorType.kBrushless);
    CANSparkMax frontRightDrive = new CANSparkMax(CANIDs.FRD, MotorType.kBrushless);
    CANSparkMax rearLeftDrive = new CANSparkMax(CANIDs.RLD, MotorType.kBrushless);
    CANSparkMax rearRightDrive = new CANSparkMax(CANIDs.RRD, MotorType.kBrushless);
    // Probably a better way to do this, but we do not know it, so here our solution
    // lies :)
    WPI_CANCoder leftEncoder = new WPI_CANCoder(CANIDs.LEFT_ENCODER);
    WPI_CANCoder rightEncoder = new WPI_CANCoder(CANIDs.RIGHT_ENCODER);
    DifferentialDrive drive = new DifferentialDrive(frontLeftDrive, frontRightDrive);
    boolean flipped = false;
    Gyro gyro = new Gyro();
    IntegratedOdometry odometry = new IntegratedOdometry(gyro, rightEncoder, leftEncoder);
    Pose2d pose = new Pose2d();
    // MAKE SURE THIS IS RIGHT
    Solenoid shifter = new Solenoid(PneumaticsModuleType.CTREPCM, Ports.SOLENOID_SHIFTER);

    SlewRateLimiter rightLimiter = new SlewRateLimiter(ACCEL_LIMIT_K, -ACCEL_LIMIT_K, 0);
    SlewRateLimiter leftLimiter = new SlewRateLimiter(ACCEL_LIMIT_K, -ACCEL_LIMIT_K, 0);

    public Drivetrain() {
        frontLeftDrive.restoreFactoryDefaults();
        frontRightDrive.restoreFactoryDefaults();
        rearLeftDrive.restoreFactoryDefaults();
        rearRightDrive.restoreFactoryDefaults();
        CANCoderConfiguration config = new CANCoderConfiguration();
        config.sensorCoefficient = ENCODER_RATIO_K;
        config.unitString = "m";
        config.sensorTimeBase = SensorTimeBase.PerSecond;
        rightEncoder.configAllSettings(config);
        config.sensorDirection = true;
        leftEncoder.configAllSettings(config);
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
        SmartDashboard.putNumber("Left Speed", left_speed);
        SmartDashboard.putNumber("Right Speed", right_speed);
        drive.tankDrive(leftLimiter.calculate(left_speed), rightLimiter.calculate(-right_speed));
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
        SmartDashboard.putData(leftEncoder);
        SmartDashboard.putData(rightEncoder);
    }
}
