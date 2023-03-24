package frc.robot.subsystems;

import java.io.IOException;
import java.util.Optional;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;

import com.ctre.phoenix.sensors.CANCoder;

import org.photonvision.EstimatedRobotPose;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Constants.Vision;

public class IntegratedOdometry {
    Gyro gyro;
    CANCoder rightEncoder, lefEncoder;
    PhotonCamera camera = new PhotonCamera(Constants.Vision.photonvision_camera);
    Pose3d pose = new Pose3d();
    PhotonPoseEstimator photonEstimator;
    AprilTagFieldLayout aprilTagFieldLayout = null;
    Field2d field = new Field2d();
    DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(
            Constants.TRACK_WIDTH);
    DifferentialDrivePoseEstimator poseEstimator;

    public IntegratedOdometry(Gyro gyro, CANCoder rightEncoder, CANCoder lefEncoder) {
        this.gyro = gyro;
        this.rightEncoder = rightEncoder;
        this.lefEncoder = lefEncoder;
        poseEstimator = new DifferentialDrivePoseEstimator(kinematics, gyro.yaw(), lefEncoder.getPosition(),
                rightEncoder.getPosition(), new Pose2d());
        try {
            aprilTagFieldLayout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2023ChargedUp.m_resourceFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (aprilTagFieldLayout != null) {
            try {
                photonEstimator = new PhotonPoseEstimator(
                        aprilTagFieldLayout,
                        PoseStrategy.AVERAGE_BEST_TARGETS,
                        camera,
                        Vision.CAMERA_POS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        SmartDashboard.putData("Field", field);
    }

    public Pose2d update() {
        // propose encoder based position
        poseEstimator.update(gyro.yaw(), lefEncoder.getPosition(), rightEncoder.getPosition());
        // if we don't have a vision estimator or there is no april tag, return that;
        if (photonEstimator == null)
            return poseEstimator.getEstimatedPosition();
        // Tell vision what the last encoder position was(to prevent spiradic hopping)
        photonEstimator.setReferencePose(pose);
        Optional<EstimatedRobotPose> estimatedPoseOptional = photonEstimator.update();
        if (!estimatedPoseOptional.isEmpty()) {
            poseEstimator.addVisionMeasurement(estimatedPoseOptional.get().estimatedPose.toPose2d(),
                    estimatedPoseOptional.get().timestampSeconds);
        }
        field.setRobotPose(poseEstimator.getEstimatedPosition());
        return poseEstimator.getEstimatedPosition();
    }
}
