package ru.progwards.java1.lessons.files;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FilesSelect {

    public void selectFiles(String inFolder, String outFolder, List<String> keys) {
        if (inFolder==null || outFolder==null || keys==null) {
            return;
        }
        Path outPath = Paths.get(outFolder);
        if (Files.notExists(outPath)) {
            try {
                Files.createDirectory(outPath);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        Path inPath = Paths.get(inFolder);
        PathMatcher pm = FileSystems.getDefault().getPathMatcher("glob:**/*.txt");
        try {
            Files.walkFileTree(inPath, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path p, BasicFileAttributes attrs) {
                    if (pm.matches(p)) {
                        try {
                            String fileString = Files.readString(p);
                            Iterator<String> it = keys.iterator();
                            while (it.hasNext()) {
                                String key = it.next();
                                if (fileString.contains(key)) {
                                    Path nameFile = p.getFileName();
                                    Path out = outPath.resolve(key);
                                    if (Files.notExists(out)) {
                                        try {
                                            Files.createDirectory(out);
                                        } catch (IOException e) {
                                            System.out.println(e.getMessage() + " ошибка при создании каталога");
                                        }
                                    }
                                    out = Paths.get(out.toString()+"/"+nameFile.toString());
                                    try {
                                        Files.copy(p, out, StandardCopyOption.REPLACE_EXISTING);
                                    } catch (IOException e) {
                                        System.out.println(e.getMessage() + " ошибка при копировании");
                                    }
                                }
                            }
                        } catch (IOException e) {
                            System.out.println(e.getMessage() + " ошибка при чтении файла");
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }
                @Override
                public FileVisitResult visitFileFailed(Path p, IOException e) {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        FilesSelect t = new FilesSelect();
        List<String> key = new ArrayList<>();
        key.add("123");
        key.add("321");

        t.selectFiles("D:\\test", "D:\\tests", key);
    }
}
