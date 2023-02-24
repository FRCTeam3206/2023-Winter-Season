package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

public class ChargeLeveler extends CommandBase {

    private Drivetrain drive;

    public ChargeLeveler(Drivetrain drive) {
        this.drive = drive;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        SmartDashboard.putNumber("Level Active", 1.0);
        // m_ShermanDrive.resetGyro();
    }

    @Override
    public void execute() {
        SmartDashboard.putNumber("Level Active", 1.0);
        /*
         * if (m_ShermanDrive.pitch() > 4.0) {
         * m_ShermanDrive.arcadeDrive(-0.25, 0);
         * SmartDashboard.putString("Status","Forward");
         * } else if (m_ShermanDrive.pitch() < -4.0) {
         * m_ShermanDrive.arcadeDrive(0.25, 0);
         * SmartDashboard.putString("Status", "Backward");
         * }else{
         * m_ShermanDrive.arcadeDrive(0, 0);
         * SmartDashboard.putString("Status", "Neutral");
         * }
         */
        double speed = -drive.pitch() * Constants.CHARGE_LEVEL_K;
        if (speed < -.6)
            speed = -.6;
        if (speed > .6)
            speed = .6;
        drive.arcadeDrive(speed, 0);
        //
    }

    @Override
    public void end(boolean interrupted) {
        SmartDashboard.putNumber("Level Active", 0.0);
    }

}