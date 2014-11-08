package ca.liothe.bib.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.SearchServiceFactory;

@Configuration
@ComponentScan(basePackages = {"ca.liothe.bib"})
public class BibliothecaConfig {
	
	@Bean
	public DatastoreService getDatastoreService(){
		return DatastoreServiceFactory.getDatastoreService();
	}

	@Bean
	public Index getIndex(){
	    IndexSpec indexSpec = IndexSpec.newBuilder().setName("Library").build(); 
	    return SearchServiceFactory.getSearchService().getIndex(indexSpec);
	}
	
	@Bean
	public MemcacheService getMemcacheService(){
		return MemcacheServiceFactory.getMemcacheService();
	}
	
}
