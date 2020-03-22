package org.codejudge.sb.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.tools.DocumentationTool.Location;
import org.codejudge.sb.model.*;

import org.codejudge.sb.model.Lead;
import org.codejudge.sb.repository.LeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class LeadService {

	@Autowired
	 private LeadRepository leadRepo;
	@Autowired
	private Lead lead;
	
	public Map<String, String> createLead(Map<String, String> params, HttpServletResponse response) {
		String res = "";
		Map<String, String> map = new HashMap<>();
		String email = params.get("email");
		String mobile = params.get("mobile");
		String first_name = params.get("first_name");
		String last_name = params.get("last_name");
		String location_type = params.get("location_type");
		String location_string = params.get("location_string");
		String status = params.get("status");


		

		
		if(isNullOrEmpty(email)) {
			res = "Email is required to create a lead";
			map.put("Error", res);
			response.setStatus(400);
			return map;
		}
		
		Lead  l = leadRepo.findById(email).orElse(null);
		System.out.println("email l = "+l);
		if(l != null ) {
			res = "Email is already registered";
			map.put("Error", res);
			response.setStatus(400);

			return map;
		}else {
			if(!isNullOrEmpty(mobile)) {
				String checkMobile = checkMobileNum(mobile);
				if(!("true".equals(checkMobile))) {
					response.setStatus(400);
					map.put("Error", checkMobile);
					return map;

				}
			}
			lead  = new Lead();
			lead.setEmail(email);
			lead.setMobile(Integer.parseInt(mobile));
			lead.setFirst_name(first_name);
			lead.setLast_name(last_name);
			if(location_type.equalsIgnoreCase("Country")) {
				lead.setLocation_type(Location_type.Country);
			}else if(location_type.equalsIgnoreCase("City")) {
				lead.setLocation_type(Location_type.City);
			}else if(location_type.equalsIgnoreCase("Zip")) {
				lead.setLocation_type(Location_type.Zip);
			}
			lead.setLocation_string(location_string);
			if("Contacted".equalsIgnoreCase(status))
				lead.setStatus(Status.Contacted);
			else
				lead.setStatus(Status.Created);

			leadRepo.save(lead);
			response.setStatus(201);
			Lead ll = leadRepo.findById(email).orElse(null);
			 map.put("first_name", ll.getFirst_name());
			 map.put("last_name", ll.getLast_name());
			 map.put("mobile", Integer.toString(ll.getMobile()));
			 map.put("email", ll.getEmail());
			 map.put("location_type", ll.getLocation_type().toString());
			 map.put("location_string", ll.getLocation_string());
			 map.put("status", ll.getStatus().toString());

			
		}
		
		return map;
	}
	
	
	public static boolean isNullOrEmpty(Object obj) {
		boolean isNull = false;
		String objStr = obj.toString();
		if(objStr == null || objStr.isEmpty() || objStr.equalsIgnoreCase("Null") || objStr.equals("[:]")) {
			isNull = true;
		}
		return isNull;
	}
	
	public  String checkMobileNum(String mobile) {
		String res = "true";
		try {
			if(mobile.length() != 10) {
				res = "Invalid mobile number, should be a 10 digits number";
				return res;
			}
			int num = Integer.parseInt(mobile);
			Lead  l = leadRepo.findByMobile(num).orElse(null);
			if(l != null) {
				res = "Mobile number already registered";
				return res;
			}

			
		}catch(NumberFormatException e) {
			res = "Mobile number should contains only digits";
		}
		return res;
		
	}
	
	public Map<String, String> getLead(String email, HttpServletResponse response){
		Map<String, String> map = new HashMap<>();
		System.out.println("email in get = "+email);
		Lead ll = leadRepo.findById(email).orElse(null);
		if(ll == null) {
			response.setStatus(404);
			return map;
		}
		map.put("first_name", ll.getFirst_name());
		 map.put("last_name", ll.getLast_name());
		 map.put("mobile", Integer.toString(ll.getMobile()));
		 map.put("email", ll.getEmail());
		 map.put("location_type", ll.getLocation_type().toString());
		 map.put("location_string", ll.getLocation_string());
		 map.put("status", ll.getStatus().toString());
		response.setStatus(200);
		return map;
	}
	
	public Map<String, String> updateLead(Map<String, String> params, HttpServletResponse response) {
		String res = "";
		Map<String, String> map = new HashMap<>();
		String email = params.get("email");
		String mobile = params.get("mobile");
		String first_name = params.get("first_name");
		String last_name = params.get("last_name");
		String location_type = params.get("location_type");
		String location_string = params.get("location_string");
		String status = params.get("status");


		
		Lead  lead = leadRepo.findById(email).orElse(null);
		if(lead == null ) {
			res = "No such lead with this email";
			map.put("Error", res);
			response.setStatus(400);
			return map;
		}else {
			if(!isNullOrEmpty(mobile)) {
				String checkMobile = checkMobileNum(mobile);
				if(!("true".equals(checkMobile))) {
					response.setStatus(400);
					map.put("Error", checkMobile);
					return map;

				}
			}
			if(!isNullOrEmpty(mobile))
			lead.setMobile(Integer.parseInt(mobile));
			if(!isNullOrEmpty(first_name))

			lead.setFirst_name(first_name);
			if(!isNullOrEmpty(last_name))

			lead.setLast_name(last_name);
			if(!isNullOrEmpty(location_type))
			{
			if(location_type.equalsIgnoreCase("Country")) {
				lead.setLocation_type(Location_type.Country);
			}else if(location_type.equalsIgnoreCase("City")) {
				lead.setLocation_type(Location_type.City);
			}else if(location_type.equalsIgnoreCase("Zip")) {
				lead.setLocation_type(Location_type.Zip);
			}
			}
			if(!isNullOrEmpty(location_string))

			lead.setLocation_string(location_string);
			if(!isNullOrEmpty(status))
			{
			if(status.equalsIgnoreCase("Contacted"))
				lead.setStatus(Status.Contacted);
			else
				lead.setStatus(Status.Created);
			}
			leadRepo.save(lead);
			response.setStatus(202);
			 map.put("status", "success");
		}
		
		return map;
	}
	public Map deleteLead(String email, HttpServletResponse response) {
		Map res = new HashMap<>();
		try {
			Lead ll = leadRepo.findById(email).orElse(null);
			if(ll == null) {
				res.put("reason", "No Such Lead with this id");
				res.put("status", "failure");

				response.setStatus(400);
			}else {
				leadRepo.delete(ll);
				response.setStatus(204);
				res.put("status", "success");

			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return res;
	}
	
	public Map makeLead(String email, HttpServletResponse response) {
		Map res = new HashMap<>();
		try {
			Lead ll = leadRepo.findById(email).orElse(null);
			if(ll == null) {
				res.put("reason", "No Such Lead with this id");
				res.put("status", "failure");
				response.setStatus(400);
			}else {
				ll.setStatus(Status.Contacted);
				leadRepo.saveAndFlush(ll);
				response.setStatus(202);
				res.put("status", "success");
				res.put("communication", "Status of lead has been changed");
				
			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return res;
	}
}
