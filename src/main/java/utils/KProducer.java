package utils;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sbt.qa.bdd.AutotestError;

import java.util.Properties;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import static ru.sbt.qa.bot900.utils.Commons.attachText;

/**
 * Created by Simonov-MS on 26.02.2018.
 */

/**
 *
 *Этот инструмент отправляет сообщения в очередь
 */

public class KProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KProducer.class.getName()); //объявляем логер для логирования
    public static Future<RecordMetadata> kafkaSend(String url, String topic, String key, String message){
    //settings
    Properties properties = new Properties();
    properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, url);
    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, key);
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    //producer
    Producer <String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer<String, String>(properties);
    //send message
    Future<RecordMetadata>  response =  producer.send(new ProducerRecord<String, String>(topic, message), new Callback() {

        @Override
        public void onCompletion(RecordMetadata metadata, Exception exception) {
            LOGGER.info("check callback after send message in qwe");
            attachText("info", "check callback after send message in qwe");
            if (exception != null) { //проверяю что запрос успешно отправился в очередь
                exception.printStackTrace();
                LOGGER.info("Exception :");
                attachText("info", "Exception :");
                throw new AutotestError(exception.getMessage());

            }else{
                LOGGER.info("message flew into qwe successfully");
                attachText("info", "message flew into qwe successfully");
            }
        }
    });
    //end
    producer.close();
    return response;
}}
