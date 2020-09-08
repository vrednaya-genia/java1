package ru.progwards.java1.telegrambot;

import java.util.Scanner;
import org.telegram.telegrambots.ApiContextInitializer;
import ru.progwards.java1.telegrambot.ProgwardsTelegramBot;
import ru.progwards.java1.telegrambot.ProgwardsTelegramBot.FoundTags;

public class MyFlirtBot extends ProgwardsTelegramBot {
    String[] problems = {"погода", "настроение", "работа", "учеба", "здоровье", "семья"};
    private boolean stop = false;

    // запоминаем количество упоминаний каждой проблемы у user
    void collectProblems(Integer userid, FoundTags tags) {
        String problem = getLastFound(tags);
        Integer count = 0;
        String str = getUserData(userid, problem);
        if (str != null)
            count = Integer.parseInt(str);
        count++;
        setUserData(userid, problem, count.toString());
    }

    String finish(Integer userid) {
        String str = "";
        String res = "";
        for (int i=0; i<6; i++) {
            str = getUserData(userid, problems[i]);
            if (str != null) {
                res = res + problems[i] + ", ";
            }
        }
        res = res.substring(0,res.length()-2);
        return "Эх.. " + res + "... Надеюсь тебе стало лучше) пока!";
    }

    String finishCheck(Integer userid) {
        // проверить что все 4 группы блюд в заказе
        // если какой-то группы нет && бот не предлагал
        // то предложить и установить ключ, что бы не предлагать 2 раза

        stop = true;
        // если заказ пуст выдать другое сообщение
        return "Надеюсь тебе стало лучше) пиши, если опять что-то будет тревожить!";
    }

    /** Метод, который возвращает ответ бота
     *  @return ответ
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

            if (checkLastFound(tags, "погода")) {
                collectProblems(userid, tags);
                return "У природы нет плохой погоды, главное, что ты, солнышко, светишь ярче всех!) ";
            }
            if (checkLastFound(tags, "настроение")) {
                collectProblems(userid, tags);
                return "От улыбки станет всем светлее) а от твоей - еще и теплее ";
            }
            if (checkLastFound(tags, "работа")) {
                collectProblems(userid, tags);
                return "Ты точно будешь зарабатывать миллионы! Еще чуть-чуть и попрет ";
            }
            if (checkLastFound(tags, "учеба")) {
                collectProblems(userid, tags);
                return "Встречают по одежке, но какая разница, какие у тебя оценки, если ты просто гений, как Пушкин! ";
            }
            if (checkLastFound(tags, "здоровье")) {
                collectProblems(userid, tags);
                return "надо помедитировать.. ";
            }
            if (checkLastFound(tags, "семья")) {
                collectProblems(userid, tags);
                return "Очень важно иметь рядом кого-то, кто тебя ценит! Вот у тебя есть я! ";
            }

            if (checkLastFound(tags, "привет"))
                return "Привет, солнышко!\nТы просто молодец в последнее время! Может есть что-то что тебя беспокоит? "+
                        "\n(учеба, работа, семья...)";
            if (checkLastFound(tags, "дурак"))
                return "прости меня( я буду лучше стараться!\nчто скажешь?";
            if (checkLastFound(tags, "хорошо"))
                return "стоп или поехали дальше?";

            if (checkLastFound(tags, "конец"))
                return finish(userid);
        }
        if (foundCount(tags) > 1)
            return "Как много всего... \n" + extract(tags) + "Давай-ка определимся ;)";

        return "Прости, я глупышка не понимаю тебя. Может поскафним о чем-то другом? ";
    }

    public static void main(String[] args) {
        ApiContextInitializer.init();
        MyFlirtBot bot = new MyFlirtBot();
        bot.username = "MyFlirtBot";
        bot.token = "1331740204:AAFRZpUZbZW8SYvSG3s9cXtFFJA6n-UyUHI";

        bot.addTags("привет", "привет, здравствуйте, добр, день, вечер, утро");
        bot.addTags("дурак", "дурак, идиот, тупой, не понимаешь");
        bot.addTags("хорошо", "хорошо, ладн");
        bot.addTags("конец", "конец, все, стоп, хватит");
        // проблемы
        bot.addTags("погода", "погод, дожд, туч, жар, холод, гололед");
        bot.addTags("настроение", "настр, груст, тоск, жизн");
        bot.addTags("работа", "работ, начальни, отпуск, зарплат");
        bot.addTags("учеба", "учеб, учитель, училк, урок, журнал, дневник");
        bot.addTags("здоровье", "здоров, голов, ног, рук, паль, жир, устал");
        bot.addTags("семья", "пап, мам, доч, сын, бабушк, дедушк, отец, отц, семь");

        bot.start();
        //bot.test();
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
