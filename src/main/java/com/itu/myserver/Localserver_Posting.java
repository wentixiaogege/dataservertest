package com.itu.myserver;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.itu.DAO.DataAccess;
import com.itu.bean.SmartMeterData;
import com.itu.util.Log4jUtil;


@Path("/localserverpost")
public class Localserver_Posting {
	Logger logger= Log4jUtil.getLogger(AddressBookResource.class);
	//@Path("/smartmeterdata")
	@POST
	@Consumes("application/x-protobuf")
	public Response postPerson(SmartMeterData smdata) {		
		logger.debug(String.format("post a smdata, id:%d, ieee:%s, energy:%s", smdata.getId(),smdata.getSmIeeeAddress(),smdata.getEnergy()));
		//AddressBookStore.store(person);
		DataAccess.localserver_postSmartmeterData(smdata);
		return Response.ok().build();
	}
}


