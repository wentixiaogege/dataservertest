package com.itu.myserver;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.itu.myserver.AddressBookProtos.Person;
import com.itu.util.AddressBookStore;
import com.itu.util.Log4jUtil;

@Path("/addressbook")
public class AddressBookResource {
	Logger logger= Log4jUtil.getLogger(AddressBookResource.class);
	@PUT
	@Consumes("application/x-protobuf")
	public Response putPerson(Person person) {		
		logger.debug(String.format("put a person, id:%d, name:%s, email:%s", person.getId(),person.getName(),person.getEmail()));
		AddressBookStore.store(person);
		return Response.ok().build();
	}
	@POST
	@Consumes("application/x-protobuf")
	public Response postPerson(Person person) {		
		logger.debug(String.format("post a person, id:%d, name:%s, email:%s", person.getId(),person.getName(),person.getEmail()));
		AddressBookStore.store(person);
		return Response.ok().build();
	}
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello() {
		logger.debug("testing using hello");
		return "Hello Jersey";
	}
	
	@GET
	@Path("/person")
    @Produces("application/x-protobuf")  
    public AddressBookProtos.Person getPerson() {  
        return AddressBookProtos.Person.newBuilder()  
                .setId(1)  
                .setName("Sam")  
                .setEmail("sam@sampullara.com")  
                .addPhone(AddressBookProtos.Person.PhoneNumber.newBuilder()  
                        .setNumber("415-555-1212")  
                        .setType(AddressBookProtos.Person.PhoneType.MOBILE)  
                        .build())  
                .build();  
    } 
	
	@GET
	@Path("/{name}")
	public Response getPerson(@PathParam("name") String name) {		
		Person p = AddressBookStore.getPerson(name);
		System.out.println(name);
		logger.debug(String.format("get a person, id:%d, name:%s, email:%s", p.getId(),p.getName(),p.getEmail()));
		return Response.ok(p, "application/x-protobuf").build();
	}
}
