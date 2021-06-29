package com.amsdams.couchbase;

import java.util.Optional;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.couchbase.core.mapping.CouchbaseDocument;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.couchbase.client.java.json.JsonObject;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
public class NoteRepositoryTest {

	@Autowired
	NoteRepository noteRepository;

	@Test
	void create() throws JSONException {

		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<note>\n" + "  <to>Tove</to>\n"
				+ "  <from>Jani</from>\n" + "  <heading>Reminder</heading>\n"
				+ "  <body>Don't forget me this weekend!</body>\n" + "</note>";

		String id = UUID.randomUUID().toString();
		
		JSONObject jsonObject = XML.toJSONObject(xml);

		JsonObject json = JsonObject.fromJson(jsonObject.toString());
		log.info("json {}", json);
		/*
		 * 
		 * {"note":{"from":"Jani","to":"Tove","body":"Don't forget me this weekend!","heading":"Reminder"}}
		 */
		CouchbaseDocument cbDoc = new CouchbaseDocument();
		cbDoc.setContent(json);
		
		Note note = new Note();
		//not autogenerated index
		note.setId(id);
		note.setJson(cbDoc);
		noteRepository.save(note);
		
		Optional<Note>  found = noteRepository.findById(id);
		log.info("expected {}", note);
		log.info("actual {}", found);
		//Assertions.assertEquals(note, found);
		
		
		

	}

}
