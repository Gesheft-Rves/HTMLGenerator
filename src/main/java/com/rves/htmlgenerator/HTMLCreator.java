package com.rves.htmlgenerator;



import com.rves.htmlgenerator.pojo.Test;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.lang.reflect.Field;


public class HTMLCreator {

    {

        CONSTANT_CARD_FIELD         = getFile(DIRECTORY_HTML_FRAGMENTS + "frgCardField");
        CONSTANT_FORM_FIELD         = getFile(DIRECTORY_HTML_FRAGMENTS + "frgFormField");
        CONSTANT_LIST_FIELD_VALUE   = getFile(DIRECTORY_HTML_FRAGMENTS + "frgListFieldVal");
        CONSTANT_LIST_FIELD         = getFile(DIRECTORY_HTML_FRAGMENTS + "frgListField");

    }

    File mainResourcesDirectory                     = new File("src/main/resources");
    private static String DIRECTORY_HTML_FRAGMENTS  = "templates/doc/fragmentsHTML/";
    private  String DIRECTORY_PATH                  = (new StringBuilder(mainResourcesDirectory.getAbsolutePath()).append("templates/doc/webPages/%pathClassName%Dir/")).toString();
    private static String DIRECTORY_STATIC_HTML     = "templates/doc/staticHTML/%staticFragmentName%" ;
    private static String CONSTANT_LIST_FIELD ;
    private static String CONSTANT_LIST_FIELD_VALUE ;
    private static String CONSTANT_FORM_FIELD  ;
    private static String CONSTANT_CARD_FIELD ;


    private Writer writer = null;


    private String getFile(String fileName){
        String result = "";
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            result = IOUtils.toString(classLoader.getResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void createFolders(Class clazz) {
        StringBuilder field = new StringBuilder();
        field.append((DIRECTORY_PATH + "webPages/%pathClassName%Dir/").replace("%pathClassName%",clazz.getSimpleName().toLowerCase()));
        File theDirClazz = new File(field.toString());
        // if the directory does not exist, create it
        folderIsExists(theDirClazz);
    }

    private void folderIsExists(File f) {
        if (!f.exists()) {
            System.out.println("creating directory: " + f.getName());
            boolean result = false;

            try{
                f.mkdir();
                result = true;
            }
            catch(SecurityException se){
                //handle it
            }
            if(result) {
                System.out.println("DIR created");
            }
        }
    }


    /**
     * Создает html страницу со списком сущностей
     * @param clazz класс сущности
     */
    protected void createList(Class clazz) {
        createFolders(clazz);
        StringBuilder field = new StringBuilder(System.lineSeparator());
        StringBuilder fieldValues = new StringBuilder(System.lineSeparator());

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(DIRECTORY_PATH.replace("%pathClassName%",clazz.getSimpleName().toLowerCase())+Thread.currentThread().getStackTrace()[1].getMethodName()+".HTML"), "utf-8"));

            Field[] fields = clazz.getDeclaredFields();

            for (Field f:fields) {
                f.setAccessible(true);
                field.append(CONSTANT_LIST_FIELD.replace("%fieldName%", f.getName())).append(System.lineSeparator());
                fieldValues.append(CONSTANT_LIST_FIELD_VALUE.replace("%fieldName%", f.getName())).append(System.lineSeparator());
            }

            String result  =  getFile(DIRECTORY_STATIC_HTML.replace("%staticFragmentName%","list.txt"));

            result = result.replace("%fields%", field.toString())
                    .replace       ("%fieldsValues%", fieldValues.toString())
                    .replaceAll    ("%className%", clazz.getSimpleName())
                    .replaceAll    ("%classNameToLowerCase%", clazz.getSimpleName().toLowerCase());

            writer.write(result);
        } catch (IOException  ex) {
            System.out.println("Ups... " + ex);
        } finally {
            try {writer.close();} catch (Exception ex) {/*ignore*/}
        }
    }

    /**
     * Создает html страницу с формой сущности
     * @param clazz класс сущности
     */
    private void createForm(Class clazz) {
        createFolders(clazz);
        StringBuilder field = new StringBuilder(System.lineSeparator());

        try {

            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(DIRECTORY_PATH.replace("%pathClassName%",clazz.getSimpleName().toLowerCase())+Thread.currentThread().getStackTrace()[1].getMethodName()+".HTML"), "utf-8"));

            Field[] fields = clazz.getDeclaredFields();

            for (Field f:fields) {
                f.setAccessible(true);
                field.append(CONSTANT_FORM_FIELD.replace("%fieldName%", f.getName())).append(System.lineSeparator());
            }

            String result  =  getFile(DIRECTORY_STATIC_HTML.replace("%staticFragmentName%","form.txt"));
            result = result.replace("%fields%", field.toString())
                    .replaceAll    ("%className%", clazz.getSimpleName())
                    .replaceAll    ("%classNameToLowerCase%", clazz.getSimpleName().toLowerCase());

            writer.write(result);

        } catch (IOException  ex) {
            // Report
        } finally {
            try {writer.close();} catch (Exception ex) {/*ignore*/}
        }
    }

    /**
     * Создает html страницу с формой сущности
     * @param clazz класс сущности
     */
    private void createCard(Class clazz) {
        createFolders(clazz);
        StringBuilder field = new StringBuilder(System.lineSeparator());
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(DIRECTORY_PATH.replace("%pathClassName%",clazz.getSimpleName().toLowerCase())+Thread.currentThread().getStackTrace()[1].getMethodName()+".HTML"), "utf-8"));
            Field[] fields = clazz.getDeclaredFields();

            for (Field f:fields) {
                f.setAccessible(true);
                field.append(CONSTANT_CARD_FIELD.replace("%classNameToLowerCase%", clazz.getSimpleName().toLowerCase())
                        .replace("%className%", clazz.getSimpleName())
                        .replace("%fieldName%", f.getName())).append(System.lineSeparator());
            }

            String result  =  getFile(DIRECTORY_STATIC_HTML.replace("%staticFragmentName%","card.txt"));
            result = result.replace("%fields%", field.toString())
                    .replaceAll    ("%className%", clazz.getSimpleName())
                    .replaceAll    ("%classNameToLowerCase%", clazz.getSimpleName().toLowerCase());

            writer.write(result);
        } catch (IOException  ex) {
            // Report
        } finally {
            try {writer.close();} catch (Exception ex) {/*ignore*/}
        }
    }

    /**
     * пример вызова методов:
     */

    public static void main(String[] args) {

        HTMLCreator creator = new HTMLCreator();
        creator.createList(Test.class);
//        creator.createForm(User.class);
//        creator.createCard(User.class);
//        creator.createCard(Room.class);
    }

    /**
     * Пример сущности:
     */
    static class User {
        String username;
        String password;
    }

    static class Room {
        int no;
        String building;
    }
}

