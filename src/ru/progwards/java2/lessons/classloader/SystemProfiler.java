package ru.progwards.java2.lessons.classloader;

public class SystemProfiler {
    /*
    Создать java-агент - профилировщик, основанный на классе Profiler из базового курса. При решении задачи использовать библиотеку javassist (начиная с JDK 9 требуется версия 3.21.0-GA или старше). Задача агента - пропатчить программу:

1) добавить из агента необходимые классы профайлера в программу, над которой работает агент;

2) пропатчить каждый метод, вставляя перед ним вызов enterSection(<полное имя метода, включая класс и пакет>), и после него exitSection(<полное имя метода, включая класс и пакет>);

3) реализовать метод, printStatisticInfo(String fileName) который выводит результат профилировки в файл с именем fileName;

4) добавить в конец метода main программы вызов метода printStatisticInfo(<Имя главного класса программы> + ".stat");

5) имена классов для профилировки className взять из параметра агента (-javaagent:<имя агента.jar>=<параметр>), при этом 0-ой класс является главным классом программы (содержит метод main).

Имена классов, когда их более одного разделяются ";"

Подсказка по работе Javassist (агент добавляет в метод main программы вычисление времени выполнения метода):

ClassPool classPool = ClassPool.getDefault();

CtClass ctClass = classPool.get(className.replace('/', '.'));

CtMethod ctMethod = ctClass.getDeclaredMethod("main"); // имя метода

ctMethod.addLocalVariable("start", CtClass.longType);

ctMethod.insertBefore("start = System.currentTimeMillis();");

ctMethod.insertAfter("System.out.println(\"время выполнения\" + (System.currentTimeMillis() - start));");
     */
}
