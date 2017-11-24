package com.airport.ticket.bms.util;

/**
 * JSONUtils.java 2011-03-08 09:40:08
 */

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.io.StringWriter;

/**
 * JSON转换处理工具类
 *
 * @author
 * @created
 */
public class JsonUtils {

    /** 日志记录者 */
    private static Log logger = LogFactory.getLog(JsonUtils.class);

    /**
     * 数组格式JSON串转换为ObjectList对象
     *
     * @param <T>
     * @param json
     *            JSON字符串
     * @param tr
     *            TypeReference,例如: new TypeReference< List<Album> >(){}
     * @return ObjectList对象
     */
    public static <T> T jsonToGenericObject(String json, TypeReference<T> tr) {

        if (StringUtils.isEmpty(json)) {
            return null;
        } else {

            // Jackson方式将Json转换为对象
            MappingJsonFactory f = new MappingJsonFactory();
            try {
                JsonParser parser = f.createJsonParser(json);
                return parser.readValueAs(tr);

            } catch (Exception e) {

                if (logger.isWarnEnabled()) {
                    logger.warn("将JSON[" + json + "]转换成泛型[" + tr.getType()
                            + "]出现异常!", e);
                }

                return null;
            }
        }
    }

    /**
     * JSON转换为Object对象
     *
     * @param json
     *            JSON字符串
     * @param clazz
     *            要转换成的类的类型
     * @return Object对象
     */
    public static <T> T jsonToObject(String json, Class<T> clazz) {

        if (StringUtils.isEmpty(json)) {
            return null;

        } else {

            // JSON转换为对象
            MappingJsonFactory f = new MappingJsonFactory();
            try {
                JsonParser parser = f.createJsonParser(json);
                return parser.readValueAs(clazz);
            } catch (Exception e) {

                if (logger.isWarnEnabled()) {
                    logger.warn(
                            "将JSON[" + json + "]转换成["
                                    + clazz.getCanonicalName() + "]出现异常!", e);
                }

                return null;
            }
        }
    }

    /**
     * Object对象转换为JSON格式 例如List对象、JavaBean对象、JavaBean对象数组、Map对象、List Map对象
     *
     * @param object
     *            传入的Object对象
     * @return object的JSON格式字符串
     */
    public static String toJson(Object object) {

        // 将对象转为JSON
        MappingJsonFactory f = new MappingJsonFactory();
        StringWriter sw = new StringWriter();

        try {
            JsonGenerator generator = f.createJsonGenerator(sw);
            generator.writeObject(object);
            generator.close();

        } catch (Exception e) {

            if (logger.isWarnEnabled()) {
                logger.warn("将对象转换成JSON出现异常!", e);
            }

            return "";
        }

        return sw.toString();

    }

    /**
     * 数组格式JSON串转换为ObjectList对象
     *
     * @param <T>
     * @param json
     *            JSON字符串
     * @param tr
     *            TypeReference,例如: new TypeReference< List<Album> >(){}
     * @return ObjectList对象
     */
    public static <T> T jsonToGeneric(String json, TypeReference<T> tr) {

        if (StringUtils.isEmpty(json)) {
            return null;

        } else {
            // 将Json转换为对象
            MappingJsonFactory f = new MappingJsonFactory();

            try {
                JsonParser parser = f.createJsonParser(json);
                return parser.readValueAs(tr);
            } catch (Exception e) {

                logger.warn("JSON格式转换为泛型对象出现异常!", e);

                return null;
            }
        }
    }

