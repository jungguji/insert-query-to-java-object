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
        //given
        String query = "INSERT INTO partner (created_at,updated_at,name,address,detail_address,homepage,description,image,opening_hours,ending_hours,phone,tab_category_id,strict_company_id,other_cost,fix_order,region,holiday,etc,registration_number) VALUES (NULL,'2022-01-21 09:26:59','더채플앳청담','서울 강남구 선릉로 757','','http://www.thechapel.co.kr','세상에 없던 가장 아름다운 웨딩의 대명사, 더채플 모두가 인정하는 격조 높은 클래스는 웨딩, 본연의 가치에 집중하는 것에서 출발해 상상 그 이상의 환희를 선사하기에 더욱 특별합니다.\n" +
                "\n" +
                " 여유롭고 이국적인 휴양지의 설렘과 자연에서 영감을 얻은 싱그러운 분위기, 우아하고 로맨틱한 예식을 도심 속에서도 프라이빗하게 만끽할 수 있는 진정한 한국형 채플 웨딩.\n" +
                "\n" +
                "국내 최고 높이의 천장고 아래 펼쳐지는 드라마틱한 버진로드와 압도적인 웅장함, 곳곳에 더해진 사랑스러운 축복의 메시지까지 모두의 축복 속에 완성되는 위대한 러브스토리의 절정,\n" +
                "\n" +
                "더채플 웨딩의 주인공이 되어주세요.','https://s3-ap-northeast-2.amazonaws.com/wedqueen/admin/wedding/company/thumbnail/156378515778986FF44BA9B470A1C079B204E7DF9EA20.png','3000-01-31 10:00:01','3000-01-31 19:00:01','02-421-1121',102,NULL,NULL,NULL,'seocho','설연휴, 추석연휴','건물 내 200대, 외부 300대','');";
        String tableName = "partner";
        //when
        String when = converter.convert(tableName, query);
        //then

        log.info(when);
    }

    @Test
    void convert_1() {
        //given
        String query = "INSERT INTO wed_goddess_talk (created_at,updated_at,anonymous,content,title,user_id) VALUES ('2022-03-11 12:13:12','2022-03-11 12:13:12',0,'string','string',191307);";
        String tableName = "wed_goddess_talk";
        //when
        String when = converter.convert(tableName, query);
        //then

        log.info(when);
    }

    @Test
    void convert_여러개() {
        //given
        String query = "INSERT INTO test_table (name, age, homepage, tag, nickname, address, detail_address, gender) values('jgji', 22, 'https://google.co.kr', '', null, null, null, 'man')";
        String query1 = "INSERT INTO test_table (name, age, homepage, tag, nickname, address, detail_address, gender) values('jgji', 22, 'https://google.co.kr', '', '서울시', null, null, 'man')";
        String query2 = "INSERT INTO test_table (name, age, homepage, tag, nickname, address, detail_address, gender) values('jgji', 22, 'https://google.co.kr', '', '여러분', null, null, 'man')";
        String query3 = "INSERT INTO test_table (name, age, homepage, tag, nickname, address, detail_address, gender) values('jgji', 22, 'https://google.co.kr', '', '담배꽁초', null, null, 'man')";
        String query4 = "INSERT INTO test_table (name, age, homepage, tag, nickname, address, detail_address, gender) values('jgji', 22, 'https://google.co.kr', '', null, null, null, 'man')";
        String tableName = "test_table";

        List<String> givens = new ArrayList<>();

        givens.add(query);
        givens.add(query1);
        givens.add(query2);
        givens.add(query3);
        givens.add(query4);

        //when
        for (String given : givens) {
            String when = converter.convert(tableName, given);
            log.info(when);
        }

        //then
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
                "test_q_tdf_gsg"
                , "user_name"
                , "user_id"
                , "id"
        );

        List<String> whens = converter.convertVariables(given);

        assertThat(whens).contains("testQTdfGsg", "userName", "userId", "id");
    }

    @Test
    void betweenTest() {
        //given
        String args = "NULL,'2022-01-21 09:26:59','더채플앳청담','서울 강남구 선릉로 757','','http://www.thechapel.co.kr','세상에 없던 가장 아름다운 웨딩의 대명사, 더채플 모두가 인정하는 격조 높은 클래스는 웨딩, 본연의 가치에 집중하는 것에서 출발해 상상 그 이상의 환희를 선사하기에 더욱 특별합니다.\n" +
                "\n" +
                " 여유롭고 이국적인 휴양지의 설렘과 자연에서 영감을 얻은 싱그러운 분위기, 우아하고 로맨틱한 예식을 도심 속에서도 프라이빗하게 만끽할 수 있는 진정한 한국형 채플 웨딩.\n" +
                "\n" +
                "국내 최고 높이의 천장고 아래 펼쳐지는 드라마틱한 버진로드와 압도적인 웅장함, 곳곳에 더해진 사랑스러운 축복의 메시지까지 모두의 축복 속에 완성되는 위대한 러브스토리의 절정,\n" +
                "\n" +
                "더채플 웨딩의 주인공이 되어주세요.','https://s3-ap-northeast-2.amazonaws.com/wedqueen/admin/wedding/company/thumbnail/156378515778986FF44BA9B470A1C079B204E7DF9EA20.png','3000-01-31 10:00:01','3000-01-31 19:00:01','02-421-1121',102,NULL,NULL,NULL,'seocho','설연휴, 추석연휴','건물 내 200대, 외부 300대',''";
        String[] whens = StringUtils.substringsBetween(args, ",", ",");

        for (String when : whens) {
            System.out.println("when = " + when);
        }
    }

    @Test
    void values() {

        String args = "'설연휴, 추석연휴','건물 내 200대, 외부 300대','', 'test'";

        List<String> whens = converter.values(args);

        for (String when : whens) {
            log.info(when);
        }
    }

    @Test
    void convertClassName() {
        String tableName = "partner";

        String when = converter.convertClassName(tableName);

        assertThat(when).isEqualTo("Partner");

        String tableName2 = "wedding_talk_ads";

        String when2 = converter.convertClassName(tableName2);

        assertThat(when2).isEqualTo("WeddingTalkAds");
    }
}