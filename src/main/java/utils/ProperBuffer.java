package utils;

import ru.sbt.qa.bdd.AutotestError;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Simonov-MS on 26.02.2018.
 */

/**
 * Получает настройки из Property
 */

public class ProperBuffer {
    Properties prop = new Properties();
    private String server = null;
    private String to_ai_topic = null;
    private String aih_topic = null;
    private String from_ai_topic = null;
    private String pa_topic = null;
    private String sberTopic = null;
    private String votchTopic = null;
    private String group_id = null;
    private String url = null;
    private String qa_mock = null;

    public String kafkaServer() {
        try {
            prop.load(new FileInputStream("src/test/resources/config/application.properties"));
            server = prop.getProperty("KAFKA_SERVER");
        } catch (IOException e) {
            e.printStackTrace();
            throw new AutotestError(e.getMessage());
        }
        return server;
    }
    public String paTopic() {
        try {
            prop.load(new FileInputStream("src/test/resources/config/application.properties"));
            pa_topic = prop.getProperty("PA_TOPIC");
        } catch (IOException e) {
            e.printStackTrace();
            throw new AutotestError(e.getMessage());
        }
        return pa_topic;
    }
    public String gorupId() {
        try {
            prop.load(new FileInputStream("src/test/resources/config/application.properties"));
            group_id = prop.getProperty("CONSUMER_GROUP_ID");
        } catch (IOException e) {
            e.printStackTrace();
            throw new AutotestError(e.getMessage());
        }
        return group_id;
    }
    public String toAiTopic() {
        try {
            prop.load(new FileInputStream("src/test/resources/config/application.properties"));
            to_ai_topic = prop.getProperty("TO_AI_TOPIC");
        } catch (IOException e) {
            e.printStackTrace();
            throw new AutotestError(e.getMessage());
        }
        return to_ai_topic;
    }
    public String aihTopic() {
        try {
            prop.load(new FileInputStream("src/test/resources/config/application.properties"));
            aih_topic = prop.getProperty("AIH_TOPIC");
        } catch (IOException e) {
            e.printStackTrace();
            throw new AutotestError(e.getMessage());
        }
        return aih_topic;
    }
    public String fromAiTopic() {
        try {
            prop.load(new FileInputStream("src/test/resources/config/application.properties"));
            from_ai_topic = prop.getProperty("FROM_AI_TOPIC");
        } catch (IOException e) {
            e.printStackTrace();
            throw new AutotestError(e.getMessage());
        }
        return from_ai_topic;
    }
    public String sberTopic() {
        try {
            prop.load(new FileInputStream("src/test/resources/config/application.properties"));
            sberTopic = prop.getProperty("SBER_TOPIC");
        } catch (IOException e) {
            e.printStackTrace();
            throw new AutotestError(e.getMessage());
        }
        return sberTopic;
    }
    public String votchTopic() {
        try {
            prop.load(new FileInputStream("src/test/resources/config/application.properties"));
            votchTopic = prop.getProperty("VOTCH_TOPIC");
        } catch (IOException e) {
            e.printStackTrace();
            throw new AutotestError(e.getMessage());
        }
        return votchTopic;
    }
    public String sberMesUrl() {
        try {
            prop.load(new FileInputStream("src/test/resources/config/application.properties"));
            url = prop.getProperty("SBERMESS_HANDLER");
        } catch (IOException e) {
            e.printStackTrace();
            throw new AutotestError(e.getMessage());
        }
        return url;
    }
    public String qaMock() {
        try {
            prop.load(new FileInputStream("src/test/resources/config/application.properties"));
            qa_mock = prop.getProperty("QA_MOCK");
        } catch (IOException e) {
            e.printStackTrace();
            throw new AutotestError(e.getMessage());
        }
        return qa_mock;
    }
}





