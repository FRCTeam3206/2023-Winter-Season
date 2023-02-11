package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
    CANSparkMax frontLeftDrive = new CANSparkMax(1, MotorType.kBrushless);
    CANSparkMax frontRighttDrive = new CANSparkMax(2, MotorType.kBrushless);
    CANSparkMax rearLeftDrive = new CANSparkMax(3, MotorType.kBrushless);
    CANSparkMax rearRightDrive = new CANSparkMax(4, MotorType.kBrushless);

    RelativeEncoder leftEncoder = frontLeftDrive.getEncoder();
    RelativeEncoder rightEncoder = frontRighttDrive.getEncoder();

    DifferentialDrive drive = new DifferentialDrive(frontLeftDrive, frontRighttDrive);
    Gyro gyro = new Gyro();

    public Drivetrain() {
        frontLeftDrive.restoreFactoryDefaults();
        frontRighttDrive.restoreFactoryDefaults();
        rearLeftDrive.restoreFactoryDefaults();
        rearRightDrive.restoreFactoryDefaults();

        leftEncoder.setPosition(0);
        rightEncoder.setPosition(0);

        frontRighttDrive.setInverted(true);
        rearLeftDrive.follow(frontLeftDrive);
        rearRightDrive.follow(frontRighttDrive);
    }

    public void arcadeDrive(double xaxisSpeed, double zaxisRotate) {
        drive.arcadeDrive(xaxisSpeed, zaxisRotate);
    }

    public void tankDrive(double left_speed, double right_speed) {
        drive.tankDrive(left_speed, right_speed);
    }

    public void reset_position() {
        leftEncoder.setPosition(0);
        rightEncoder.setPosition(0);
    }

    @Override
    public void periodic() {
        gyro.periodic();
    }
}
