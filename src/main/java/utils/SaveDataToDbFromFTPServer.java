package utils;

import static ru.sbt.qa.bot900.utils.ConnectMSSQLServer.*;
import static ru.sbt.qa.bot900.utils.ParseDataFromFTPServer.aiAnswersList;
import static ru.sbt.qa.bot900.utils.ParseDataFromFTPServer.aiQuestionList;

/**
 * Инсутрмент отвечает за сохранения тестовых данных с удаленного сервера в тестовую базу данных
 * @author Simonov-MS
 */
public class SaveDataToDbFromFTPServer {

    public static void updateAnswersInDataBase(){
        ConnectMSSQLServer connServer = new ConnectMSSQLServer();
        //получение тестовых данных
        ParseDataFromFTPServer.getAnswer();
        //коннект в бд
        tableName = "ai_answer";
        ConnectMSSQLServer.dbTruncate("jdbc:sqlserver://10.116.6.208\\SQL2014R2;databaseName=chatbot", "MobileApp", "MobileApp");
        for (int i = 0;i <  aiAnswersList.size(); i++) {
            ConnectMSSQLServer.type = aiAnswersList.get(i).getAction().getCommand();
            ConnectMSSQLServer.id = Integer.parseInt(aiAnswersList.get(i).getId());
            ConnectMSSQLServer.text = aiAnswersList.get(i).getAction().getNodes().getAnswer().get(0).get(0);
            connServer.dbUpdate("jdbc:sqlserver://10.116.6.208\\SQL2014R2;databaseName=chatbot", "MobileApp", "MobileApp");
        }
    }
    public static void updateQuestionsInDataBase(){
        ConnectMSSQLServer connServer = new ConnectMSSQLServer();
        //получение тестовых данных c удаленного сервера
        ParseDataFromFTPServer.getQuestion();
        //коннект в бд
        tableName = "ai_questions";
        //очистка таблицы перед тем как записать в нее свежие данные
        ConnectMSSQLServer.dbTruncate("jdbc:sqlserver://10.116.6.208\\SQL2014R2;databaseName=chatbot", "MobileApp", "MobileApp");
        for (int i = 0;i <  aiQuestionList.size(); i++) {
            for (String s : aiQuestionList.get(i).getQuestions_negative()) {
                ConnectMSSQLServer.type = "negative";
                ConnectMSSQLServer.id = aiQuestionList.get(i).getId_faq();
                ConnectMSSQLServer.text = s;
                connServer.dbUpdate("jdbc:sqlserver://10.116.6.208\\SQL2014R2;databaseName=chatbot", "MobileApp", "MobileApp");
            }
            for (String s : aiQuestionList.get(i).getQuestions_positive()) {
                ConnectMSSQLServer.type = "positive";
                ConnectMSSQLServer.id = aiQuestionList.get(i).getId_faq();
                ConnectMSSQLServer.text = s;
                connServer.dbUpdate("jdbc:sqlserver://10.116.6.208\\SQL2014R2;databaseName=chatbot", "MobileApp", "MobileApp");
            }
        }
    }

    public static void main(String[] args) {
        updateQuestionsInDataBase();
        updateAnswersInDataBase();
    }
}
