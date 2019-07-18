package com.revature.services;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.entities.Content;
import com.revature.entities.Link;
import com.revature.repositories.LinkRepository;
import com.revature.repositories.ContentRepository;
import com.revature.repositories.ModuleRepository;
import com.revature.util.LogException;

@Service
@Transactional
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	ContentRepository cr;
	@Autowired 
	LinkRepository lr;
	@Autowired
	ModuleRepository mr;

	/**
	 * createContent first inserts the content to the database
	 * then iterates over the set of links that are within the content
	 * and replaces the fk of content id with the correct content id
	 * then adds the set of links to the link table. 
	 */
	@LogException
	@Override
	public Content createContent(Content content) {
		
		//get the tags for the new submitted content
		Set<Link> links = content.getLinks();
		
		
		if (links == null) { //if the submitted content has no tags then throw an error
			throw new NullPointerException();
		}
		
		content.setLinks(null);
		content = cr.save(content); //add content to repository
		
		for(Link link : links) {
			link.setContentId(content.getId());
		}
		
		lr.saveAll(links);
		
		content.setLinks(links);		

		return content;
	}
	

	/**
	 * Get all the content from the database and passes a set of content objects
	 */
	@Override
	@LogException
	public Set<Content> getAllContent() {
			Set<Content> contents = new HashSet<>();
			cr.findAll().forEach(contents :: add);
			return contents;
		}
	
	/**
	 * get content from the data base that match a passed in id
	 * then returns the content with that id.
	 */
	@Override
	@LogException
	public Content getContentById(int id) {	
			return cr.findById(id).iterator().next();		
	}
	
	/**
	 * Description - Updates an existing content
	 * @param newContent - content received from client request
	 * @return - the updated content
	 * @throws - NullPointerException - if the newContent parameter is null or if the requested content to be updated doesn't exist in content Repository
	 */
	//Need an update content method here - Mdo
	@Override
	@LogException
	public Content updateContent(Content newContent) {
		
		//check if requested update to content is a null object
		if(newContent == null)
			throw new NullPointerException();
		
		//check if newContent has an id
		if(Character.isDigit(newContent.getId()))
			throw new NumberFormatException();
		
		//In order to check if we are removing(and/or updating other fields in content) or adding tags
		//we must first split the tags into the existing tags and the new tags (if there are any)
		//because when receiving the new tags from the client, all new tags have an id of 0. If you try to add
		//the tags straight away it will cause a DataIntegrity error or InvalidData error.
		//get the tags for the new submitted content
		Set<Link> oldLinks = new HashSet<>();
		Set<Link> newLinks = new HashSet<>();
		
		for(Link link : newContent.getLinks()) {
			if(link.getId() == 0) {
				newLinks.add(link);
			} else {
				oldLinks.add(link);
			}
		}
		
		//set the links in the newContent to the old content
		newContent.setLinks(oldLinks);
		
		//check if there are any new links
		if(!newLinks.isEmpty()) {
			//if there is
			//save the new links (which will assign actual id's to each new link)
			for(Link l : lr.saveAll(newLinks)) {
				oldLinks.add(l); //add the new newLinks to the oldLinks
			}
			newContent.setLinks(oldLinks); //now set the new oldLinks for newContent
		}
		
		//get the existing content
		Content oldContent = this.getContentById(newContent.getId());
		
		//check if content exists in content repo
		if(oldContent == null)
			throw new NullPointerException();
		
		
		//add new updated content
		return cr.save(newContent);
	}
	
	
	
}
