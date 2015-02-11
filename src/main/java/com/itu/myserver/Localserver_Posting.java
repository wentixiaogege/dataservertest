package com.itu.myserver;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.itu.DAO.DataAccess;
import com.itu.bean.SmartMeterData;
import com.itu.localserver.client.SmartMeterDataProtos.SmartMeter;
import com.itu.util.Log4jUtil;


@Path("/localserverpost")
public class Localserver_Posting {
	Logger logger= Log4jUtil.getLogger(AddressBookResource.class);
	@Path("/smartmeter")
	@POST
	@Consumes("application/x-protobuf")
	public Response postSmartMeter(SmartMeter smdata) {		
		logger.debug(String.format("post a smdata, id:%d, ieee:%s, power:%s", smdata.getId(),smdata.getIeeeAddress(),smdata.getPower()));
		//AddressBookStore.store(person);
		DataAccess.localserver_postSmartmeter(smdata);
		return Response.ok().build();
	}
	
	
	@Path("/smartmeterdata")
	@POST
	@Consumes("application/x-protobuf")
	public Response postSmartMeterData(com.itu.localserver.client.SmartMeterDataProtos.SmartMeterData smdatalist) {	
		
		//logger.debug(String.format("post a smdata, id:%d, ieee:%s, power:%s", smdata.getId(),smdata.getIeeeAddress(),smdata.getPower()));
		//AddressBookStore.store(person);
		DataAccess.localserver_postSmartmeterData(smdatalist);
		return Response.ok().build();
	}
}


