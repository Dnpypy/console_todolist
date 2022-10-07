package db;

import java.io.IOException;
import java.util.Properties;

/**
 * класс работает с properties файлами
 * application.properties
 */
public final class PropertiesUtil {

    private static final Properties PROPERTIES = new Properties();

    /**
     * отбрабатывает всего лишь один раз при первой загрузке нашего класса в память
     */
    static {
        loadProperties();
    }

    /**
     * возвращает значение по ключу из нашего ассоциативного массива
     */
    public static String get(String key){
        return PROPERTIES.getProperty(key);
    }

    /**
     * считывает наш applictaion.properties файл из нашего classpath
     */
    private static void loadProperties() {
        try (var inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private PropertiesUtil() {

    }
}
