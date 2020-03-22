package org.codejudge.sb.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.tools.DocumentationTool.Location;
import org.json.JSONObject;
import org.codejudge.sb.model.Lead;
import org.codejudge.sb.repository.LeadRepository;
import org.codejudge.sb.services.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class LeadController {
	@Autowired
	private LeadRepository leadRepo;
	@Autowired
	private Lead lead;
	@Autowired
	private LeadService leadServcie;
	
	
	
	@PostMapping(value = "/api/leads")
	public Map createLead(@RequestBody String jsonStr, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		
		Map<String, String> params = new HashMap<>();
		System.out.println("jsonstr = "+jsonStr);
		ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

		JSONObject jsonObject = new JSONObject(jsonStr);
		if (jsonObject != null) {
			params = objectMapper.readValue(jsonObject.toString(), new TypeReference<Map<String, Object>>() {});
		}
		System.out.println("params = "+params);
		params.put("first_name", params.get("first_name"));
		params.put("last_name", params.get("last_name"));
		params.put("mobile", params.get("mobile"));
		params.put("email", params.get("email"));
		params.put("location_type", params.get("location_type"));
		params.put("location_string", params.get("location_string"));
		params.put("status", params.get("status"));

		Map res = leadServcie.createLead(params, response);
		
		return res;
	}
	
	@GetMapping(value = "/api/leads/{lead_id}")
	public Map getLead(@PathVariable("lead_id") String lead_id, HttpServletResponse response) {
		Map res = leadServcie.getLead(lead_id, response);
		return res;
		
	}
	
	@PutMapping(value = "/api/leads/{lead_id}")
	public Map updateLead(@PathVariable("lead_id") String lead_id, @RequestBody String jsonStr, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		
		Map<String, String> params = new HashMap<>();
		System.out.println("jsonstr = "+jsonStr);
		ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

		JSONObject jsonObject = new JSONObject(jsonStr);
		if (jsonObject != null) {
			params = objectMapper.readValue(jsonObject.toString(), new TypeReference<Map<String, Object>>() {});
		}
		System.out.println("params = "+params);
		params.put("first_name", params.get("first_name"));
		params.put("last_name", params.get("last_name"));
		params.put("mobile", params.get("mobile"));
		params.put("email", lead_id);
		params.put("location_type", params.get("location_type"));
		params.put("location_string", params.get("location_string"));
		params.put("status", params.get("status"));
		
		
		Map res = leadServcie.updateLead(params, response);
		return res;
	}
	
	@DeleteMapping(value="/api/leads/{lead_id}")
	public Map deleteLead(@PathVariable("lead_id") String lead_id, @RequestBody String jsonStr, HttpServletResponse response) {
		Map map = leadServcie.deleteLead(lead_id, response);
		return map;
	}
	
	@PutMapping(value = "/api/mark_lead/{lead_id}")
	public Map makeLead(@PathVariable("lead_id") String lead_id, @RequestBody String jsonStr, HttpServletResponse response) {
		Map map = leadServcie.makeLead(lead_id, response);
		return map;
	}
	
}
