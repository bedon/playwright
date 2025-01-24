package org.example.interfaces;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.example.annotations.FindAll;
import org.example.annotations.FindBy;
import org.example.elements.BaseLocatedElement;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface LocatorInitializer {
    default void initializeLocators(Class clazz, Page page) {
        for (Field field : clazz.getDeclaredFields()) {
            try {
                if (field.trySetAccessible() && field.get(this) == null) {
                    handleFindBy(page, field);
                    handleFindAll(page, field);
                }
            } catch (Exception e) {
                throw new RuntimeException("Не удалось инициализировать поле: " + field.getName(), e);
            }
        }
    }

    private void handleFindAll(Page page, Field field) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        if (field.isAnnotationPresent(FindAll.class)) {
            FindAll annotation = field.getAnnotation(FindAll.class);
            List<Locator> locators = Arrays.stream(annotation.value()).map(locator -> page.locator(locator.selector())).collect(Collectors.toList());
            if (field.getType().equals(Locator.class)) {
                field.set(this, locators.get(0));
            } else if (field.getType().equals(List.class)) {
                field.set(this, locators);
            }
        } else if (Locatable.class.isAssignableFrom(field.getType())) {
            Class<?> fieldType = field.getType();
            FindAll annotation = field.getAnnotation(FindAll.class);
            String selector = Arrays.stream(annotation.value()).findFirst().get().selector();
            if (annotation != null) {
                BaseLocatedElement element = (BaseLocatedElement) fieldType
                        .getDeclaredConstructor(Page.class, String.class)
                        .newInstance(page, selector);
                field.set(this, element);
            }
        }
    }

    private void handleFindBy(Page page, Field field) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
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
        } else if (field.getType().equals(List.class)) {
            //TODO realize list returned
        }
    }
}
