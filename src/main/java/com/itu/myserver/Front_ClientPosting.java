package com.itu.myserver;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.itu.DAO.DataAccess;
import com.itu.myserver.CloudCommandProtos.CloudCommand;
import com.itu.util.Log4jUtil;

@Path("/frontendpost")
public class Front_ClientPosting {
	Logger logger = Log4jUtil.getLogger(Front_ClientPosting.class);

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello() {
		logger.debug("plain text test with using hello");
		return "Hello Jersey";
	}

	
	@POST
	@Consumes("application/x-protobuf")
	public Response postcommand(CloudCommand cmd) {
		logger.debug(String.format(
				"post a new command, id:%d, name:, param1:%s", cmd.getId(),
				cmd.getName(), cmd.getParam1()));
		// AddressBookStore.store(person);
		try {
			DataAccess.frontend_addNewCommand(cmd);
			return Response.ok().entity("true").build();
		} catch (Exception e) {
			return Response.ok().entity(e.getMessage()).build();
		}
	}
}
