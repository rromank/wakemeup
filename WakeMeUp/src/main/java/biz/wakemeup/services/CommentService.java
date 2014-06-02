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

import biz.wakemeup.dao.CommentDao;
import biz.wakemeup.model.Comment;
import biz.wakemeup.model.DBEntity;

@Path("/comments")
public class CommentService {
	CommentDao dao = new CommentDao();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getAll() {
		List<Comment> comments = new ArrayList<Comment>();
		for (DBEntity entity : dao.findAll().values()) {
			comments.add((Comment) entity);
		}
		return comments;
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getComment(@PathParam("id") String id) {
		return Response.status(201).entity(dao.findById(new ObjectId(id)))
				.build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createNewComment(Comment requestComment) {
		if (requestComment != null && requestComment.isCorrect()) {
			dao.insert(requestComment);
			return Response.status(201)
					.entity(requestComment.getDBObject().toString()).build();
		}
		return Response.status(400).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateComment(Comment requestComment) {
		if (requestComment.getId() == null || (!requestComment.isCorrect())) {
			return Response.status(400).build();
		}
		if (dao.findById(new ObjectId(requestComment.getId())) != null) {
			dao.update(requestComment);
			return Response.status(200).build();
		} else {
			dao.insert(requestComment);
			return Response.status(201)
					.entity(requestComment.getDBObject().toString()).build();
		}
	}

	@DELETE
	@Path("{id}")
	public Response removeComment(@PathParam("id") String id) {
		if (dao.findById(new ObjectId(id)) != null) {
			dao.remove(new ObjectId(id));
			return Response.status(200).build();
		}
		return Response.status(204).build();
	}
}
