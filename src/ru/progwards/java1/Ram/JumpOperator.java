package ru.progwards.java1.Ram;

public class JumpOperator extends Operator {
    public JumpOperator(Type type, String operand, Program data) {
        super(type, operand, data);
    }

    @Override
    void applyOp() {
        if (type == Type.HALT) {
            data.listOperator.setPointer(-1);
            return;
        }
        if (data.labels.containsKey(operand)) {
            Integer point = data.labels.get(operand);
            switch (type) {
                case JMP:
                    data.listOperator.setPointer(point);
                    return;
                case JGTZ:
                    if (data.registers.get(0)>0) {
                        data.listOperator.setPointer(point);
                        return;
                    }
                    break;
                case JZ:
                    if (data.registers.get(0)==0) {
                        data.listOperator.setPointer(point);
                        return;
                    }
                    break;
            }
        } else {
            System.out.println("Попытка перехода по несуществующей ссылке!");
            data.listOperator.setPointer(-1);
        }
        data.listOperator.nextPointer();
    }
}
