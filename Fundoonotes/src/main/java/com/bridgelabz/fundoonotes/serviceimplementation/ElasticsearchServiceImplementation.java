package com.bridgelabz.fundoonotes.serviceimplementation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.configuration.ElasticSearchConfig;
import com.bridgelabz.fundoonotes.constant.Constant;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.ElasticsearchService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ElasticsearchServiceImplementation implements ElasticsearchService {
	
	private Constant constant = new Constant();
	
	@Autowired
	private ElasticSearchConfig elasticSearchConfig;

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<Notes> searchNote(String title, long noteId) {
		try {
			Notes note = noteRepository.findById(noteId);
			if (note != null) {
				List<Notes> isTitlePresent = noteRepository.findByTitle(title, noteId);
				if (isTitlePresent != null) {
					return noteRepository.getAllTitle(title);
				}
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String createNote(Notes note) {
		try {
			Map<String, Object> noteMapper = objectMapper.convertValue(note, Map.class);
			IndexRequest indexRequest = new IndexRequest(constant.INDEX, constant.TYPE, String.valueOf(note.getNoteId()))
					.source(noteMapper);
			IndexResponse indexResponse = elasticSearchConfig.client().index(indexRequest, RequestOptions.DEFAULT);
			System.out.println(indexRequest + " " + indexResponse);
			return indexResponse.getResult().name();
		} catch (Exception e) {
			log.warn(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateNote(long noteId) {
		try {
			Notes note = noteRepository.findById(noteId);
			Map<String, Object> notemapper = objectMapper.convertValue(note, Map.class);
			UpdateRequest updateRequest = new UpdateRequest(constant.INDEX, constant.TYPE, String.valueOf(note.getNoteId()))
					.doc(notemapper);
			UpdateResponse updateResponse = elasticSearchConfig.client().update(updateRequest, RequestOptions.DEFAULT);
			log.info(updateResponse.getResult().name());
		} catch (IOException e) {
			log.warn(e.getMessage());
		}
	}

	@Override
	public String deleteNote(long noteId) {
		try {
			Notes note = noteRepository.findById(noteId);
			DeleteRequest deleterequest = new DeleteRequest(constant.INDEX, constant.TYPE, String.valueOf(note.getNoteId()));
			DeleteResponse deleteResponse = elasticSearchConfig.client().delete(deleterequest, RequestOptions.DEFAULT);
			return deleteResponse.getResult().name();
		} catch (IOException e) {
			log.warn(e.getMessage());
		}
		return null;
	}

	@Override
	public List<Notes> searchTitle(String title) {
		try {
			SearchRequest searchrequest = new SearchRequest("Bridgelabz");
			SearchSourceBuilder searchsource = new SearchSourceBuilder();
			searchsource.query(QueryBuilders.matchQuery("Title", title));
			searchrequest.source(searchsource);
			SearchResponse searchresponse = elasticSearchConfig.client().search(searchrequest, RequestOptions.DEFAULT);
			return getResult(searchresponse);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;

	}

	private List<Notes> getResult(SearchResponse searchresponse) {
		try {
			SearchHit[] search = searchresponse.getHits().getHits();
			List<Notes> note = new ArrayList<>();
			if (search.length > 0) {
				Arrays.stream(search)
						.forEach(s -> note.add(objectMapper.convertValue(s.getSourceAsMap(), Notes.class)));
			}
			return note;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}