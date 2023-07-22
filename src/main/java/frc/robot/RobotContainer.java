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
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.Inputs;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.ArmMove;
import frc.robot.commands.ChargeLeveler;
import frc.robot.commands.ClawCommand;
import frc.robot.commands.DriveDistance;
import frc.robot.commands.DriveTime;
import frc.robot.commands.DriveUntilSupplier;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.TankDrive;
import frc.robot.commands.IntakeDown;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Lights;;

public class RobotContainer {
    // Subsystems
    Drivetrain drive = new Drivetrain();
    Vision vision = new Vision();
    Claw claw = new Claw();
    Arm arm = new Arm();
    Intake intake = new Intake();
    Lights lights = new Lights();
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
        lights.setRainbow();
        autons();
    }

    private DriveUntilSupplier getGetOnChargeStation() {
        return new DriveUntilSupplier(drive, () -> {
            // System.out.println(0);
            return drive.pitch() < -5;
        }, -0.8);
    }

    public SequentialCommandGroup getGetOverChargeStation() {
        return new SequentialCommandGroup(new ParallelCommandGroup(new DriveUntilSupplier(drive, () -> {
            System.out.println(1);
            return drive.pitch() > 5;
        }, -0.8).setTimeout(3000), new InstantCommand(() -> {
            intake.runIntake(0);
            intake.setTransport(false);
        }, intake)),

                new DriveUntilSupplier(drive, () -> {
                    System.out.println(2);
                    return Math.abs(drive.pitch()) < 2;
                }, -0.8).setTimeout(3000),

                new DriveTime(drive, 0.8, 350));
    }

    SequentialCommandGroup balence = new SequentialCommandGroup(
            getGetOnChargeStation(),
            getGetOverChargeStation(),

            new DriveUntilSupplier(drive, () -> {
                // System.out.println(3);
                return drive.pitch() > 5;
            }, 0.8),

            new DriveDistance(drive, 1.2, .5));

    private InstantCommand getDropCube() {
        return new InstantCommand(() -> {
            drive.fixReverseDrive();
            intake.runIntake(-.2);
        }, intake, drive);
    }

    SequentialCommandGroup back = new SequentialCommandGroup(
            new InstantCommand(() -> {
                drive.resetEncoders();
            }),
            new DriveUntilSupplier(drive, () -> drive.getRawEncoderDistance() < -3.75, -.7)
                    .setTimeout(7000));

    private void autons() {
        auton_chooser.addOption("Cube+Taxi+Charge", new SequentialCommandGroup(new Command[] {
                getDropCube(),
                balence
        }));
        auton_chooser.setDefaultOption("DONT USE Cube+Forward",
                new ParallelCommandGroup(new Command[] { getDropCube(), back }));
        auton_chooser.addOption("DONT USEPure Encoder Cube+Taxi+Charge", new SequentialCommandGroup(
                getDropCube(),
                new DriveUntilSupplier(drive, () -> drive.getRawEncoderDistance() < -4, -.7)
                        .setTimeout(7000),
                new DriveUntilSupplier(drive, () -> drive.getRawEncoderDistance() > -2, .5)
                        .setTimeout(7000)));
        auton_chooser.addOption("DONT USE Partial Encoder Cube+Taxi+Charge", new SequentialCommandGroup(
                getDropCube(),
                new DriveUntilSupplier(drive, () -> drive.getRawEncoderDistance() < -4, -.7)
                        .setTimeout(7000),
                new InstantCommand(() -> {
                    intake.setTransport(false);
                }, intake),
                new DriveUntilSupplier(drive, () -> {
                    // System.out.println(3);
                    return drive.pitch() > 5;
                }, 0.8),
                new InstantCommand(() -> {
                    intake.setTransport(true);
                }, intake),
                new DriveDistance(drive, 1.15, .5)));
        auton_chooser.addOption("Cube+Balance", new SequentialCommandGroup(
                getDropCube(),
                new DriveUntilSupplier(drive, () -> {
                    // System.out.println(3);
                    return drive.pitch() < -5;
                }, -0.5),
                new DriveDistance(drive, -1.15, -.5)));
        auton_chooser.addOption("Cube+Taxi", new SequentialCommandGroup(
                getDropCube(),
                new DriveDistance(drive, -3.75, -.5)));
        auton_chooser.addOption("High_Cube+Balance", new SequentialCommandGroup(
                new InstantCommand(() -> {
                    claw.cube();
                    intake.setTransport(false);
                }, claw, intake),
                // new WaitCommand(1.0),
                new InstantCommand(() -> {
                    arm.setArmPosition(Constants.ArmConstants.ARM_ANGLE_GRAB);
                }, arm),
                new DriveTime(drive, -.4, 3000),
                new InstantCommand(() -> {
                    claw.open();
                }, claw),
                new DriveDistance(drive, .4, .4),
                new InstantCommand(() -> {
                    arm.setArmPosition(Constants.ArmConstants.ARM_ANGLE_DOWN);
                }, arm),
                new WaitCommand(1.0),
                new DriveUntilSupplier(drive, () -> {
                    // System.out.println(3);
                    return drive.pitch() > 5;
                }, 0.5).setTimeout(5000),
                new DriveDistance(drive, 1.14, .5),
                new DriveUntilSupplier(drive, () -> {
                    return drive.pitch() > 3;
                }, 0.25).setTimeout(1000)));
        auton_chooser.addOption("High_Cube+Taxi", new SequentialCommandGroup(
                new InstantCommand(() -> {
                    claw.cube();
                }, claw),
                // new WaitCommand(1.0),
                new InstantCommand(() -> {
                    arm.setArmPosition(Constants.ArmConstants.ARM_ANGLE_GRAB);
                }, arm),
                new DriveTime(drive, -.4, 3000),
                new InstantCommand(() -> {
                    claw.open();
                }, claw),

                new DriveDistance(drive, 3.75, .5),
                new InstantCommand(() -> {
                    arm.setArmPosition(Constants.ArmConstants.ARM_ANGLE_DOWN);
                }, arm)));

        SmartDashboard.putData(auton_chooser);
    }

    private void configureBindings() {
        // Setup Drivetrain
        drive_chooser.setDefaultOption("Tank", tank);
        drive_chooser.addOption("Arcade.", arcade);
        SmartDashboard.putData("Drive Mode", drive_chooser);
        rightStick.button(BTN_LEVEL).whileTrue(new ChargeLeveler(drive));
        drive.setDefaultCommand(drive_chooser.getSelected());
        Trigger fallenandcantgetup = new Trigger(() -> { return Math.abs(drive.pitch())>45; });
        fallenandcantgetup.whileTrue(new RunCommand(() -> {lights.setLightColor(155, 0, 0);}, lights));
        xbox.axisLessThan(3, -.5).whileTrue(new RunCommand(() -> {
            if (arm.position < 100)
                arm.setArmPosition(arm.position + .4);
        }, arm));
        xbox.axisGreaterThan(3, .5).whileTrue(new RunCommand(() -> {
            if (arm.position > 0)
                arm.setArmPosition(arm.position - .4);
        }, arm));
        xbox.button(11).onTrue(new SequentialCommandGroup(
                new InstantCommand(() -> {
                    intake.resetEncoder();
                }, intake)));
        xbox.axisGreaterThan(1, .5).whileTrue(new RunCommand(() -> {
            intake.overrideTransport(-.5);
        }, intake));
        xbox.axisLessThan(1, -.5).whileTrue(new RunCommand(() -> {
            intake.overrideTransport(.5);
        }, intake));
        xbox.povUp().whileTrue(new RunCommand(() -> {
            arm.setArmPosition(Constants.ArmConstants.ARM_ANGLE_GRAB);
        }, arm));
        xbox.povDown().whileTrue(new RunCommand(() -> {
            arm.setArmPosition(Constants.ArmConstants.ARM_ANGLE_DOWN);
        }, arm));
        xbox.povRight().whileTrue(new RunCommand(() -> {
            arm.setArmPosition(Constants.ArmConstants.ARM_ANGLE_SCORE);
        }, arm));
        xbox.povLeft().whileTrue(new RunCommand(() -> {
            arm.setArmPosition(Constants.ArmConstants.ARM_ANGLE_HOLD);
        }, arm));
        // vision.setDefaultCommand(new PhotonLibVision(vision));
        // Setup Claw
        // claw.setDefaultCommand(
        xbox.button(10).whileTrue(new RunCommand(() -> {
            claw.open();
        }, claw));// Start
        xbox.button(4).whileTrue(new RunCommand(() -> {
            claw.cone();
        }, claw));// Yellow y
        xbox.button(1).whileTrue(new RunCommand(() -> {
            claw.cube();
        }, claw));// Blue X
        xbox.button(3).whileTrue(new RunCommand(() -> {
            intake.runIntake(.6);
        }));
        // // Setup Intake

        // intake.setDefaultCommand(
        // new IntakeCommand(
        // intake,
        // () -> xbox.getHID().getRawButton(BTN_INTAKE_CONE),
        // () -> xbox.getHID().getRawButton(BTN_REVERSE_INTAKE_CONE),
        // () -> xbox.getHID().getRawButton(BTN_INTAKE_CUBE),
        // () -> xbox.getHID().getRawButton(BTN_REVERSE_INTAKE_CUBE),
        // () -> xbox.getHID().getRawButton(BTN_TRANS_DOWN)));

        xbox.button(BTN_INTAKE_CONE).whileTrue(new RunCommand(() -> {
            intake.runIntake(.5);
            intake.setDeploy(true);
            intake.setTransport(true);
        }, intake));
        xbox.button(BTN_INTAKE_CUBE).whileTrue(new RunCommand(() -> {
            intake.runIntake(.4);
            intake.setDeploy(true);
            intake.setTransport(true);
        }, intake));
        xbox.button(BTN_REVERSE_INTAKE_CONE).whileTrue(new RunCommand(() -> {
            intake.runIntake(-.62);
            intake.setDeploy(true);
            intake.setTransport(true);
        }, intake));
        xbox.button(BTN_REVERSE_INTAKE_CUBE).whileTrue(new RunCommand(() -> {
            intake.runIntake(-.25);
            intake.setDeploy(true);
            intake.setTransport(true);
        }, intake));
        xbox.button(Inputs.BTN_TRANS_DOWN).whileTrue(new RunCommand(() -> {
            // intake.setDeploy(false);
            // intake.runIntake(0);
            // intake.setDeploy(true);
            intake.setTransport(false);
        }, intake));

        intake.setDefaultCommand(new RunCommand(() -> {
            intake.runIntake(0);
            intake.setDeploy(false);
            intake.setTransport(true);
        }, intake));
        // xbox.pov(0).whileTrue(new RunCommand(() -> {
        // intake.overrideTransport(.5);
        // }, intake));
        // xbox.pov(180).whileTrue(new RunCommand(() -> {
        // intake.overrideTransport(-.5);
        // }, intake));
        xbox.button(12).onTrue(new InstantCommand(() -> {
            intake.resetEncoder();
        }, intake));
        // xbox.povUp().whileTrue(new RunCommand(() -> {
        // armo.setElbowUp();
        // }, armo));
        // Setup Compressor
        // pcmCompressor.enableDigital();
    }

    public Command getAutonomousCommand() {

        return auton_chooser.getSelected();

    }
}