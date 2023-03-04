package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.ADIS16448_IMU;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.helpers.Filter;

public class Gyro {
    public ADIS16448_IMU raw_gyro = new ADIS16448_IMU();

    public Gyro() {
        raw_gyro.calibrate();
        raw_gyro.reset();
    }

    public double getRawGyroAngleX() {
        double value = raw_gyro.getGyroAngleX();
        SmartDashboard.putNumber("Raw Pos X", value);
        return value;
    }

    public double getRawGyroAngleY() {
        double value = raw_gyro.getGyroAngleY();
        SmartDashboard.putNumber("Raw Pos Y", value);
        return value;
    }

    public double getRawGyroAngleZ() {
        double value = raw_gyro.getGyroAngleZ();
        SmartDashboard.putNumber("Raw Pos Z", value);
        return value;
    }

    public Filter pos_x = new Filter(() -> this.getRawGyroAngleX(), Constants.FILTER_WINDOW_SIZE);
    public Filter pos_y = new Filter(() -> this.getRawGyroAngleY(), Constants.FILTER_WINDOW_SIZE);
    public Filter pos_z = new Filter(() -> this.getRawGyroAngleZ(), Constants.FILTER_WINDOW_SIZE);

    public double getGyroAngleX() {
        double value = pos_x.get();
        SmartDashboard.putNumber("Pos X", value);
        return value;
    }

    public double getGyroAngleY() {
        double value = pos_y.get();
        SmartDashboard.putNumber("Pos Y", value);
        return value;
    }

    public double getGyroAngleZ() {
        double value = pos_z.get();
        SmartDashboard.putNumber("Pos Z", value);
        return value;
    }

    public Rotation2d yaw() {
        return new Rotation2d(getRawGyroAngleZ());
    }

    public void periodic() {
        pos_x.periodic();
        pos_y.periodic();
        pos_z.periodic();
        getGyroAngleX();
        getGyroAngleY();
        getGyroAngleZ();
    }
}
