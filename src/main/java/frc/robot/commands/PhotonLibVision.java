package frc.robot.commands;

import java.io.IOException;
import java.util.Optional;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Vision;

public class PhotonLibVision extends CommandBase {
    PhotonCamera camera = new PhotonCamera(Constants.Vision.photonvision_camera);
    Pose3d pose = new Pose3d();
    PhotonPoseEstimator estimator;
    AprilTagFieldLayout aprilTagFieldLayout = null;
    Field2d field = new Field2d();

    public PhotonLibVision(Vision vision) {
        addRequirements(vision);
        try {
            aprilTagFieldLayout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2023ChargedUp.m_resourceFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (aprilTagFieldLayout != null) {
            try {
                estimator = new PhotonPoseEstimator(
                        aprilTagFieldLayout,
                        PoseStrategy.AVERAGE_BEST_TARGETS,
                        camera,
                        new Transform3d());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void execute() {
        if (estimator == null)
            return;
        Optional<EstimatedRobotPose> estimatedPoseOptional = estimator.update();
        if (!estimatedPoseOptional.isEmpty()) {
            pose = estimatedPoseOptional.get().estimatedPose;
            field.setRobotPose(pose.toPose2d());
            SmartDashboard.putData("Field", field);
        }
        // Rotation3d rotation = pose.getRotation();
        // SmartDashboard.putNumber("X", pose.getX());
        // SmartDashboard.putNumber("Y", pose.getY());
        // SmartDashboard.putNumber("Z", pose.getZ());
        // SmartDashboard.putNumber("rX", rotation.getX());
        // SmartDashboard.putNumber("rY", rotation.getY());
        // SmartDashboard.putNumber("rZ", rotation.getZ());
    }
}