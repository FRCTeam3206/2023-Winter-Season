package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class TankDrive extends CommandBase {
    private final Drivetrain m_drive;
    private final Supplier<Double> leftSupplier;
    private final Supplier<Double> rightSupplier;
    Supplier<Boolean> shiftSupplier;

    public TankDrive(Drivetrain subsystem, Supplier<Double> leftForwardPower, Supplier<Double> rightForwardPower,
            Supplier<Boolean> shiftSupplier) {
        this.m_drive = subsystem;
        this.leftSupplier = leftForwardPower;
        this.rightSupplier = rightForwardPower;
        this.shiftSupplier = shiftSupplier;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        this.m_drive.tankDrive(this.leftSupplier.get(), this.rightSupplier.get());
        m_drive.setShift(shiftSupplier.get());
    }

    @Override
    public void end(boolean interrupted) {
    }

}