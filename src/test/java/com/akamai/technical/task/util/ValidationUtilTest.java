package com.akamai.technical.task.util;

import com.akamai.technical.task.exception.PropertyNotExistException;
import com.akamai.technical.task.model.dto.SocialNetworkPostDto;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ValidationUtilTest {

    @DataProvider(name = "data-provider-correct")
    public Object[][] dataProviderCorrect() {
        return new Object[][]{{"author", SocialNetworkPostDto.class}};
    }

    @DataProvider(name = "data-provider-incorrect")
    public Object[][] dataProviderIncorrect() {
        return new Object[][]{{"badauthor", SocialNetworkPostDto.class}};

    }

    @Test(dataProvider = "data-provider-correct")
    void should_find_property_in_class_fields(String property, Class<?> clazz) {
        ValidationUtil.checkSortProperty(property, clazz);
    }

    @Test(dataProvider = "data-provider-incorrect", expectedExceptions = PropertyNotExistException.class)
    void should_not_find_property_in_class_fields(String property, Class<?> clazz) {
        ValidationUtil.checkSortProperty(property, clazz);
    }
}
