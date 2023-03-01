package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import java.io.IOException;
import java.util.Optional;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.EstimatedRobotPose;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

public class IntegratedOdometry {
    Gyro gyro;
    RelativeEncoder rightEncoder, lefEncoder;
    PhotonCamera camera = new PhotonCamera(Constants.Vision.photonvision_camera);
    Pose3d pose = new Pose3d();
    PhotonPoseEstimator estimator;
    AprilTagFieldLayout aprilTagFieldLayout = null;
    Field2d field = new Field2d();
    DifferentialDriveOdometry odometry;

    public IntegratedOdometry(Gyro gyro, RelativeEncoder rightEncoder, RelativeEncoder lefEncoder) {
        this.gyro = gyro;
        this.rightEncoder = rightEncoder;
        this.lefEncoder = lefEncoder;
        odometry = new DifferentialDriveOdometry(gyro.yaw(), 0, 0);
        try {
            aprilTagFieldLayout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2023ChargedUp.m_resourceFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (aprilTagFieldLayout != null) {
            try {
                Transform3d cameraPos = new Transform3d(new Translation3d(0, .2, .36), new Rotation3d(0, 0, Math.PI));
                estimator = new PhotonPoseEstimator(
                        aprilTagFieldLayout,
                        PoseStrategy.CLOSEST_TO_REFERENCE_POSE,
                        camera,
                        cameraPos);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Pose2d update() {
        // propose encoder based position
        Pose2d pose = odometry.update(gyro.yaw(), lefEncoder.getPosition(), rightEncoder.getPosition());
        // if we don't have a vision estimator or there is no april tag, return that;
        if (estimator == null)
            return pose;
        // Tell vision what the last encoder position was(to prevent spiradic hopping)
        estimator.setReferencePose(pose);
        Optional<EstimatedRobotPose> estimatedPoseOptional = estimator.update();
        if (!estimatedPoseOptional.isEmpty()) {
            pose = estimatedPoseOptional.get().estimatedPose.toPose2d();
        }
        field.setRobotPose(pose);
        SmartDashboard.putData("Field", field);
        // tell odometry that vision is correct
        odometry.resetPosition(gyro.yaw(), lefEncoder.getPosition(), rightEncoder.getPosition(), pose);
        return pose;
    }
}
