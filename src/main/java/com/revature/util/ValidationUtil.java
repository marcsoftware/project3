package com.revature.util;

import java.util.List;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.entities.Content;
import com.revature.entities.Module;
import com.revature.exceptions.InvalidContentException;
import com.revature.exceptions.InvalidModuleException;
import com.revature.exceptions.InvalidSearchException;

@Component
@Aspect
public class ValidationUtil {
	
	@Pointcut("within(com.revature.services..*) ")
	public void servicesPC() {
		
	}
	
	@LogException
	@Before("servicesPC() && args(content,..)")
	public void verifyContent(Content content) {
		if(content == null)
			throw new InvalidContentException("content is of null value");
		
		if(content.getFormat().isEmpty() || content.getFormat().length() > 254)
			throw new InvalidContentException("Format is either empty or is longer than 254 characters.");
		
		if(content.getTitle().isEmpty() || content.getTitle().length() > 254)
			throw new InvalidContentException("Title is either empty or is longer than 254 characters.");
		
		if(content.getUrl().isEmpty() || content.getUrl().length() > 254)
			throw new InvalidContentException("URL is either empty or is longer than 254 characters.");
		
		if(content.getDescription().length() > 254)
			throw new InvalidContentException("Description is longer than 254 characters.");
	}
	
	@LogException
	@Before("servicesPC() && args(module,..)")
	public void verifyModule(Module module) {
		if(module == null)
			throw new InvalidContentException("Module is of null value");
		
		if(module.getSubject().isEmpty() || module.getSubject().length() > 254)
			throw new InvalidModuleException("Subject is empty or is longer than 254 characters.");
	}
	
	@LogException
	@Before("servicesPC() && args(title,..)")
	public void verifyStringTitle(String title) {
		if(title.length() > 254)
			throw new InvalidSearchException("title is empty or is longer than 254 characters.");
	}
	
	@LogException
	@Before("servicesPC() && args(format,..)")
	public void verifyStringFormat(String format) {
		if(format.length() > 254)
			throw new InvalidSearchException("format is empty or is longer than 254 characters.");
	}
	
	@LogException
	@Before("servicesPC() && args(moduleId,..)")
	public void verifyModuleId(int moduleId) {
		if(moduleId <= 0)
			throw new InvalidSearchException("the ModuleId is not a valid index.");
	}
	
	@LogException
	@Before("servicesPC() && args(moduleIds,..)")
	public void verifyListModuleId(List<Integer> moduleIds) {
		for(int x = 0; x < moduleIds.size(); x++) {
			this.verifyModuleId(moduleIds.get(x));
		}
	}
	
	@LogException
	@Before("servicesPC() && args(title, format, moduleIds,..)")
	public void verifyFilter(String title, String format, List<Integer> moduleIds) {
		this.verifyStringTitle(title);
		this.verifyStringFormat(format);
		this.verifyListModuleId(moduleIds);
	}
	
	@LogException
	@Before("servicesPC() && args(id,..)")
	public void verifyId(int id) {
		if(id <= 0)
			throw new InvalidSearchException("the id is not a valid index.");
	}

}
