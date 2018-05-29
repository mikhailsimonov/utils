package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Инуструмент коннекта и выполнения insert(с возможность выполнять в будущем и др запросы) запросов в БД MsSql
 * @author Simonov-Ms
 */
public class ConnectMSSQLServer
{
    static int id;
    static String type;
    static String text;
    static String tableName;
    static String tableNameId;
    public static String question;
    public static String answer;

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectMSSQLServer.class.getName());

    public static String getQuestion() {
        return question;
    }

    public static void setQuestion(String question) {
        ConnectMSSQLServer.question = question;
    }

    public static String getAnswer() {
        return answer;
    }

    public static void setAnswer(String answer) {
        ConnectMSSQLServer.answer = answer;
    }

    public void dbUpdate(String db_connect_string,
                         String db_userid,
                         String db_password)
    {
        if (tableName == "ai_questions"){
            tableNameId = "[question_id]";
        }else if(tableName == "ai_answer"){
            tableNameId = "[answer_id]";
        }
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(db_connect_string,
                    db_userid, db_password);
            Statement statement = conn.createStatement();

            String queryString ="INSERT INTO "+ tableName +" ([type],"+ tableNameId +",[text]) VALUES";
            queryString +="('"+ type + "'," + id + ",'" + text + "')";

            LOGGER.info("save to DB in table: " + tableName + ",\nid: " + id + "\ntype: " + type + "\ndata: " + text);
            statement.executeUpdate(queryString);
           /* while (rs.next()) {
                System.out.println("success");
                //System.out.println(rs.getString(1));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param db_connect_string
     * @param db_userid
     * @param db_password
     */
    public static void dbTruncate(String db_connect_string,
                          String db_userid,
                          String db_password)
    {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(db_connect_string,
                    db_userid, db_password);
            System.out.println("connected");
            Statement statement = conn.createStatement();
            String queryString ="TRUNCATE TABLE " + tableName;
            statement.executeUpdate(queryString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * функция выполняет селект в бд MSSQL
     * @param db_connect_string
     * @param db_userid
     * @param db_password
     * @param request
     */
    public static void dbSelect( String db_connect_string,
                          String db_userid,
                          String db_password,
                          String request)
    {
        if (tableName == "ai_questions"){
            tableNameId = "[question_id]";
        }else if(tableName == "ai_answer"){
            tableNameId = "[answer_id]";
        }
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(db_connect_string,
                    db_userid, db_password);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(request);

             while (rs.next()) {
                setQuestion(rs.getString("question"));
                setAnswer(rs.getString("answer"));
                 System.out.println("success");
            }
            System.out.println(getQuestion() + "\n" + getAnswer());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
