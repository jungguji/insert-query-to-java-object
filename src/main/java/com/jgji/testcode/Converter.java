package com.jgji.testcode;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Converter {

    private static final String OPEN = "(";
    private static final String END = ")";
    private static final String COMMA = ",";

    private static final String GRAVE_ACCENT = "`";
    private static final String APOSTROPHE = "'";
    private static final String EMPTY = "";

    private static final String UNDERSCORE = "_";
    private static final String ENTER = "\n";


    private static final int ASCII = 32;

    public String convert(String tableName, String insertQuery) {
        String[] columnAndValue = StringUtils.substringsBetween(insertQuery, OPEN, END);

        List<String> columns = getColumns(columnAndValue[0]);
        List<String> variables = convertVariables(columns);

        List<String> values = values(columnAndValue[1]);

        String camelcaseTableName = convertVariables(Arrays.asList(tableName)).get(0);
        StringBuilder code = new StringBuilder();

        code.append(camelcaseTableName).append("builder()").append(ENTER);
        for (int i = 0; i < variables.size(); i++) {
            code.append(variables.get(i)).append(OPEN).append(values.get(i)).append(END).append(ENTER);
        }
        code.append(".build();");

        return code.toString();
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

                String camelcase = convertCamelcase(splits);

                variables.add(splits[0] + camelcase);
            } else {
                variables.add(column);
            }
        }

        return variables;
    }

    private String convertCamelcase(String[] splits) {
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i < splits.length; i++) {
            String split = splits[i];

            char[] chars = split.toCharArray();
            char ch = (char) (chars[0] - ASCII);
            chars[0] = ch;

            sb.append(chars);
        }

        return sb.toString();
    }

    public List<String> values(String args) {
        final char separator = ',';
        char[] all = args.toCharArray();

        List<String> values = new ArrayList<>();

        StringBuilder value = new StringBuilder();

        boolean open = false;
        boolean end = false;

        for (char ch : all) {
            if (ch == separator) {
                values.add(value.toString());
                value = new StringBuilder();
                open = false;
                end = false;
            } else {
                value.append(ch);
            }
        }

        return values;
    }
}
