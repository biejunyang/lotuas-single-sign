package com.bjy.lotuas.samples.tomcatjsp.service;

import org.springframework.stereotype.Service;

import com.bjy.lotuas.samples.tomcatjsp.exception.ServiceException;

@Service
public class ExceptionTestService {
	
	public void fail1() {
		throw new ServiceException("business errorr");
	}
	
}
