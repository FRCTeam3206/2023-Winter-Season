package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class TankDrive extends CommandBase {
    private final Drivetrain m_drive;
    private final Supplier<Double> leftSupplier;
    private final Supplier<Double> rightSupplier;
    Supplier<Boolean> shiftSupplier;
    Supplier<Boolean> reverseSupplier;

    public TankDrive(Drivetrain subsystem, Supplier<Double> leftForwardPower, Supplier<Double> rightForwardPower,
            Supplier<Boolean> shiftSupplier, Supplier<Boolean> reverseSupplier) {
        this.m_drive = subsystem;
        this.leftSupplier = leftForwardPower;
        this.rightSupplier = rightForwardPower;
        this.shiftSupplier = shiftSupplier;
        this.reverseSupplier = reverseSupplier;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        this.m_drive.tankDrive(0, 0);
    }

    @Override
    public void execute() {

        if (reverseSupplier.get()) {
            this.m_drive.tankDrive(this.leftSupplier.get(), this.rightSupplier.get());
        } else {
            this.m_drive.tankDrive(-this.leftSupplier.get(), -this.rightSupplier.get());
        }
        m_drive.setShift(shiftSupplier.get());
    }

    @Override
    public void end(boolean interrupted) {
    }

}