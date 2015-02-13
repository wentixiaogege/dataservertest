package com.itu.myserver;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.itu.DAO.DataAccess;
import com.itu.myserver.SmartMeterDataRecordsProtos.SmartMeterDataRecord;
import com.itu.myserver.SmartMeterDataRecordsProtos.SmartMeterDataRecords;
import com.itu.util.Log4jUtil;


@Path("/localserverpost")
public class Localserver_Posting {
	Logger logger= Log4jUtil.getLogger(Localserver_Posting.class);
	@Path("/smartmeterrecord")
	@POST
	@Consumes("application/x-protobuf")
	public Response postSmartMeter(SmartMeterDataRecord smdata) {		
		//logger.debug(String.format("post a smdata, id:%d, ieee:%s, power:%s", smdata.getId(),smdata.getIeeeAddress(),smdata.getPower()));
		
		
		logger.debug(String.format("post a smdata, idex:%d, getRmsV1:%s", smdata.getSmIndex(),smdata.getRmsV1()));

		DataAccess.localserver_postSmartmeterRecord(smdata);
		return Response.ok().build();
	}
	
	
	@Path("/smartmeterdata")
	@POST
	@Consumes("application/x-protobuf")
	public Response postSmartMeterData(SmartMeterDataRecords smdatalist) {	
		
		logger.debug(String.format("post a list of smdata to database"));
		
		DataAccess.localserver_postSmartmeterDataRecords(smdatalist);
		return Response.ok().build();
	}
}


