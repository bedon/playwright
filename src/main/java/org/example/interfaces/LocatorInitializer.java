package org.example.interfaces;

import com.microsoft.playwright.Page;
import org.example.annotations.FindBy;
import org.example.elements.BaseLocatedElement;

import java.lang.reflect.Field;

public interface LocatorInitializer {
    default void initializeLocators(Class clazz, Page page) {
        for (Field field : clazz.getDeclaredFields()) {
            try {
                if (field.trySetAccessible() && field.get(this) == null) {
                    if (field.isAnnotationPresent(FindBy.class)) {
                        FindBy annotation = field.getAnnotation(FindBy.class);
                        field.set(this, page.locator(annotation.selector()));
                    } else if (Locatable.class.isAssignableFrom(field.getType())) {
                        Class<?> fieldType = field.getType();
                        FindBy annotation = fieldType.getAnnotation(FindBy.class);
                        if (annotation != null) {
                            BaseLocatedElement element = (BaseLocatedElement) fieldType
                                    .getDeclaredConstructor(Page.class, String.class)
                                    .newInstance(page, annotation.selector());
                            field.set(this, element);
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("Не удалось инициализировать поле: " + field.getName(), e);
            }
        }
    }
}
