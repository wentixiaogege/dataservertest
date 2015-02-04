package com.itu.myserver;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.itu.DAO.DataAccess;
import com.itu.myserver.CloudCommandProtos.CloudCommand;
import com.itu.myserver.CloudCommandProtos.CloudCommands;
import com.itu.util.Log4jUtil;

@Path("/localserverget")
public class Localserver_Getting {
	Logger logger= Log4jUtil.getLogger(Localserver_Getting.class);
	
	@GET
	@Path("/cloudcommand")
	public Response getNewCloudCommand() {		
		
		try {
			CloudCommand new_cmd = DataAccess.localserver_getNewCommand();
			if(null == new_cmd)
				logger.debug(String.format("none unchekced commands"));
			else
				logger.debug(String.format("get a newcmd, id:%d, name:%s, param1:%s", new_cmd.getId(),new_cmd.getName(),new_cmd.getParam1()));

			return Response.ok(new_cmd, "application/x-protobuf").build();//send to localserver through protobuf
		} catch (Exception e) {
			logger.info("exception here---------------");
			return Response.ok().entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/cloudcommands")
	public Response getNewCloudCommands() {		
		
		try {
			CloudCommands new_cmds = DataAccess.localserver_getNewCommands();
			//logger.debug(String.format("get a newcmd, id:%d, name:%s, param1:%s", new_cmd.getId(),new_cmd.getName(),new_cmd.getParam1()));
			return Response.ok(new_cmds, "application/x-protobuf").build();//send to localserver through protobuf
		} catch (Exception e) {
			logger.info("exception here---------------");
			return Response.ok().entity(e.getMessage()).build();
		}
	}
}
