package ru.progwards.java1.lessons.files;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FindDuplicates {
    Path strPath;
    List<String> item;
    boolean hasDuple;

    public List<List<String>> findDuplicates(String startPath) {
        List<List<String>> res = new ArrayList<>();
        strPath = Paths.get(startPath);
        try {
            Files.walkFileTree(strPath, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path p, BasicFileAttributes attrs) {
                    List<String> dupleList = getDuplicate(p);
                    if (dupleList != null) {
                        Path file = p.getFileName();
                        boolean repeat = false;
                        Iterator<List<String>> it = res.iterator();
                        while (it.hasNext()) {
                            List<String> tempList = it.next();
                            Path fileDuple = Paths.get(tempList.get(0)).getFileName();
                            if (fileDuple.equals(file)) {
                                repeat = true;
                                break;
                            }
                        }
                        if (!repeat) {
                            item = dupleList;
                            res.add(item);
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
        return res;
    }

    public List<String> getDuplicate(Path pIn) {
        hasDuple = false;
        item = new ArrayList<>();
        String fName = pIn.getFileName().toString();
        PathMatcher pm = FileSystems.getDefault().getPathMatcher("glob:**/"+fName);
        try {
            Files.walkFileTree(strPath, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path p, BasicFileAttributes attrs) {
                    if (pm.matches(p)) {
                        if (!p.equals(pIn) && isRealDuple(p, pIn)) {
                            item.add(p.toString());
                            hasDuple = true;
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

        if (!hasDuple) {
            item = null;
        } else {
            item.add(pIn.toString());
        }
        return item;
    }

    public boolean isRealDuple(Path p1, Path p2) {
        boolean checked = true;
        try {
            FileTime lmt1 = Files.getLastModifiedTime(p1);
            FileTime lmt2 = Files.getLastModifiedTime(p2);
            if (lmt1.compareTo(lmt2)!=0) {
                return false;
            }
        } catch (IOException e) {
            checked = false;
            System.out.println("Не удается проверить атрибут:lastModifiedTime");
            System.out.println(e.getMessage());
        }

        try {
            long sz1 = Files.size(p1);
            long sz2 = Files.size(p2);
            if (sz1!=sz2) {
                return false;
            }
        } catch (IOException e) {
            checked = false;
            System.out.println("Не удается проверить атрибут:size");
            System.out.println(e.getMessage());
        }

        try {
            byte[] file1 = Files.readAllBytes(p1);
            byte[] file2 = Files.readAllBytes(p2);
            if (file1.length != file2.length) {
                return false;
            }
            for (int i = 0; i < file1.length; i++) {
                if (file1[i] != file2[i]) {
                    return false;
                }
            }
        } catch (IOException e) {
            checked = false;
            System.out.println("Не удается проверить содержимое файла");
            System.out.println(e.getMessage());
        }

        return checked;
    }

    public static void main(String[] args) {
        FindDuplicates t = new FindDuplicates();
        List<List<String>> tt = t.findDuplicates("D:\\test");
        for (List<String> l : tt)
            System.out.println(l);
    }
}
