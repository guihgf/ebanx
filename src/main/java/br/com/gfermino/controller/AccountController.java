package br.com.gfermino.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@RequestMapping("/reset")
	public String balance() {
		services.reset();
		return "OK";
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public int balanceWithoutParameter(@PathVariable("id") int id)   {
    	return services.findById(id).getBalance();
    }
	
	@RequestMapping("/balance")
	public int balance(@RequestParam(value="account_id") int id) {
		return services.findById(id).getBalance();
	}
	
	@RequestMapping(name = "/event", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<Object> event(@RequestBody AccountVo vo)   {
		Map<String, Object> rtn = new LinkedHashMap<>();
		
		if(vo.getType().equals("deposit")){
			rtn.put("destination", this.services.deposit(vo));

		}
		else if(vo.getType().equals("withdraw")){
			rtn.put("origin", this.services.withdraw(vo));
		}
		else {
			//transfer
			this.services.transfer(vo);
	
			rtn.put("origin", this.services.findById(vo.getOrigin()));
			rtn.put("destination", this.services.findById(vo.getDestination()));

		}
		
	    return new ResponseEntity<Object>(rtn,HttpStatus.CREATED);
				
	}
	
}
