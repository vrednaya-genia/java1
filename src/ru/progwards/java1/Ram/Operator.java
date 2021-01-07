package ru.progwards.java1.Ram;

abstract class Operator {
    enum Type {ADD, SUB, READ, STORE, INPUT, WRITE, LOAD, JMP, JGTZ, JZ, HALT}

    Type type;
    String operand;
    Program data;
    public Operator(Type type, String operand, Program data) {
        this.type = type;
        this.operand = operand;
        this.data = data;
    }

    Integer getOperand() {
        if (operand == null || data.registers == null) {
            return -1;
        }
        if ("".equals(operand)) {
            return -1;
        }
        if (type == Type.READ || type == Type.STORE) {  // для ReadOperator
            if (operand.charAt(0) == '*') {
                int iop = Integer.parseInt(operand.substring(1));
                return data.registers.get(iop);
            } else {
                return Integer.parseInt(operand);
            }
        } else {  // для других операторов
            if (operand.charAt(0) == '=') {
                return Integer.parseInt(operand.substring(1));
            }
            if (operand.charAt(0) == '*') {
                int iop = Integer.parseInt(operand.substring(1));
                iop = data.registers.get(iop);
                return data.registers.get(iop);
            }
            return data.registers.get(Integer.parseInt(operand));
        }
    }

    abstract void applyOp();

    // void applyOp() {
    //      data.pointer++;
    // если нет оператора HALT
    //      if (data.pointer == data.listOperator.size()) {
    //                data.pointer = -1;
    //            }
    // }
}
