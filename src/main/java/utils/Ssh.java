package utils;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sbt.qa.bot900.stepsdefs.SberMessHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @author Simonov-MS
 */

public class Ssh {

    private static final Logger LOGGER = LoggerFactory.getLogger(Ssh.class.getName());
    private static final String folderAnswer ="src/test/resources/answers";
    private static final String folderQuestion ="src/test/resources/questions";

    static List<String> listOfTexts = new ArrayList<>();

    /**
     * @param login
     * @param url
     * @param pass
     * @throws JSchException
     */

    public static void getTestDataAnswers(String login, String url, String pass) throws JSchException {
        try {
            JSch ssh = new JSch();
            //обращаемся к серверу по shh протоколу
            Session session = ssh.getSession(login, url, 22);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");

            //коннектимся
            session.setConfig(config);
            session.setPassword(pass);
            session.connect();

            //по sftp проходим в директорию
            Channel channel = session.openChannel("sftp");
            channel.connect();

            ChannelSftp sftp = (ChannelSftp) channel;
            sftp.cd("/ai/configs/sb900-external-configs/references/");

            //очищаем папку от прежних данных
            truncateFolder(folderAnswer);

            Vector filelist = sftp.ls("/ai/configs/sb900-external-configs/references/");
            for (int i=0; i<filelist.size();i++){
                ChannelSftp.LsEntry lsEntry = (ChannelSftp.LsEntry) filelist.get(i);
                listOfTexts.add(lsEntry.getFilename().replaceAll("\\?", " del "));
            }

            for (int i = 0; i < listOfTexts.size(); i++){
                listOfTexts.removeIf(p -> !p.equals("faq_answers.json"));//удалить из масива ненужные нам записи
                System.out.println(listOfTexts.get(i));
                sftp.get("/ai/configs/sb900-external-configs/references/"+listOfTexts.get(i),
                        "src/test/resources/answers");
            }
            // /ai/configs/sb900-external-configs/references
            Boolean success = true;

            if (success) {
                LOGGER.info("file download. success!");
            }else {
                LOGGER.error("ERROR");
            }

            channel.disconnect();
            session.disconnect();
        } catch (JSchException e) {
            System.out.println(e.getMessage().toString());
            e.printStackTrace();
        } catch (SftpException e) {
            System.out.println(e.getMessage().toString());
            e.printStackTrace();
        }
    }

    /**
     * @param login
     * @param url
     * @param pass
     * @throws JSchException
     */

    public static void getTestDataQuestions(String login, String url, String pass) throws JSchException {
        try {
            JSch ssh = new JSch();
            //обращаемся к серверу по shh протоколу
            Session session = ssh.getSession(login, url, 22);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");

            //коннектимся
            session.setConfig(config);
            session.setPassword(pass);
            session.connect();

            //по sftp проходим в директорию
            Channel channel = session.openChannel("sftp");
            channel.connect();

            ChannelSftp sftp = (ChannelSftp) channel;
            sftp.cd("/ai/configs/sb900-external-configs/workdata/original_files/faq/");

            //очищаем папку от прежних данных
            truncateFolder(folderQuestion);

            Vector filelist = sftp.ls("/ai/configs/sb900-external-configs/workdata/original_files/faq/");
            for (int i=0; i<filelist.size();i++){
                ChannelSftp.LsEntry lsEntry = (ChannelSftp.LsEntry) filelist.get(i);
                listOfTexts.add(lsEntry.getFilename());
            }

            for (int i = 0; i < listOfTexts.size(); i++){
                listOfTexts.removeIf(p -> p.equals(".") || p.equals("..") || p.equals("unused"));//удалить из масива ненужные нам записи
                System.out.println(listOfTexts.get(i));
                sftp.get("/ai/configs/sb900-external-configs/workdata/original_files/faq/"+listOfTexts.get(i),
                        "src/test/resources/questions");
            }
            // /ai/configs/sb900-external-configs/references
            Boolean success = true;

            if (success) {
                LOGGER.info("file download. success!");
            }else {
                LOGGER.error("ERROR");
            }
            channel.disconnect();
            session.disconnect();
        } catch (JSchException e) {
            System.out.println(e.getMessage().toString());
            e.printStackTrace();
        } catch (SftpException e) {
            System.out.println(e.getMessage().toString());
            e.printStackTrace();
        }
    }

    /**
     * @param path
     */

    public static void truncateFolder(String path) {
        for (File file : new File(path).listFiles()){
            if(file.isFile()) file.delete();
        }
    }
}
