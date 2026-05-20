import java.util.*;

public class QuestionBank {
    public static class Question {
        int id;
        String title, a, b, c, d, answer;
        public Question(int id, String title, String a, String b, String c, String d, String answer) {
            this.id = id; this.title = title; this.a = a; this.b = b; this.c = c; this.d = d; this.answer = answer;
        }
    }

    public static List<Question> getQuestions() {
        List<Question> list = new ArrayList<>();
        list.add(new Question(0, "TCP是面向？", "无连接", "连接", "报文", "数据报", "B"));
        list.add(new Question(1, "UDP是面向？", "连接", "无连接", "流", "可靠", "B"));
        list.add(new Question(2, "以下哪个可靠？", "UDP", "IP", "TCP", "HTTP", "C"));
        list.add(new Question(3, "心跳用什么更合适", "TCP", "UDP", "FTP", "DNS", "B"));
        list.add(new Question(4, "Socket用于？", "文件", "网络通信", "数据库", "界面", "B"));
        return list;
    }
}