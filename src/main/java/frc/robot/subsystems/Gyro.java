package frc.robot.subsystems;

import edu.wpi.first.wpilibj.ADIS16448_IMU;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.helpers.Filter;

public class Gyro {
    public ADIS16448_IMU raw_gyro = new ADIS16448_IMU();

    public Filter pos_x = new Filter(() -> raw_gyro.getGyroAngleX(), Constants.FILTER_WINDOW_SIZE);
    public Filter pos_y = new Filter(() -> raw_gyro.getGyroAngleY(), Constants.FILTER_WINDOW_SIZE);
    public Filter pos_z = new Filter(() -> raw_gyro.getGyroAngleZ(), Constants.FILTER_WINDOW_SIZE);

    public Gyro() {
        raw_gyro.reset();
    }

    public void periodic() {
        pos_x.periodic();
        pos_y.periodic();
        pos_z.periodic();
    }

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
}
