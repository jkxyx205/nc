package com.rick.nc.system.job;

import org.springframework.stereotype.Service;

@Service
public class JobTask {
	public void execute() {
		System.out.println("execute job.................");
	}
}
