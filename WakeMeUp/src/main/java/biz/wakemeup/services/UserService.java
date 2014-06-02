package biz.wakemeup.services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;

import biz.wakemeup.dao.UserDao;
import biz.wakemeup.model.DBEntity;
import biz.wakemeup.model.User;

@Path("/users")
public class UserService {

	UserDao dao = new UserDao();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAll() {
		List<User> users = new ArrayList<User>();
		for (DBEntity entity : dao.findAll().values()) {
			users.add((User) entity);
		}
		return users;
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("id") String id) {
		return Response.status(201).entity(dao.findById(new ObjectId(id)))
				.build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createNewUser(User requestUser) {
		if (requestUser != null && requestUser.isCorrect()) {
			dao.insert(requestUser);
			return Response.status(201)
					.entity(requestUser.getDBObject().toString()).build();
		}
		return Response.status(400).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(User requestUser) {
		if (requestUser.getId() == null || (!requestUser.isCorrect())) {
			return Response.status(400).build();
		}
		if (dao.findById(new ObjectId(requestUser.getId())) != null) {
			dao.update(requestUser);
			return Response.status(200).build();
		} else {
			dao.insert(requestUser);
			return Response.status(201)
					.entity(requestUser.getDBObject().toString()).build();
		}
	}

	@DELETE
	@Path("{id}")
	public Response removeUser(@PathParam("id") String id) {
		if (dao.findById(new ObjectId(id)) != null) {
			dao.remove(new ObjectId(id));
			return Response.status(200).build();
		}
		return Response.status(204).build();
	}
}
