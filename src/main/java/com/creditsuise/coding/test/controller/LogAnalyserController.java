package com.creditsuise.coding.test.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.creditsuise.coding.test.service.LogAnalyserService;

@RestController
public class LogAnalyserController {
	
	@Autowired
	private LogAnalyserService logAnalyserService;
	
	/**
	 * Sync call for code test.
	 * In Practical we can design as async call and return tracking id
	 * User can use the tracking id to know the current status of log file analysis.
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	@PostMapping(path = "/api/1/log/analysis/{filename}")
	public ResponseEntity<String> analyseLog(@PathVariable("filename") String filename) throws IOException {
		logAnalyserService.execute(filename);
		return new ResponseEntity<String>("Log file analyzed. Please check database for any event taking more then 4ms.", HttpStatus.OK);
	}
}
