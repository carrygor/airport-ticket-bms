package com.airport.ticket.bms.service.Imp;

import com.airport.ticket.bms.Enum.KeyEnum;
import com.airport.ticket.bms.ExceptionGMS.SystemException;
import com.airport.ticket.bms.dao.CustomerListDao;
import com.airport.ticket.bms.entity.CustomerList;
import com.airport.ticket.bms.form.Customer.CustomerForm;
import com.airport.ticket.bms.form.company.CompanyForm;
import com.airport.ticket.bms.service.CustomerListService;
import com.airport.ticket.bms.util.JsonUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CustomerListServiceImp implements CustomerListService{

    @Autowired
    private CustomerListDao customerListDao;

    public List<CustomerList> fetchCustomerByFullName(String name) {
        return customerListDao.fetchCustomerByUsername(name);
    }

    public Map<String, Object> fetchCustomerByName(String name) {
        name = "%"+name+"%";
        Map<String ,Object> map = new HashMap<String, Object>();
        map.put("list",customerListDao.fetchCustomerByUsername(name));
        return map;
    }

    public Map<String, Object> fetchPageCustomer(int pageNo, int pageSize) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list",customerListDao.fetchPageCustomer((pageNo - 1)*pageSize,pageSize));
        return map;
    }

    @Transactional(rollbackFor=Exception.class)
    public boolean addCustomerMessage(CustomerForm array) throws Exception {

            //加上对是否有该该客户航空公司的判断
            //to do
            CustomerList list = customerListDao.fetchCustomerByIdCard(array.getIdCard());
            if (list != null){
                SystemException se = new SystemException();
                se.setErrCode(3);
                se.setErrMsg("已存在该客户！");
                throw  se;
            }

            CustomerList customer = new CustomerList();
            customer.setAddress(array.getAddress());
            customer.setEmail(array.getEmail());
            customer.setHouseholdRegister(array.getHouseholdRegister());
            customer.setIdcard(array.getIdCard());
            customer.setName(array.getName());
            customer.setPhone(array.getPhone());
            customer.setStatus(array.getStatus());


        int isSuccess = customerListDao.insert(list);
        return isSuccess == 1 ? true :false;

    }

    public boolean updateCustomer(CustomerList customerList) throws Exception {
        return customerListDao.updateByPrimaryKeySelective(customerList) == 1?true:false;
    }

    public boolean updateCustomer(CustomerForm form) throws Exception {
        CustomerList customer = new CustomerList();
        customer.setAddress(form.getAddress());
        customer.setEmail(form.getEmail());
        customer.setHouseholdRegister(form.getHouseholdRegister());
        customer.setIdcard(form.getIdCard());
        customer.setName(form.getName());
        customer.setPhone(form.getPhone());
        customer.setStatus(form.getStatus());

        return customerListDao.updateByPrimaryKeySelective(customer) == 1? true:false;
    }

    public CustomerList fetchFlightCustomerByKey(int key) throws Exception {
        return customerListDao.selectByPrimaryKey(key);
    }

    public Map<String,Object> searchCustomer(String name,String email,String idCard){
        name = "%"+name+"%";
        email = "%"+email+"%";
        idCard = "%"+idCard+"%";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list",customerListDao.searchCustomer(name,email,idCard));
        return map;
    }

    public boolean removeCustomer(int key){

        CustomerList list = customerListDao.selectByPrimaryKey(key);

        list.setStatus(false);
        return customerListDao.updateByPrimaryKey(list) == 1? true:false;

    }

    @Transactional(rollbackFor=Exception.class)
    public boolean importFromExcel(MultipartFile excel) throws Exception{

        Workbook workbook = new XSSFWorkbook(excel.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.rowIterator();
        List<CustomerList> lists = new ArrayList<CustomerList>();

        if (rows.hasNext()) rows.next();

        while (rows.hasNext()) {
            Row row = rows.next();  //获得行数据
            Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器
            List<String> list = new ArrayList<String>();
            while (cells.hasNext()) {
                Cell cell = cells.next();
                list.add(cell.toString());
            }
            int i = 0;
            CustomerList customer = new CustomerList();
            customer.setName(list.get(i++));
            customer.setIdcard(list.get(i++));
            customer.setPhone(list.get(i++));
            customer.setHouseholdRegister(list.get(i++));
            customer.setEmail(list.get(i++));
            customer.setAddress(list.get(i++));
            customer.setStatus(Boolean.parseBoolean(list.get(i++)));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            customer.setInsertTime(sdf.parse(list.get(i++)));
            customer.setUpdateTime(sdf.parse(list.get(i++)));
            lists.add(customer);

        }
        for (CustomerList list:lists){
            customerListDao.insert(list);
        }
        return true;
    }


}
