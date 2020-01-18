package com.bridgelabz.fundoonotes.serviceimplementation;

import java.util.List;
import java.util.Map;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.configuration.ElasticSearchConfig;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.service.ElasticsearchService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ElasticsearchServiceImplementation implements ElasticsearchService {

	@Autowired
	private ObjectMapper objectMapper;

	private static final String TYPE = "note";

	private static final String INDEX = "bridgelabz";

	@Autowired
	private ElasticSearchConfig elasticSearchConfig;

	@Override
	public String createNote(Notes note) {
		try {
			Map<String, Object> noteMapper = objectMapper.convertValue(note, Map.class);
			IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, String.valueOf(note.getNoteId()))
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
	public void updateNote(Long noteId) {

	}

	@Override
	public String deleteNote(Long noteId) {
		return null;
	}

	@Override
	public List<Notes> searchNote(String title) {
		return null;
	}

}
