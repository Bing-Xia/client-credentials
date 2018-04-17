package com.moxtra.oauth.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.moxtra.oauth.model.StatementVO;

@RestController
public class StatementController {

	@ResponseBody
    @GetMapping("/api/statement/{month}")
    public StatementVO getOrder(@PathVariable String month) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        StatementVO statement = new StatementVO();
        statement.setMonth(month);
        Map<String, Double> map = new TreeMap<String, Double>();
        double total_amount = 0;
        
        int i = (int) (Math.random() * (30 - 10 + 1) + 10);
        while (i > 0) {
        		String date = getRandomDate(month);
        		double amount = new BigDecimal(Math.random() * 1000).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        		map.put(date, amount);
        		total_amount += amount;
        		i--;
        }
        
        statement.setTotal_amount((double) Math.round(total_amount * 100) / 100);
        statement.setList(map);
        
        return statement;
    }
	
	
	
	static String getRandomDate(String month_str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();    
		
		int year = Integer.parseInt(month_str.substring(0, month_str.indexOf("-")));
		int month = Integer.parseInt(month_str.substring(month_str.indexOf("-") + 1));
		
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month-1);
		
		c.set(Calendar.DAY_OF_MONTH,1); 
		String first = format.format(c.getTime());
		
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		String last = format.format(c.getTime());
		
		Date date = randomDate(first, last); 
		
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return format2.format(date);
	}
	
    private static Date randomDate(String beginDate,String endDate){  
        try {  
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
            Date start = format.parse(beginDate);  
            Date end = format.parse(endDate);  
              
            if(start.getTime() >= end.getTime()){  
                return null;  
            }  
              
            long date = random(start.getTime(),end.getTime());
            
            
              
            return new Date(date);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
      
    private static long random(long begin,long end){  
        long rtn = begin + (long)(Math.random() * (end - begin));  
        if(rtn == begin || rtn == end){  
            return random(begin,end);  
        }  
        return rtn;  
    } 

}
