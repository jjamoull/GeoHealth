package com.converter;

import com.webgis.exception.NotFound;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TiffFilesTest {

    private String[] command = new String[] {
            "echo",
            "hello"
    };

    String[] commandWithALotOfLines = {
            "bash",
            "-c",
            "echo line1; echo line2"};

    private String[] falseCommand= new String[] {
            "That's command exists ?"
    };

    private String logPrefix = "process";


    @Test
     void commandDoesntExist(){
        assertThrows(IOException.class,
                () -> {
                    TiffFiles.runProcessFor8Bits(falseCommand,logPrefix);
                });
    }

    @Test
     void commandRunForEternality(){
        command = new String[] {"ls", "file_that_does_not_exist"};
        assertThrows(IOException.class,
                () -> {
                    TiffFiles.runProcessFor8Bits(falseCommand,logPrefix);
                });
    }

    @Test
     void commandExistAndIsOK(){
        assertDoesNotThrow(
                () -> {
                    TiffFiles.runProcessFor8Bits(command,logPrefix);
                });

    }
    @Test
    void commandWithALotOfLinesAndIsOK(){
        assertDoesNotThrow(
                () -> {
                    TiffFiles.runProcessFor8Bits(commandWithALotOfLines,logPrefix);
                });

    }





}
