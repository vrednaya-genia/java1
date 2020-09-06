package ru.progwards.java1.telegrambot;

import java.util.Scanner;
import org.telegram.telegrambots.ApiContextInitializer;
import ru.progwards.java1.telegrambot.ProgwardsTelegramBot;
import ru.progwards.java1.telegrambot.ProgwardsTelegramBot.FoundTags;

public class MyFlirtBot extends ProgwardsTelegramBot {
    private final String menu = "Я умею находить нужные слова для утешения!";
    private static final String problems = "проблемы";
    private boolean stop = false;

    // группы тем для скафнежа
    String finishCheck(Integer userid) {
        // проверить что все 4 группы блюд в заказе
        // если какой-то группы нет && бот не предлагал
        // то предложить и установить ключ, что бы не предлагать 2 раза

        stop = true;
        // если заказ пуст выдать другое сообщение
        return "Надеюсь тебе стало лучше) пиши, если опять что-то будет тревожить!";
    }

    // сохранить проблемы
    void saveProblemItem(Integer userid, FoundTags tags) {
        // считать количество
        Integer count = 0;
        String str = getUserData(userid, problems);
        if (str != null)
            count = Integer.parseInt(str);
        // инкрементировать количество
        count++;
        // сохранить количество
        setUserData(userid, problems, count.toString());
        // сохранить позицию в заказе как orderKey + count
        // ключи - order1, order2
        // данные - getLastFound(tags)

        // дополнительно 1
        // проверить связанные покупки
        // если он заказывает картошку, то предложить соус, если не предлагали
    }

    String getOrder(Integer userid) {
        // считать количество

        // в цикле по каждому элементу вывести содержимое
        // ключ orderKey + номер
        return "Выш заказ";
    }


    /**
     * Метод, который возвращает ответ бота
     * @return ответ
     */
    @Override
    public String processMessage(Integer userid, String text) {
        // сброс всех данных для пользователя - нужно для тестирования
        if (text.equals("/reset"))
            cleartUserData(userid);

        // ищем подходящие тэги под запрос из заданных через addTags
        FoundTags tags = checkTags(text);
        // если найдено всего один вариант
        if (foundCount(tags) == 1) {
            if (checkLastFound(tags, "привет"))
                return "Привет, солнышко!\nКак тебе погода? ";
            if (checkLastFound(tags, "погода"))
                return "У природы нет плохой погоды, а ты мое солнышко) Может есть то, что тебя беспокоит? " + menu;
            if (checkLastFound(tags, "дурак"))
                return "прости меня( я буду лучше стараться!\nчто скажешь?";

            if (checkLastFound(tags, "конец"))
                return getOrder(userid);

            // Добавить связанные предложения, например если он заказывает картошку, то предложить соус,
            // если отказывается - сохранить флажок, что бы бесконечно не предлагать
            // дополнительно 2
            // реализовать отмену позиции заказа

            saveProblemItem(userid, tags);
            return "Ну вроде разобрались с этим) " + getLastFound(tags) + "... Еще что-то пообсуждаем? " + menu;
        }
        if (foundCount(tags) > 1)
            return "Как много всего... \n" + extract(tags) + "\nДавай определимся о чем будем скафнить ;)";

        return "Прости, я не понимаю тебя. Может поскафним о чем-то другом? " + menu;
    }

    public static void main(String[] args) {
        ApiContextInitializer.init();

        MyFlirtBot bot = new MyFlirtBot();
        bot.username = "MyFlirtBot";
        bot.token = "1331740204:AAFRZpUZbZW8SYvSG3s9cXtFFJA6n-UyUHI";

        bot.addTags("привет", "привет, здравствуйте, добр, день, вечер, утро, hi, hello, ghbdtn");
        bot.addTags("дурак", "дурак, идиот, тупой, не понимаешь");
        bot.addTags("конец", "конец, все, стоп, нет, хватит");

        bot.addTags("погода", "погод, дожд, туч, жар, холод, норм, пойдет");
        bot.addTags("настроение", "настр, груст, тоск, хренов");
        bot.addTags("работа", "работ, начальни, отпуск");
        bot.addTags("работа", "работ, начальни, отпуск");

        //bot.start();
        bot.test();
    }

    void test() {
        Scanner in = new Scanner(System.in);
        String input;
        do {
            input = in.nextLine();
            System.out.println(processMessage(123, input));
        } while (!stop);
        in.close();
    }
}
