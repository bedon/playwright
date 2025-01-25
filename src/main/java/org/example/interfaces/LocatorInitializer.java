package org.example.interfaces;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.example.annotations.FindAll;
import org.example.annotations.FindBy;
import org.example.elements.BaseLocatedElement;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface LocatorInitializer {
    default void initializeLocators(Class clazz, Page page) {
        Field[] fields = getFields(clazz);
        for (Field field : fields) {
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
            FindAll annotation = field.getAnnotation(FindAll.class);
            if (annotation != null) {
                String selector = Arrays.stream(annotation.value()).findFirst().get().selector();
                BaseLocatedElement element = createLocatableInstance(BaseLocatedElement.class, page, selector);
                field.set(this, element);
            }
        }
    }

    private void handleFindBy(Page page, Field field) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        if (field.isAnnotationPresent(FindBy.class)) {
            String selector = field.getAnnotation(FindBy.class).selector();

            if (field.getType().equals(List.class)) {
                ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                Type[] types = parameterizedType.getActualTypeArguments();

                if (types.length > 0) {
                    Class<?> actualType = (Class<?>) types[0];

                    if (actualType.equals(Locator.class)) {
                        field.set(this, page.locator(selector).all());
                    } else if (actualType.equals(Locatable.class)) {
                        List<BaseLocatedElement> baseLocatedElementList = page.locator(selector).all().stream().map(locator -> createLocatableInstance(field.getType(), page, selector)).collect(Collectors.toList());
                        field.set(this, baseLocatedElementList);
                    }
                }
            } else if (Locatable.class.isAssignableFrom(field.getType())){
                field.set(this, createLocatableInstance(field.getType(), page, selector));
            } else if (Locator.class.isAssignableFrom(field.getType())) {
                field.set(this, page.locator(selector));
            }
        } else if (field.getType().equals(List.class)) {

        } else if (Locatable.class.isAssignableFrom(field.getType())) {
            Class<?> fieldType = field.getType();
            String selector = fieldType.getAnnotation(FindBy.class).selector();
            field.set(this, createLocatableInstance(field.getType(), page, selector));
        }
    }

    private BaseLocatedElement createLocatableInstance(Class<?> clazz, Page page, String selector) {
        try {
            return (BaseLocatedElement) clazz.getDeclaredConstructor(Page.class, String.class).newInstance(page, selector);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании экземпляра Locatable: " + clazz.getName(), e);
        }
    }

    private Field[] getFields(Class clazz) {
        List<Field> fields = new ArrayList<>();

        while (clazz.getSuperclass() != null) {
            Field[] parentFields = clazz.getDeclaredFields();
            for (Field field : parentFields) {
                fields.add(field);
            }
            clazz = clazz.getSuperclass();
            getFields(clazz);
        }

        return fields.toArray(new Field[0]);
    }
}
