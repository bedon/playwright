package org.example.interfaces;

import com.microsoft.playwright.Page;
import org.example.annotations.FindBy;
import org.example.elements.BaseLocatedElement;

import java.lang.reflect.Field;

public interface LocatorInitializer {
    default void initializeLocators(Class clazz, Page page) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.get(this) == null) {
                    if (field.isAnnotationPresent(FindBy.class)) {
                        FindBy annotation = field.getAnnotation(FindBy.class);
                        String selector = annotation.selector();
                        field.set(this, page.locator(selector));
                    } else if (Locatable.class.isAssignableFrom(field.getType())) {
                        Class<?> chieldClass = field.getType();
                        FindBy annotation = chieldClass.getAnnotation(FindBy.class);
                        String selector = annotation.selector();
                        BaseLocatedElement element = (BaseLocatedElement) chieldClass.getDeclaredConstructor(Page.class, String.class).newInstance(page, selector);
                        chieldClass.cast(element);
                        field.set(this, element);
                    }
                }
            } catch (Exception e) {
                e.getMessage();
                throw new RuntimeException("Не удалось инициализировать поле: " + field.getName(), e);
            }
        }
    }
}
