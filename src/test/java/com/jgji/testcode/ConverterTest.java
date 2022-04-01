package com.jgji.testcode;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ConverterTest {

    Converter converter = new Converter();

    @Test
    void convert() {
    }

    @Test
    void substringsBetween() {
        //given
        String query = "insert into bookmark(id, `test`,asd) values(1, 'qweqwe', 'ss');";

        //when
        String[] whens = StringUtils.substringsBetween(query, "(", ")");

        //then
        assertThat(whens).isNotEmpty();
        assertThat(whens.length).isEqualTo(2);
        assertThat(whens).contains("id, `test`,asd");
        assertThat(whens).contains("1, 'qweqwe', 'ss'");

        log.info("when [0] = {}", whens[0]);
        log.info("when [1] = {}", whens[1]);
    }

    @Test
    void getColumns() {
        String args = "id, `test`,'asd', ' asdfasdfasdfasdf ' ";

        List<String> columns = converter.getColumns(args);

        assertThat(columns).contains("id", "test","asd", "asdfasdfasdfasdf");
    }

    @Test
    void convertVariables() {
        List<String> given = Arrays.asList(
                "id"
                , "user_name"
                , "user_age"
        );

        List<String> whens = converter.convertVariables(given);

        assertThat(whens).contains("userName", "userAge", "id");
    }
}