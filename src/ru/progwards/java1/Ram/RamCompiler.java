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
        } catch (IOException e) {
            listStrFromFile = null;
            System.out.println("Ошибка при чтении файла " + e.getMessage());
        }
    }

    void execute() {
        if (program.analyze(listStrFromFile)) {
            System.out.println("Невозможно выполнить программу!");
            return;
        }
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
        String fileName = "D:\\1.3.txt";
        RamCompiler rm = new RamCompiler(fileName);
        if (rm.listStrFromFile == null) {
            return;
        }
        rm.execute();
        System.out.println("Входные данные " + rm.input());
        System.out.println("Данные регистра " + rm.register());
        System.out.println("Результат " + rm.output());
    }
}