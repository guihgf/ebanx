package br.com.gfermino.model;

import java.io.Serializable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Account implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int balance=0;
	
	public Account() {
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int amount) {
		this.balance = amount;
	}
	
	public void withdraw(int amount) {
		this.balance-=amount;
	}
	
	public void deposit(int amount) {
		this.balance+=amount;
	}	
	
	@Override
    public String toString() {
      ObjectMapper mapper = new ObjectMapper();
      
      String jsonString = "";
    try {
      mapper.enable(SerializationFeature.INDENT_OUTPUT);
      jsonString = mapper.writeValueAsString(this);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    
      return jsonString;
    }

	
}
