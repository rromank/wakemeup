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

import biz.wakemeup.dao.AlarmDao;
import biz.wakemeup.model.Alarm;
import biz.wakemeup.model.DBEntity;

@Path("/alarms")
public class AlarmService {

	AlarmDao dao = new AlarmDao();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Alarm> getAll() {
		List<Alarm> alarms = new ArrayList<Alarm>();
		for (DBEntity entity : dao.findAll().values()) {
			alarms.add((Alarm) entity);
		}
		return alarms;
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAlarm(@PathParam("id") String id) {
		return Response.status(201).entity(dao.findById(new ObjectId(id)))
				.build();
	}

	@GET
	@Path("users/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAlarmByUserId(@PathParam("id") String id) {
		return Response.status(201).entity(dao.findByUserId(new ObjectId(id)))
				.build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createNewAlarm(Alarm requestAlarm) {
		if (requestAlarm != null && requestAlarm.isCorrect()) {
			try {
				dao.insert(requestAlarm);
			} catch (IllegalArgumentException ex) {
				return Response.status(400).build();
			}
			return Response.status(201)
					.entity(requestAlarm.getDBObject().toString()).build();
		}
		return Response.status(400).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateAlarm(Alarm requestAlarm) {
		if (requestAlarm.getId() == null || (!requestAlarm.isCorrect())) {
			return Response.status(400).build();
		}
		if (dao.findById(new ObjectId(requestAlarm.getId())) != null) {
			dao.update(requestAlarm);
			return Response.status(200).build();
		} else {
			try {
				dao.insert(requestAlarm);
			} catch (IllegalArgumentException ex) {
				return Response.status(400).build();
			}
			return Response.status(201)
					.entity(requestAlarm.getDBObject().toString()).build();
		}
	}

	@DELETE
	@Path("{id}")
	public Response removeAlarm(@PathParam("id") String id) {
		if (dao.findById(new ObjectId(id)) != null) {
			dao.remove(new ObjectId(id));
			return Response.status(200).build();
		}
		return Response.status(204).build();
	}
}
