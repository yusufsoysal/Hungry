package com.yusufsoysal.slack.hungry.service;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.Locale;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MessageServiceTest {

    @InjectMocks
    private MessageService messageService;

    @Mock
    private MessageSource messageSource;

    @Test
    public void shouldReturnMessageFromPropertiesFile() {
        Mockito.when(messageSource.getMessage("MessageId", null, Locale.getDefault())).thenReturn("Text");

        assertThat(messageService.getMessage("MessageId"), Matchers.equalTo("Text"));
    }

    @Test
    public void shouldReturnMessageWithArgumentFromPropertiesFile() {
        Mockito.when(messageSource.getMessage("MessageId", new Object[]{"Argument"}, Locale.getDefault())).thenReturn("Text");

        assertThat(messageService.getMessage("MessageId", "Argument"), Matchers.equalTo("Text"));
    }

    @Test
    public void shouldReturnMessageWithMultipleArgumentsFromPropertiesFile() {
        Mockito.when(messageSource.getMessage("MessageId", new Object[]{"Argument1", "Argument2", "Argument3"}, Locale.getDefault())).thenReturn("Text");

        assertThat(messageService.getMessage("MessageId", "Argument1", "Argument2", "Argument3"), Matchers.equalTo("Text"));
    }

}