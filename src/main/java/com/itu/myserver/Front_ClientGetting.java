package com.itu.myserver;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.itu.DAO.DataAccess;
import com.itu.bean.SmartMeterData;
import com.itu.util.Log4jUtil;


@Path("/frontendget")
public class Front_ClientGetting {
	Logger logger= Log4jUtil.getLogger(Front_ClientGetting.class);
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello() {
		logger.debug("plain text test with using hello");
		return "Hello Jersey";
	}
	
	@GET
	@Path("/smartmeterdata/{id}")
	public Response getSmartmeterData(@PathParam("id") int id) {		
		//Person p = AddressBookStore.getPerson(name);
		SmartMeterData sm = DataAccess.frontend_getSmartMeterData(id);
		
		logger.debug(String.format("get a smdata, id:%d, ieee:%s, energy:%s", sm.getId(),sm.getSmIeeeAddress(),sm.getEnergy()));
		return Response.ok(sm, "application/x-protobuf").build();
	}  
}


