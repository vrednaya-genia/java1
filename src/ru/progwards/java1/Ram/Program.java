package ru.progwards.java1.Ram;

import java.util.*;

public class Program {
    Map<String, Integer> labels; // <имя метки, индекс для перехода по списку listOperator>
    ListWithPointer<Operator> listOperator; // готовый к исполнению лист операторов
    ListWithPointer<Integer> inputs;
    List<Integer> outputs;
    List<Integer> registers;

    public Program() {
        labels = new HashMap<>();
        inputs = new ListWithPointer<>();
        listOperator = new ListWithPointer<>();
        outputs = new ArrayList<>();
        registers = new ArrayList<>();
        inputs.setPointer(0);
        listOperator.setPointer(0);
    }

    //// предварительный анализ содержимого файла
    boolean defineOp(String line) {
        String func;
        String op = "";

        int index = line.indexOf(" ");
        if (index == -1) {
            func = line;
        } else {
            func = line.substring(0, index).trim();
            op = line.substring(index + 1).trim();
        }

        if (func.equals("JUMP")) {
            func = "JMP";
        }

        Operator operator;
        switch (func) {
            case "<INPUT>":
                operator = new WriteOperator(Operator.Type.INPUT, op, this);
                listOperator.add(operator);
                break;
            case "READ":
                operator = new ReadOperator(Operator.Type.READ, op, this);
                listOperator.add(operator);
                break;
            case "WRITE":
                operator = new WriteOperator(Operator.Type.WRITE, op, this);
                listOperator.add(operator);
                break;
            case "LOAD":
                operator = new WriteOperator(Operator.Type.LOAD, op, this);
                listOperator.add(operator);
                break;
            case "STORE":
                operator = new ReadOperator(Operator.Type.STORE, op, this);
                listOperator.add(operator);
                break;
            case "ADD":
                operator = new MathOperator(Operator.Type.ADD, op, this);
                listOperator.add(operator);
                break;
            case "SUB":
                operator = new MathOperator(Operator.Type.SUB, op, this);
                listOperator.add(operator);
                break;
            case "JMP":
                operator = new JumpOperator(Operator.Type.JMP, op, this);
                listOperator.add(operator);
                break;
            case "JGTZ":
                operator = new JumpOperator(Operator.Type.JGTZ, op, this);
                listOperator.add(operator);
                break;
            case "JZ":
                operator = new JumpOperator(Operator.Type.JZ, op, this);
                listOperator.add(operator);
                break;
            case "HALT":
                operator = new JumpOperator(Operator.Type.HALT, op, this);
                listOperator.add(operator);
                break;
            default:
                System.out.println(func + " " + op);
                return false;
        }
        return true;
    }

    boolean analyze(List<String> listStr) {
        Iterator<String> it = listStr.iterator();
        while (it.hasNext()) {
            String line = it.next();
            line = line.toUpperCase().trim();
            // комментарии
            if (line.contains(";")) {
                int index = line.indexOf(";");
                line = line.substring(0, index).trim();
            }
            // пустые строки
            if ("".equals(line)) {
                continue;
            }
            // метки
            if (line.contains(":")) {
                int index = line.indexOf(":");
                String labelName = line.substring(0, index).trim();
                labels.put(labelName, listOperator.size());
                if (index + 1 == line.length()) {
                    continue;
                } else {
                    line = line.substring(index + 1).trim();
                }
            }
            // остальное
            if (!defineOp(line)) {
                return true;
            }
        }
        return false;
    }

    void go() {
        while (listOperator.getPointer() != -1) {
            listOperator.get(listOperator.getPointer()).applyOp();
            // если нет оператора HALT
            if (listOperator.getPointer() == listOperator.size()) {
                listOperator.setPointer(-1);
            }
        }
    }
}
