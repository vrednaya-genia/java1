package ru.progwards.java1.Ram;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RamCompiler {
    List<String> listStrFromFile; // строки из файла
    Program program;

    RamCompiler(String fileName) {
        program = new Program();
        listStrFromFile = new ArrayList<>();
        Path pathFile = Paths.get(fileName);
        try {
            listStrFromFile = Files.readAllLines(pathFile);
            if (program.analyze(listStrFromFile)) {
                listStrFromFile = null;
                System.out.println("Произошла ошибка при анализе данных файла!");
            }
        } catch (IOException e) {
            listStrFromFile = null;
            System.out.println("Произошла ошибка при чтении файла " + e.getMessage());
        }

    }

    boolean isConstructed() {
        return listStrFromFile != null;
    }

    void execute() {
        program.go();
    }

    List<Integer> input() {
        return program.inputs;
    }

    List<Integer> output() {
        return program.outputs;
    }

    List<Integer> register() {
        return program.registers;
    }

    public static void main(String[] args) {
        String fileName = args[0];
        RamCompiler rm = new RamCompiler(fileName);
        if (!rm.isConstructed()) {
            return;
        }
        rm.execute();
        System.out.println("Входные данные " + rm.input());
        System.out.println("Данные регистра " + rm.register());
        System.out.println("Результат " + rm.output());
    }
}