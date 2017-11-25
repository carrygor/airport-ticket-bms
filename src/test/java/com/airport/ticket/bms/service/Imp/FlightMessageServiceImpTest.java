package com.airport.ticket.bms.service.Imp;

import com.airport.ticket.bms.entity.FlightMessage;
import com.airport.ticket.bms.service.FlightMessageService;
import com.airport.ticket.bms.util.JsonDateValueProcessor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml", "classpath:spring-mvc.xml"})
public class FlightMessageServiceImpTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlightMessageServiceImpTest.class);

    @Autowired
    private FlightMessageService service;

    @Test
    public void fetchPageMessage() throws Exception {
        Map<String,Object> map = service.fetchPageMessage(1,10);
        JSONObject object = new JSONObject();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());

        object.putAll(map,jsonConfig);
        LOGGER.debug(object.toString());
        LOGGER.info("hello world");
    }

    @Test
    public void test(){
        String jsonStr = "{\"total\":2,\"list\":[{\"company\":\"南航\",\"destination\":\"杭州\",\"ext1\":\"\",\"ext2\":0,\"flightPrice\":200,\"flightTime\":\"2017-11-24\",\"id\":1,\"insertTime\":\"2017-11-18\",\"origin\":\"广州\",\"seats\":99,\"status\":false,\"updateTime\":\"2017-11-18\"},{\"company\":\"北航\",\"destination\":\"伦敦\",\"ext1\":\"\",\"ext2\":0,\"flightPrice\":1500,\"flightTime\":\"2017-11-29\",\"id\":2,\"insertTime\":\"2017-11-18\",\"origin\":\"香港\",\"seats\":115,\"status\":false,\"updateTime\":\"2017-11-18\"}]}";
        JSONObject object = JSONObject.fromObject(jsonStr);
        Object list = object.get("list");
        LOGGER.debug(list.toString());
        JSONArray array = new JSONArray();
        array = JSONArray.fromObject(list);
        LOGGER.debug("boolean = "+array.isArray());
        LOGGER.info(array.size()+"=size");
    }

    @Test
    public void add(){
        FlightMessage message = new FlightMessage();
        message.setCompany("南航");
        message.setDestination("西安");
        message.setOrigin("深圳");
        message.setFlightPrice(800L);
        message.setSeats(50);
        message.setStatus(true);

        FlightMessage message1 = new FlightMessage();
        message1.setCompany("南航");
        message1.setDestination("夏威夷");
        message1.setOrigin("香港");
        message1.setFlightPrice(4000L);

        List<FlightMessage> messages = new ArrayList<FlightMessage>();
        messages.add(message);
        messages.add(message1);

        try {
//            service.addFlightMessage(messages);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testJson(){
        String jsonStr = "{\"id\":123,\"name\":\"fym\"}";
        JSONObject object = JSONObject.fromObject(jsonStr);
        System.out.println("===================================================");
        System.out.println(object.getString("id"));
        System.out.println(object.getString("name"));
        String str = object.getString("sex");
    }


    @Test
    public void testExcel(){
        File file = new File("D:/test.xlsx");
        Workbook workbook = null;
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            workbook = new XSSFWorkbook(in);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.rowIterator();
            while (rows.hasNext()) {
                Row row = rows.next();  //获得行数据
                System.out.println("Row #" + row.getRowNum());  //获得行号从0开始
                Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    System.out.println("Cell #" + cell.toString());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testArray(){
        List<String> list = new ArrayList<String>();
        String[] parms = {"admin","hello","Many"};
        list.add("admin");
        list.add("hello");
        list.add("Many");
        String str = "Many";
        int i = list.indexOf(str);
        System.out.println(i);
    }


}