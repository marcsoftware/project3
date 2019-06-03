package com.revature.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.entities.Module;
import com.revature.services.ModuleService;

@CrossOrigin(origins = "*", allowCredentials="true")
@RestController
public class ModuleController {

	@Autowired
	ModuleService moduleService;
	
	@GetMapping
	public Set<Module> getAllModules() {
		return moduleService.getAllModules();
	}
	
	@GetMapping
	public Module getModuleById(@PathVariable int id) {
		return moduleService.getModuleById(id);
	}
	
	@PostMapping
	public Module createModule(@RequestBody Module module) {
		return moduleService.createModule(module);
	}
}
