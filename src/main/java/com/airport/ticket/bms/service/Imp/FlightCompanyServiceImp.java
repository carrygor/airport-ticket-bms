package com.airport.ticket.bms.service.Imp;

import com.airport.ticket.bms.Enum.KeyEnum;
import com.airport.ticket.bms.ExceptionGMS.SystemException;
import com.airport.ticket.bms.dao.FlightCompanyDao;
import com.airport.ticket.bms.entity.FlightCompany;
import com.airport.ticket.bms.form.company.CompanyForm;
import com.airport.ticket.bms.service.FlightCompanyService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FlightCompanyServiceImp implements FlightCompanyService {

    @Autowired
    private FlightCompanyDao flightCompanyDao;

    public List<FlightCompany> fetchCompanyByFullName(String name) {
        List<FlightCompany> companies = flightCompanyDao.fetchFlightCompanyByName(name);
        return companies;
    }

    public Map<String,Object> fetchCompanyByName(String name) {

        name = "%"+name+"%";
        List<FlightCompany> companies = flightCompanyDao.fetchFlightCompanyByName(name);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("list",companies);
        return map;
    }

    public Map<String, Object> fetchPageCompany(int pageNo, int pageSize) throws Exception {

        List<FlightCompany> messages = null;

        int totalPage = 0;
        try {
            messages = flightCompanyDao.fetchPageFlightCompany((pageNo - 1) * pageSize,pageSize);
            totalPage = flightCompanyDao.totalCompany();
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
    public boolean addCompanyMessage(JSONArray array) throws Exception {

        JSONObject object = null;
        List<FlightCompany> companies = new ArrayList<FlightCompany>();

        for (int i=0;i<array.size();i++) {
            String companyStr = array.getString(i);
            object = JSONObject.fromObject(companyStr);

            if (!object.containsKey(KeyEnum.COMPANY_ADDRESS.getValue()) || !object.containsKey(KeyEnum.NAME.getValue()) ||
                    !object.containsKey(KeyEnum.COMPANY_PHONE.getValue())){
                SystemException se = new SystemException();
                se.setErrCode(3);
                se.setErrMsg("参数错误！");
                throw  se;
            }

            FlightCompany company = new FlightCompany();
            company.setAddress(object.getString(KeyEnum.COMPANY_ADDRESS.getValue()));
            company.setName(object.getString(KeyEnum.NAME.getValue()));
            company.setPhone(object.getString(KeyEnum.COMPANY_PHONE.getValue()));
            company.setEmail(object.containsKey(KeyEnum.COMPANY_EMAIL.getValue())?
                    object.getString(KeyEnum.COMPANY_EMAIL.getValue()):null);

            companies.add(company);
        }

        int isSuccess = 1;
        for (FlightCompany company:companies){
            isSuccess = flightCompanyDao.insert(company);
        }
        return isSuccess == 1 ? true :false;
    }

    public boolean updateFlightCompany(FlightCompany company) throws Exception {
        return flightCompanyDao.updateByPrimaryKeySelective(company) == 1 ? true:false;
    }

    public boolean updateFlightCompany(CompanyForm form) throws Exception {

        FlightCompany company = new FlightCompany();
        company.setId(form.getId());
        company.setAddress(form.getAddress());
        company.setName(form.getName());
        company.setPhone(form.getPhone());
        company.setEmail(form.getEmail());

        return flightCompanyDao.updateByPrimaryKeySelective(company) == 1 ? true:false;
    }

    public FlightCompany fetchFlightCompanyByKey(int key) throws Exception {
        return flightCompanyDao.selectByPrimaryKey(key);
    }
}
