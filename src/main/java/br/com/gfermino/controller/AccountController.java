package br.com.gfermino.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gfermino.services.AccountService;
import br.com.gfermino.vo.AccountVo;

@RestController
public class AccountController {
	
	@Autowired
	private AccountService services;
	
	@RequestMapping("/balance")
	public int balance(@RequestParam(value="account_id") int id) {
		return services.findById(id).getBalance();
	}
	
	@RequestMapping(name = "/event", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> event(@RequestBody AccountVo vo,HttpServletResponse response)   {
		Map<String, Object> rtn = new LinkedHashMap<>();
		
		if(vo.getType().equals("deposit")){
			rtn.put("destination", this.services.deposit(vo));
			 response.setStatus(HttpServletResponse.SC_CREATED);
		}
		else if(vo.getType().equals("withdraw")){
			rtn.put("origin", this.services.withdraw(vo));
			response.setStatus(HttpServletResponse.SC_CREATED);
		}
		else {
			//transfer
			this.services.transfer(vo);
	
			rtn.put("origin", this.services.findById(vo.getOrigin()));
			rtn.put("destination", this.services.findById(vo.getDestination()));
			
			response.setStatus(HttpServletResponse.SC_CREATED);
		}
		return rtn;	
    }	
	
}
