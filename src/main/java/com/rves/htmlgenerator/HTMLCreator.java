package com.rves.htmlgenerator;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.lang.reflect.Field;


public class HTMLCreator {

    private static final String DIRECTORY_HTML_FRAGMENTS  = "templates/doc/fragmentsHTML/";
    private static final File mainResourcesDirectory      = new File("src/main/resources");
    private static final String DIRECTORY_PATH            = (new StringBuilder(mainResourcesDirectory.getAbsolutePath()).append("\\templates\\doc\\webPages\\%pathClassName%Dir\\")).toString();
    private static final String DIRECTORY_STATIC_HTML     = "templates/doc/staticHTML/%staticFragmentName%" ;
    private static final String LIST_FIELD                = getFile(DIRECTORY_HTML_FRAGMENTS + "frgListField");
    private static final String LIST_FIELD_VALUE          = getFile(DIRECTORY_HTML_FRAGMENTS + "frgListFieldVal");
    private static final String FORM_FIELD                = getFile(DIRECTORY_HTML_FRAGMENTS + "frgFormField");
    private static final String CARD_FIELD                = getFile(DIRECTORY_HTML_FRAGMENTS + "frgCardField");


    private Writer writer ;


    private static String getFile(String fileName){
        String result = "";
        ClassLoader classLoader = HTMLCreator.class.getClassLoader();
        try {
            result = IOUtils.toString(classLoader.getResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void createFolders(Class clazz) {
        StringBuilder field = new StringBuilder();
        String filePath = field.append((DIRECTORY_PATH ).replace("%pathClassName%",clazz.getSimpleName().toLowerCase())).toString();
        File directory = new File(filePath);
        if (!directory.exists()) directory.mkdir();
    }

    /**
     * Создает html страницу со списком сущностей
     * @param clazz класс сущности
     */
    private void createListPage(Class clazz) {
        createFolders(clazz);
        StringBuilder field = new StringBuilder(System.lineSeparator());
        StringBuilder fieldValues = new StringBuilder(System.lineSeparator());
        StringBuilder path = new StringBuilder();

        path
                .append(DIRECTORY_PATH.replace("%pathClassName%",clazz.getSimpleName().toLowerCase()))
                .append(Thread.currentThread().getStackTrace()[1].getMethodName())
                .append(".HTML");

        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path.toString()), "utf-8"));

            Field[] fields = clazz.getDeclaredFields();

            for (Field f:fields) {
                f.setAccessible(true);

                field
                        .append(LIST_FIELD.replace("%fieldName%", f.getName()))
                        .append(System.lineSeparator());

                fieldValues
                        .append(LIST_FIELD_VALUE.replace("%fieldName%", f.getName()))
                        .append(System.lineSeparator());
            }

            String result  =  getFile(DIRECTORY_STATIC_HTML.replace("%staticFragmentName%","list.txt"));

            result = result.replace("%fields%", field.toString())
                    .replace       ("%fieldsValues%", fieldValues.toString())
                    .replaceAll    ("%className%", clazz.getSimpleName())
                    .replaceAll    ("%classNameToLowerCase%", clazz.getSimpleName().toLowerCase());

            writer.write(result);
        } catch (IOException  ex) {
            ex.printStackTrace();

        } finally {
            try {writer.close();} catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Создает html страницу с формой сущности
     * @param clazz класс сущности
     */
    private void createFormPage(Class clazz) {
        createFolders(clazz);
        StringBuilder field = new StringBuilder(System.lineSeparator());
        StringBuilder path = new StringBuilder();

        path
                .append(DIRECTORY_PATH.replace("%pathClassName%",clazz.getSimpleName().toLowerCase()))
                .append(Thread.currentThread().getStackTrace()[1].getMethodName())
                .append(".HTML");

        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path.toString()), "utf-8"));

            Field[] fields = clazz.getDeclaredFields();

            for (Field f:fields) {
                f.setAccessible(true);

                field
                        .append(FORM_FIELD.replace("%fieldName%", f.getName()))
                        .append(System.lineSeparator());
            }

            String result  =  getFile(DIRECTORY_STATIC_HTML.replace("%staticFragmentName%","form.txt"));
            result = result.replace("%fields%", field.toString())
                    .replaceAll    ("%className%", clazz.getSimpleName())
                    .replaceAll    ("%classNameToLowerCase%", clazz.getSimpleName().toLowerCase());

            writer.write(result);

        } catch (IOException  ex) {
            ex.printStackTrace();
        } finally {
            try {writer.close();} catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Создает html страницу с формой сущности
     * @param clazz класс сущности
     */
    private void createCardPage(Class clazz) {
        createFolders(clazz);
        StringBuilder field = new StringBuilder(System.lineSeparator());
        StringBuilder path = new StringBuilder();

        path
                .append(DIRECTORY_PATH.replace("%pathClassName%",clazz.getSimpleName().toLowerCase()))
                .append(Thread.currentThread().getStackTrace()[1].getMethodName())
                .append(".HTML");

        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path.toString()), "utf-8"));

            Field[] fields = clazz.getDeclaredFields();

            for (Field f:fields) {
                f.setAccessible(true);
                field.append(CARD_FIELD.replace("%classNameToLowerCase%", clazz.getSimpleName().toLowerCase())
                        .replace("%className%", clazz.getSimpleName())
                        .replace("%fieldName%", f.getName())).append(System.lineSeparator());
            }

            String result  =  getFile(DIRECTORY_STATIC_HTML.replace("%staticFragmentName%","card.txt"));
            result = result.replace("%fields%", field.toString())
                    .replaceAll    ("%className%", clazz.getSimpleName())
                    .replaceAll    ("%classNameToLowerCase%", clazz.getSimpleName().toLowerCase());

            writer.write(result);
        } catch (IOException  ex) {
            ex.printStackTrace();
        } finally {
            try {writer.close();} catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}

