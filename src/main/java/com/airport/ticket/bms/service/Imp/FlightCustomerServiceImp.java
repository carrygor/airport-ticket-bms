package com.airport.ticket.bms.service.Imp;

import com.airport.ticket.bms.Enum.KeyEnum;
import com.airport.ticket.bms.ExceptionGMS.SystemException;
import com.airport.ticket.bms.dao.FlightCustomerDao;
import com.airport.ticket.bms.entity.FlightCustomer;
import com.airport.ticket.bms.form.Customer.FlightCustomerForm;
import com.airport.ticket.bms.service.FlightCustomerService;
import com.airport.ticket.bms.util.JsonUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class FlightCustomerServiceImp implements FlightCustomerService {

    @Autowired
    private FlightCustomerDao flightCustomerDao;

    public List<FlightCustomer> fetchFlightCustomerByFullName(String name) {
        return flightCustomerDao.fetchFlightCustomerByFullName(name);
    }

    public Map<String, Object> fetchFlightCustomerByName(String name) {

        name = "%"+name+"%";
        List<FlightCustomer> companies = flightCustomerDao.fetchFlightCustomerByName(name);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("list",companies);
        return map;
    }

    public Map<String, Object> fetchPageFlightCustomer(int pageNo, int pageSize) throws Exception {
        List<FlightCustomer> messages = null;

        int totalPage = 0;
        try {
            messages = flightCustomerDao.fetchPageFlightCustomers((pageNo - 1) * pageSize,pageSize);
            totalPage = flightCustomerDao.totalCustomer();
        } catch (Exception e){
            SystemException systemException = new SystemException();
            systemException.setErrCode(2);
            systemException.setErrMsg("数据库查询错误！");
            throw systemException;
        }

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("total",totalPage);
        map.put("list",messages);
        return map;
    }

    @Transactional(rollbackFor=Exception.class)
    public boolean addFlightCustomer(JSONArray array) throws Exception {

        JSONObject object = null;
        List<FlightCustomer> customers = new ArrayList<FlightCustomer>();

        for (int i=0;i<array.size();i++) {
            String companyStr = array.getString(i);
            object = JSONObject.fromObject(companyStr);

            String[] parms = {KeyEnum.NAME.getValue(), KeyEnum.ORIGIN.getValue(), KeyEnum.DESTINATION.getValue(),
                KeyEnum.COMPANY.getValue(), KeyEnum.FLIGHT_TIME.getValue(), KeyEnum.FLIGHT_PRICE.getValue(),
                    KeyEnum.ID_CARD.getValue()
            };

            if (!this.validateParam(parms,object)){
                SystemException se = new SystemException();
                se.setErrCode(3);
                se.setErrMsg("参数错误！");
                throw  se;
            }


            FlightCustomer customer = new FlightCustomer();
            customer.setCustomerName(JsonUtils.getNodeValue(companyStr, KeyEnum.NAME.getValue()));
            customer.setDestination(JsonUtils.getNodeValue(companyStr, KeyEnum.DESTINATION.getValue()));
            customer.setFlightCompany(JsonUtils.getNodeValue(companyStr, KeyEnum.COMPANY.getValue()));
            customer.setFlightPrice(Long.parseLong(JsonUtils.getNodeValue(companyStr, KeyEnum.FLIGHT_PRICE.getValue())));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            customer.setFlightTime(sdf.parse(JsonUtils.getNodeValue(companyStr, KeyEnum.FLIGHT_TIME.getValue())));
            customer.setIdcard(JsonUtils.getNodeValue(companyStr, KeyEnum.ID_CARD.getValue()));
            customer.setOrigin(JsonUtils.getNodeValue(companyStr, KeyEnum.ORIGIN.getValue()));
            boolean status = Boolean.parseBoolean(JsonUtils.getNodeValue(companyStr, KeyEnum.STATUS.getValue()));
            customer.setStatus(status);

            customers.add(customer);
        }

        int isSuccess = 1;
        for (FlightCustomer customer:customers){
            isSuccess = flightCustomerDao.insert(customer);
        }
        return isSuccess == 1 ? true :false;
    }

    public boolean updateFlightCustomer(FlightCustomer customer) throws Exception {
        return flightCustomerDao.updateByPrimaryKeySelective(customer) == 1?true:false;
    }

    public boolean updateFlightCustomer(FlightCustomerForm form) throws Exception {

        FlightCustomer customer = new FlightCustomer();
        customer.setCustomerName(form.getCustomerName());
        customer.setDestination(form.getDestination());
        customer.setFlightCompany(form.getCompany());
        customer.setFlightPrice(form.getPrice());
        customer.setFlightTime(form.getFlightTime());
        customer.setIdcard(form.getIdCard());
        customer.setOrigin(form.getOrigin());
        customer.setStatus(form.isStatus());
        customer.setId(form.getId());
        return flightCustomerDao.updateByPrimaryKeySelective(customer) == 1?true:false;
    }

    public FlightCustomer fetchFlightCustomerByKey(int key) throws Exception {
        return flightCustomerDao.selectByPrimaryKey(key);
    }

    public Map<String,Object> searchFlightCustomer(String company,String origin,String destination,String date){

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list",flightCustomerDao.searchFlightCustomers(date,company,origin,destination));
        return map;
    }



    protected boolean validateParam(String[] params, JSONObject body) {
        try {
            for (int i = 0; i < params.length; i++) {
                if (body.get(params[i]) == null
                        || "".equals(body.get(params[i]))) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
