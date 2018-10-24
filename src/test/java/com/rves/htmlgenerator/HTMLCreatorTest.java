package com.rves.htmlgenerator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;


public class HTMLCreatorTest {
    File testResourcesDirectory = new File("src/test/resources");
    File mainResourcesDirectory = new File("src/main/resources");
    private String DIRECTORY_ACTUAL_FILE   = (new StringBuilder(mainResourcesDirectory.getAbsolutePath()).append("/templates/doc/webPages/")).toString();
    private String DIRECTORY_EXPECTED_FILE = testResourcesDirectory.getAbsolutePath();

    HTMLCreator creator = new HTMLCreator();


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

    @Test
    public void createListTest() {
        creator.createList(com.rves.htmlgenerator.pojo.Test.class);

        File actualFile = new File(DIRECTORY_ACTUAL_FILE + "testDir/createList.HTML");
        File expectedFile = new File(new StringBuilder().append(DIRECTORY_EXPECTED_FILE).append("/").append("createList.HTML").toString());

        comparePages(actualFile, expectedFile);
    }

    @Test
    public void createFormTest() {
        creator.createList(com.rves.htmlgenerator.pojo.User.class);

        File actualFile = new File(DIRECTORY_ACTUAL_FILE + "userDir/createForm.HTML");
        File expectedFile = new File(new StringBuilder().append(DIRECTORY_EXPECTED_FILE).append("/").append("createForm.HTML").toString());

        comparePages(actualFile, expectedFile);
    }

    @Test
    public void createCardTest() {
        creator.createList(com.rves.htmlgenerator.pojo.Room.class);

        File actualFile = new File(DIRECTORY_ACTUAL_FILE +"roomDir/createCard.HTML");
        File expectedFile = new File(new StringBuilder().append(DIRECTORY_EXPECTED_FILE).append("/").append("createCard.HTML").toString());

        comparePages(actualFile, expectedFile);
    }



    private void comparePages(File actualFile, File expectedFile) {
        try {
            String a = FileUtils.readFileToString(actualFile, "utf-8");
            String b = FileUtils.readFileToString(expectedFile, "utf-8");

            assertEquals("The files differ!",
                    FileUtils.readFileToString(actualFile, "utf-8"),
                    FileUtils.readFileToString(expectedFile, "utf-8"));
            System.out.println("Файлы равны");
        } catch (IOException e) {
            System.out.println("Файлы не равны");
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}