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

import biz.wakemeup.dao.AudioRecordDao;
import biz.wakemeup.model.AudioRecord;
import biz.wakemeup.model.DBEntity;

@Path("/audioRecords")
public class AudioRecordService {
	AudioRecordDao dao = new AudioRecordDao();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<AudioRecord> getAll() {
		List<AudioRecord> audioRecords = new ArrayList<AudioRecord>();
		for (DBEntity entity : dao.findAll().values()) {
			audioRecords.add((AudioRecord) entity);
		}
		return audioRecords;
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAudioRecord(@PathParam("id") String id) {
		return Response.status(201).entity(dao.findById(new ObjectId(id)))
				.build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createNewAudioRecord(AudioRecord requestAudioRecord) {
		if (requestAudioRecord != null && requestAudioRecord.isCorrect()) {
			dao.insert(requestAudioRecord);
			return Response.status(201)
					.entity(requestAudioRecord.getDBObject().toString())
					.build();
		}
		return Response.status(400).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateAudioRecord(AudioRecord requestAudioRecord) {
		if (requestAudioRecord.getId() == null
				|| (!requestAudioRecord.isCorrect())) {
			return Response.status(400).build();
		}
		if (dao.findById(new ObjectId(requestAudioRecord.getId())) != null) {
			dao.update(requestAudioRecord);
			return Response.status(200).build();
		} else {
			dao.insert(requestAudioRecord);
			return Response.status(201)
					.entity(requestAudioRecord.getDBObject().toString())
					.build();
		}
	}

	@DELETE
	@Path("{id}")
	public Response removeAudioRecord(@PathParam("id") String id) {
		if (dao.findById(new ObjectId(id)) != null) {
			dao.remove(new ObjectId(id));
			return Response.status(200).build();
		}
		return Response.status(204).build();
	}
}
