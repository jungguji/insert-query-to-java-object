package com.jgji.testcode;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Converter {

    private static final String OPEN = "(";
    private static final String END = ")";
    private static final String COMMA = ",";

    private static final String GRAVE_ACCENT = "`";
    private static final String APOSTROPHE = "'";
    private static final String EMPTY = "";

    private static final String UNDERSCORE = "_";

    private static final int ASCII = 32;




    public String convert(String insertQuery) {
        String[] columnAndValue = StringUtils.substringsBetween(insertQuery, OPEN, END);

        List<String> columns = getColumns(columnAndValue[0]);
        return EMPTY;
    }

    public List<String> getColumns(String args) {
        String[] split = args.split(COMMA);

        List<String> columns = new ArrayList<>();
        for (String s : split) {
            s = s.replace(GRAVE_ACCENT, EMPTY);
            s = s.replace(APOSTROPHE, EMPTY);

            columns.add(s.trim());
        }

        return columns;
    }

    public List<String> convertVariables(List<String> columns) {

        List<String> variables = new ArrayList<>();
        for (String column : columns) {
            if (column.contains(UNDERSCORE)) {
                String[] splits = column.split(UNDERSCORE);

                char[] chars = splits[1].toCharArray();

                char ch = (char) (chars[0] - ASCII);
                chars[0] = ch;

                String camelcase = new String(chars);

                variables.add(splits[0] + camelcase);
            } else {
                variables.add(column);
            }
        }

        return variables;
    }
}
