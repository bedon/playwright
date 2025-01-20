package org.example.interfaces;

import com.microsoft.playwright.Page;
import org.example.annotations.FindBy;
import org.example.elements.BaseLocatedElement;

import java.lang.reflect.Field;

public interface LocatorInitializer {
    default void initializeLocators(Object instance, Page page) {
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.isAnnotationPresent(FindBy.class)) {
                    FindBy annotation = field.getAnnotation(FindBy.class);
                    String selector = annotation.selector();
                    field.set(instance, page.locator(selector));
                }
                else if (Locatable.class.isAssignableFrom(field.getType())) {
                    Object childInstance = field.get(instance);
                    if (childInstance == null) {
                        childInstance = field.getType().getDeclaredConstructor(Page.class).newInstance(page);
                        field.set(instance, childInstance);
                    }
                    FindBy classAnnotation = field.getType().getAnnotation(FindBy.class);
                    if (classAnnotation != null && childInstance instanceof BaseLocatedElement) {
                        String selector = classAnnotation.selector();
                        ((BaseLocatedElement) childInstance).initLocator(page.locator(selector));
                    }
                    initializeLocators(childInstance, page);
                }
            } catch (Exception e) {
                throw new RuntimeException("Не удалось инициализировать поле: " + field.getName(), e);
            }
        }
    }
}
