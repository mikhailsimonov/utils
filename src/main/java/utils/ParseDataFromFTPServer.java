package utils;

import com.google.gson.*;
import com.jcraft.jsch.JSchException;
import ru.sbt.qa.bot900.model.AiAnswers;
import ru.sbt.qa.bot900.model.AiQuestions;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.sbt.qa.bot900.utils.Ssh.*;

/**
 *  Инструмент разбора файлов JSon
 *  @author Simonov-MS
 */

public class ParseDataFromFTPServer {

    private static final String FILENAME_ANSWERS = "src/test/resources/answers/";
    private static final String FILENAME_QUESTIONS = "src/test/resources/questions/";
    static String answer = "";
    static String request = "";
    static List<AiAnswers> aiAnswersList = new ArrayList<>();
    static List<AiQuestions> aiQuestionList = new ArrayList<>();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static void readFilesNameAnswers(String listOfText) {
        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader(FILENAME_ANSWERS +listOfText));
            String s;
            while ((s = reader.readLine()) != null){
                answer +=s;
            }
            //answer = answer.substring(1, answer.length() - 1);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ParseDataFromFTPServer.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readFilesNameQuestions(String listOfText) {
        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader(FILENAME_QUESTIONS +listOfText));
            String s;
            while ((s = reader.readLine()) != null){
                request += s;
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ParseDataFromFTPServer.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return request;
    }

    public static void getAnswer() {

        try {
            getTestDataAnswers("wasadmin","10.116.151.223","wasadmin");
            for (String listOfText : listOfTexts) {
                readFilesNameAnswers(listOfText);
            }
        } catch (JSchException e) {
            e.printStackTrace();
        }

        JsonObject jsonObject = new JsonParser().parse(answer).getAsJsonObject();
        for (Map.Entry<String, JsonElement> stringJsonElementEntry : jsonObject.entrySet()) {
            AiAnswers aiAnswers = GSON.fromJson(stringJsonElementEntry.getValue(), AiAnswers.class);
            aiAnswers.setId(stringJsonElementEntry.getKey());
            aiAnswersList.add(aiAnswers);
        }
    }

    public static void getQuestion() {

        try {
            getTestDataQuestions("wasadmin","10.116.151.223","wasadmin");

            for (String listOfText : listOfTexts) {
                request = "";
                readFilesNameQuestions(listOfText);
                request = request.substring(1, request.length() - 1);
                AiQuestions aiQuestions = GSON.fromJson(request, AiQuestions.class);
                aiQuestionList.add(aiQuestions);
            }
            for (int i = 0; i < aiQuestionList.size();i++){
                aiQuestionList.get(i);
            }
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }
}

