package ru.progwards.java1.Ram;

public class MathOperator extends Operator {
    public MathOperator(Type type, String operand, Program data) {
        super(type, operand, data);
    }

    @Override
    void applyOp() {
        Integer number = getOperand();
        if (number == -1) {
            System.out.println("Произошла ошибка при выполнении программы (арифметический оператор)!");
            data.listOperator.setPointer(-1);
            return;
        }
        if (type == Type.ADD) {
            data.registers.set(0, data.registers.get(0) + number);
        }
        if (type == Type.SUB) {
            data.registers.set(0, data.registers.get(0) - number);
        }
        super.applyOp();
    }
}
