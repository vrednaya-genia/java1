package ru.progwards.java1.Ram;

public class ReadOperator extends Operator {
    public ReadOperator(Type type, String operand, Program data) {
        super(type, operand, data);
    }

    @Override
    void applyOp() {
        Integer number = getOperand();
        if (number == -1) {
            System.out.println("Произошла ошибка при выполнении программы (оператор чтения)!");
            data.listOperator.setPointer(-1);
            return;
        }
        if (type == Type.READ) {
            if (number < data.registers.size()) {
                data.registers.set(number, data.inputs.get(data.inputs.getPointer()));
            } else {
                for (int i = data.registers.size(); i < number; i++) {
                    data.registers.add(null);
                }
                data.registers.add(data.inputs.get(data.inputs.getPointer()));
            }
            data.inputs.nextPointer();
        }
        if (type == Type.STORE) {
            if (number < data.registers.size()) {
                data.registers.set(number, data.registers.get(0));
            } else {
                for (int i = data.registers.size(); i < number; i++) {
                    data.registers.add(null);
                }
                data.registers.add(data.registers.get(0));
            }
        }
        data.listOperator.nextPointer();
    }
}
