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

import com.itu.myserver.AddressBookProtos.Person;
import com.itu.util.AddressBookStore;

@Path("/addressbook")
public class AddressBookResource {
	@PUT
	@Consumes("application/x-protobuf")
	public Response putPerson(Person person) {		
		System.out.println("put person...");
		AddressBookStore.store(person);
		return Response.ok().build();
	}
	@POST
	@Consumes("application/x-protobuf")
	public Response postPerson(Person person) {		
		System.out.println("post person...");
		AddressBookStore.store(person);
		return Response.ok().build();
	}
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello() {
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
		return Response.ok(p, "application/x-protobuf").build();
	}
}
