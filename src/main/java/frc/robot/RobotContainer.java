// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.Inputs.*;
import static frc.robot.Constants.Ports.*;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.ChargeLeveler;
import frc.robot.commands.DriveTime;
import frc.robot.commands.DriveUntilSupplier;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.TankDrive;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Vision;

public class RobotContainer {
    // Subsystems
    Drivetrain drive = new Drivetrain();
    Vision vision = new Vision();
    // Claw claw = new Claw();
    // Arm arm = new Arm();
    Intake intake = new Intake();
    // Compressor pcmCompressor = new Compressor(COMPRESSOR_MODULE,
    // PneumaticsModuleType.CTREPCM);

    // Inputs
    CommandGenericHID leftStick = new CommandGenericHID(LEFT_JOYSTICK);
    CommandGenericHID rightStick = new CommandGenericHID(RIGHT_JOYSTICK);
    CommandGenericHID xbox = new CommandGenericHID(XBOX_CONTROLLER);

    // Drive Commands
    ArcadeDrive arcade = new ArcadeDrive(
            drive,
            () -> rightStick.getRawAxis(ARCADE_FORWARD_AXIS),
            () -> rightStick.getRawAxis(ARCADE_ROTATE_AXIS),
            () -> rightStick.getHID().getRawButton(BTN_SHIFT));
    TankDrive tank = new TankDrive(drive, () -> leftStick.getRawAxis(TANK_LEFT_AXIS),
            () -> rightStick.getRawAxis(TANK_RIGHT_AXIS), () -> rightStick.getHID().getRawButton(BTN_SHIFT),
            () -> leftStick.getHID().getRawButton(1));

    // Choosers
    SendableChooser<Command> drive_chooser = new SendableChooser<>();
    SendableChooser<Command> auton_chooser = new SendableChooser<>();

    public RobotContainer() {
        configureBindings();
        autons();
    }

    private void autons() {
        auton_chooser.addOption("Charge Level", new SequentialCommandGroup(new Command[] {
                new InstantCommand(() -> {
                    drive.calibrateGyro();
                }),
                new DriveUntilSupplier(drive, () -> {
                    // System.out.println(0);
                    return drive.pitch() > 10;
                }, 0.8),

                new DriveUntilSupplier(drive, () -> {
                    // System.out.println(1);
                    return drive.pitch() < -10;
                }, 0.8),

                new DriveUntilSupplier(drive, () -> {
                    // System.out.println(2);
                    return Math.abs(drive.pitch()) < 1;
                }, 0.8).setTimeout(1500),

                new DriveTime(drive, 0.8, 350),

                new DriveUntilSupplier(drive, () -> {
                    // System.out.println(3);
                    return drive.pitch() < -10;
                }, -0.8),

                new DriveUntilSupplier(drive, () -> {
                    // System.out.println(4);
                    return Math.abs(drive.pitch()) < 5;
                }, -0.5),

                new ChargeLeveler(drive)
        }));
        auton_chooser.setDefaultOption("Forward", new DriveTime(drive, .5, 5000));
        SmartDashboard.putData(auton_chooser);
    }

    private void configureBindings() {
        // Setup Drivetrain
        drive_chooser.setDefaultOption("Tank", tank);
        drive_chooser.addOption("Tank.", arcade);
        SmartDashboard.putData("Drive Mode", drive_chooser);
        rightStick.button(BTN_LEVEL).whileTrue(new ChargeLeveler(drive));
        drive.setDefaultCommand(drive_chooser.getSelected());
        // vision.setDefaultCommand(new PhotonLibVision(vision));
        // Setup Claw
        // claw.setDefaultCommand(
        // new ClawCommand(
        // claw,
        // () -> xbox.getHID().getRawButton(BTN_CONE),
        // () -> xbox.getHID().getRawButton(BTN_CUBE)));

        // // Setup Intake
        intake.setDefaultCommand(
                new IntakeCommand(
                        intake,
                        () -> xbox.getHID().getRawButton(BTN_INTAKE_CONE),
                        () -> xbox.getHID().getRawButton(BTN_REVERSE_INTAKE_CONE),
                        () -> xbox.getHID().getRawButton(BTN_INTAKE_CUBE),
                        () -> xbox.getHID().getRawButton(BTN_REVERSE_INTAKE_CUBE)));

        // Setup Compressor
        // pcmCompressor.enableDigital();
    }

    public Command getAutonomousCommand() {

        return auton_chooser.getSelected();

    }
}