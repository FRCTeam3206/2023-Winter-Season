package frc.robot.subsystems;

import edu.wpi.first.wpilibj.ADIS16448_IMU;
import frc.robot.Constants;
import frc.robot.helpers.Filter;

public class Gyro {
    public ADIS16448_IMU imu = new ADIS16448_IMU();

    public Filter pos_x = new Filter(() -> imu.getGyroAngleX(), Constants.FILTER_WINDOW_SIZE);
    public Filter pos_y = new Filter(() -> imu.getGyroAngleY(), Constants.FILTER_WINDOW_SIZE);
    public Filter pos_z = new Filter(() -> imu.getGyroAngleZ(), Constants.FILTER_WINDOW_SIZE);

    public Gyro() {
        imu.reset();
    }

    public void periodic() {
        pos_x.periodic();
        pos_y.periodic();
        pos_z.periodic();
    }

    public double getGyroAngleX() {
        return pos_x.get();
    }

    public double getGyroAngleY() {
        return pos_y.get();
    }

    public double getGyroAngleZ() {
        return pos_z.get();
    }
}
