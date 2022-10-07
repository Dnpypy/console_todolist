package locale_package;

import java.util.Locale;
import java.util.ResourceBundle;

public enum MessageManager {
    EN(ResourceBundle.getBundle("TestBundle", new Locale("en", "US"))),
    RU(ResourceBundle.getBundle("TestBundle", new Locale("ru", "RU")));
    private ResourceBundle bundle;

    MessageManager(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public String getString(String key) {
        return bundle.getString(key);
    }
}