    /**
     * 得到节点值数组
     *
     * @param json
     *            JSON字符串
     * @param nodeKey
     *            想得到节点的名称
     */
    public static String getNodeValue(String json, String nodeKey) {

        if (StringUtils.isEmpty(json)) {
            return "";
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode readTree = objectMapper.readTree(json);
                // path 返回空 get 异常
                return readTree.path(nodeKey).textValue();
            } catch (Exception e) {
                logger.warn("JSON格式取节点异常!", e);
                return "";
            }
        }
    }
    public static void main(String[] args) {
        String json = "{\"signCert\":\"MIIEOzCCAyOgAwIBAgIQU4EVoM72cLy2CrOrRMiN0zANBgkqhkiG9w0BAQUFADB7MQswCQYDVQQGEwJDTjEkMCIGA1UEChMbTkVUQ0EgQ2VydGlmaWNhdGUgQXV0aG9yaXR5MR8wHQYDVQQLExZPcmdhbml6YXRpb24gQ2xhc3NBIENBMSUwIwYDVQQDExxORVRDQSBPcmdhbml6YXRpb24gQ2xhc3NBIENBMB4XDTA4MDIyNjA2MzczNVoXDTEzMDIyNjA2MzczNVowgZwxCzAJBgNVBAYTAkNOMRIwEAYDVQQIEwlHdWFuZ2RvbmcxFTATBgNVBAceDFQJeWWN7wA4ADBT9zEZMBcGA1UECh4QXn9d3l4CV85eAonEUhJcQDEVMBMGA1UECx4MepdT42cNUqFOulRYMQ8wDQYDVQQDHgZPVWcIZg4xHzAdBgkqhkiG9w0BCQEWEHdpbjUyMDdAc2luYS5jb20wgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAJ33sbXktcERYmN7zloOpgO7/0APEqp3DLH+QKVOZ/Pgn4sZJocN7R2uCAShVsMvxZ9lQhwtIbGrlHTcwElH3xQIykW6bz41cvKqe3NUB/VncvzlOYhlWdUYmeSRYpjIY2gK/3/eGSTFdZt8zRmuCmRiashE65Fzy7Nu0o6RBLAtAgMBAAGjggEbMIIBFzAfBgNVHSMEGDAWgBQtCUOC+UpXaoQmsHX3s6vU+dzBbzAdBgNVHQ4EFgQUgvX8ZzpAup6qnYHD6di6g+pmVBUwVwYDVR0gBFAwTjBMBgorBgEEAYGSSAEKMD4wPAYIKwYBBQUHAgEWMGh0dHA6Ly93d3cuY25jYS5uZXQvY3Mva25vd2xlZGdlL3doaXRlcGFwZXIvY3BzLzAbBgNVHREEFDASgRB3aW41MjA3QHNpbmEuY29tMAwGA1UdEwEB/wQCMAAwDgYDVR0PAQH/BAQDAgbAMEEGA1UdHwQ6MDgwNqA0oDKGMGh0dHA6Ly9jbGFzc2FjYTEuY25jYS5uZXQvY3JsL09yZ2FuaXphdGlvbkNBLmNybDANBgkqhkiG9w0BAQUFAAOCAQEAp9Z6smtfS8sSBrdOxn6dTe11/JPTCvQ18h1SrQW2z//sv1bk3k8LB9An4qd/4gQNiuEcyQG37xoR/TUzDI6bDL1BdrJI0Xi3tXR9uGgum9WzgHd0rWS5zYEM273odVfz6VDl+0E85syHaGj30vQFOiIc4/IxZEM6XUBigJTT/cTWoZcZTpK43+GkimkDzz0mi7se+tmwZVyoUL8RP97pFiXiEuk5+KYR5brYthUjJ15f1Qtc4O+b1C5sPLuscU9csK9GRfTJyVN21Tfnmu5Zy4xLQKmMqWuaO3Bz8Kt++zbSoU73WUx4vWVYofRIH4TM+XfAQCEYDUUUvpeyxUkjOg==\",\"encodeCert\":\"xx\",\"pin\":\"621168\",\"userId\":\"18814141111\",\"uuid\":\"8d9b55a2-9fa3-400e-8fe7-30cda27b248c\",\"signData\":\"JAQpfkDOiui44MC3+6DeZHSdIavpGeIwSiv9OnvYy+raRdXpOeWFD1w3kVBlrMM2LDkwGpFiFBnrR07f9AR6H6ALhFAioGj1+3dxiARZdhz/IUqLvcFqMEQCv24r6L0h0v9jbK2hFTaUjS1tXkjsHBv11yBEAdTVkB2ra6uwjIs=\",\"caName\":\"G4bCaServiceImpl\"}";
        String json1 ="{\"type\":\"2\",\"signCert\":\"MIIEFTCCAv2gAwIBAgIUZhnl+jF4T5tmRY8mirw9UWDjLv8wDQYJKoZIhvcNAQEFBQAwUTEYMBYGA1UEAwwPaG5fdG9wY2FfdXNlcmNhMRgwFgYDVQQLDA/mtYvor5Xpg6jor5XnlKgxGzAZBgNVBAoMEuWkqeivmuWuieS/oeivleeUqDAeFw0xNjA2MDMwOTI2MjJaFw0xODA2MDMwOTI2MjJaMEIxFDASBgNVBAMMCzE4ODc5MTU0MjE0MRQwEgYDVQQLDAtobl90b3BjYV9yYTEUMBIGA1UECgwLaG5fdG9wY2FfcmEwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAIn8mwNGi2QASaNf8dsKtnD5nRuArWCgaTLZV8lxkw8zDvZ9jxkYyYN7MdJZKcixyiXWtJ2EeZGye+2lAxu0YxDfcSUluLxo7Z1sbzumPvXJOon2njvyxVWmidqd+Eet4e7J/bcnG80JmdIEvazQMRRzz/t/lKOHnOgi/70j8BGLAgMBAAGjggF2MIIBcjAJBgNVHRMEAjAAMAsGA1UdDwQEAwIFoDCBgwYIKwYBBQUHAQEEdzB1MHMGCCsGAQUFBzAChmdodHRwOi8vdG9wY2Euc3ppdHJ1cy5jb20uY24vdXNlckVucm9sbC9jYUNlcnQ/Y2VydFNlcmlhbE51bWJlcj02MEZGNUREMTI4QjlENDA3QzA2QUE1MDg0M0E4MjdENTY3NzNGNUE5MGgGA1UdLgRhMF8wXaBboFmGV2h0dHA6Ly90b3BjYS5zeml0cnVzLmNvbS5jbi9wdWJsaWMvaXRydXNjcmw/Q0E9NjBGRjVERDEyOEI5RDQwN0MwNkFBNTA4NDNBODI3RDU2NzczRjVBOTBoBgNVHR8EYTBfMF2gW6BZhldodHRwOi8vdG9wY2Euc3ppdHJ1cy5jb20uY24vcHVibGljL2l0cnVzY3JsP0NBPTYwRkY1REQxMjhCOUQ0MDdDMDZBQTUwODQzQTgyN0Q1Njc3M0Y1QTkwDQYJKoZIhvcNAQEFBQADggEBAAXjAzhqJz16duUsX401jN0Na9D2j1fGjDOAWWw7dUu1GrKOH8+RsM7QUp9j3C1GvG4MuJSN8PpsjViytCcs6Jz0l9kJ3fj8kOejFkMckIKjN3yl4KxhaGsXynjpdEndIHR2SxIzi1pAN69mldkl8EP3ElzUzdKzx60fKV10AKOlrpbHGn7p42T2WzOikDUmebh4ARJEwSFQx5E94nJ2HwpC4qIEVy0fG2OtlT9V9KHnTJ1gxB4Ye0oI7PMivgH2xu6JCi+ExkUMI0HJqLPMUxx7jQbmpACFOw+CX7FdVJYy5g2scZ+6G0UTJlXXwNsn1v0d0k6TF3A6aQ9Ou+jLBhA=\",\"encodeCert\":\"xx\",\"pin\":\"123456\",\"userId\":\"18979154214\",\"uuid\":\"75bc30f0-cb34-436f-aab3-3c2394b92a98\",\"caName\":\"G4bCaServiceImpl\",\"subject\":\"C=CN,CN=预注册,EmailAddress=yuzhuce@gzemail.cn\",\"signData\"=\"chs6iF5ryeoqm2CZ44fFGCKRPtgOLdFeBNLP907RhFo7HpBkvbrBjS9FygJZX+470nMZbE9o6PlVCmsN81bEvpRqNMSwNelYO1Mxb9/VWZ7xN8+q1UYjGz4Pjjp9OhiP7utPWbv/vcJjT0dW95Z7qMuhoDZMmgh3C6BVmADDwkU=\"}";
        String json2 = "{\"name\":\"hello\"}";
//		System.out.println(JsonUtils.getNodeValue(json2, "name"));
    }
}