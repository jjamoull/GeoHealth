package com.webgis.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NotFoundTest {
    private String message = "There is not what you search !";

    @Test
    public void throwTheException(){
        NotFound notFound = assertThrows(NotFound.class,
                () -> {
                    throw new NotFound(message);
                });
        assertEquals(notFound.getMessage(), message);
    }

}
