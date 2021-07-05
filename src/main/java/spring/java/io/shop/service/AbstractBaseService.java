package spring.java.io.shop.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public abstract class AbstractBaseService {

	protected final Logger LOGGER=LoggerFactory.getLogger(this.getClass());
	
	protected final Gson gson =new Gson();
}
