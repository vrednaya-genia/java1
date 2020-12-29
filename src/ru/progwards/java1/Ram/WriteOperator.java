package ru.progwards.java1.Ram;

public class WriteOperator extends Operator {
    public WriteOperator(Type type, String operand, Program data) {
        super(type, operand, data);
    }

    @Override
    void applyOp() {
        if (type == Type.INPUT) {
            String[] ops = operand.split(" ");
            for (int i=0; i<ops.length; i++) {
                // проверка корректности входных данных
                try {
                   Integer temp = Integer.parseInt(ops[i]);
                } catch (Exception e) {
                    System.out.println("Произошла ошибка при выполнении программы (чтение входных данных)!");
                    data.listOperator.setPointer(-1);
                    return;
                }
                data.inputs.add(Integer.parseInt(ops[i]));
            }
            data.listOperator.nextPointer();
            return;
        }
        Integer number = getOperand();
        if (number == -1) {
            System.out.println("Произошла ошибка при выполнении программы (оператор записи)!");
            data.listOperator.setPointer(-1);
            return;
        }
        if (type == Type.WRITE) {
            data.outputs.add(number);
        }
        if (type == Type.LOAD) {
            data.registers.set(0, number);
        }
        data.listOperator.nextPointer();
    }
}

