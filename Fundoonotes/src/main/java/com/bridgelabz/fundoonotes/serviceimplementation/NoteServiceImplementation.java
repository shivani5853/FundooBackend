package com.bridgelabz.fundoonotes.serviceimplementation;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.ReminderDto;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.NoteServiceInf;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NoteServiceImplementation implements NoteServiceInf {

	@Autowired
	private JwtGenerator jwtGenerator;

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private UserRepository userRepository;

	private ReminderDto reminderDto = new ReminderDto();
	
	@Autowired
	private RedisTemplate<String,Object> redis;

	@Override
	public Notes save(NoteDto noteDto, String token) {
		try {
			long id = jwtGenerator.parse(token);
			System.out.println(token);
			log.info("Id:" + id + " Note Description:" + noteDto.getDescription());
			User user = userRepository.findById(id);
			if (user != null) {
				Notes note = new Notes(noteDto.getTitle(), noteDto.getDescription());
				note.setNoteUser(user);
				note.setCreatedAt(LocalDateTime.now());
				note.setIsArchive(false);
				note.setIsPinned(false);
				note.setIsTrash(false);
				note.setColour("white");
				note.setReminder(reminderDto.getReminderStatus());
				note.setReminderTime(reminderDto.getReminder());
				System.out.println(note);
				noteRepository.insertData(note.getNoteId(), note.getTitle(), note.getDescription(), note.getCreatedAt(),
						note.getUpdateTime(), id, note.getReminder(), note.getReminderTime(), note.getIsTrash());
				System.out.println(note);
				
				return note;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer pinned(long noteId, String token) {
		System.out.println("111");
		try {
			Notes noteOne=verify(token);
//			System.out.println("Inside");
//			ValueOperations<String, Object> userIdOne=redis.opsForValue();
//			System.out.println(userIdOne+" userIdOne" );
			long userId = jwtGenerator.parse(token);
			System.out.println("User Id:" + userId + " Token:" + token + "Note Id:" + noteId);
			
			Notes note = noteRepository.findById(noteId);
			System.out.println(note);
			if (note.getIsPinned()) {
				noteRepository.setPinned(false, userId, noteId);
				return 1;
			} else if (!note.getIsPinned()) {
				noteRepository.setPinned(true, userId, noteId);
				return 0;
			} else {
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public Integer archive(long noteId, String token) {
		try {
			System.out.println("Inside");
			long userId = jwtGenerator.parse(token);
			System.out.println("User Id:" + userId + " Token:" + token + "Note Id:" + noteId);
			Notes note = noteRepository.findById(noteId);
			System.out.println(note);
			if (note.getIsArchive()) {
				noteRepository.setArchive(false, userId, noteId);
				return 1;
			} else if (!note.getIsArchive()) {
				noteRepository.setArchive(true, userId, noteId);
				return 0;
			} else {
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public Notes colour(long noteId, String token, String colour) {
		try {
			System.out.println(colour);
			Long userId = jwtGenerator.parse(token);
			Notes note = noteRepository.findById(noteId);
			System.out.println(userId);
			noteRepository.setColour(colour, userId, noteId);
			return note;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Notes reminder(ReminderDto reminderMe, long noteId, String token) {
		try {
			System.out.println(reminderMe.getReminderStatus());
			long userId = jwtGenerator.parse(token);
			User user = userRepository.findById(userId);
			if (user != null) {
				Notes note = noteRepository.findById(noteId);
				System.out.println(note.getColour());
				note.setReminderTime(reminderMe.getReminder());
				note.setReminder(reminderMe.getReminderStatus());
				System.out.println(note);
				note.setupdateTime();
				noteRepository.remindMe(note.getReminderTime(), note.getReminder(), note.getUpdateTime(), user.getUserId(),
						noteId);
				System.out.println(note.getReminder() + " " + note.getReminderTime() + " " + " " + note.getUpdateTime()
						+ reminderDto.getReminder() + " " + reminderDto.getReminderStatus());
				return note;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer delete(long noteId, String token) {
		try {
			long userId = jwtGenerator.parse(token);
			User isUserAvailable = userRepository.findById(userId);

			if (isUserAvailable.isVerified()) {
				Notes isNoteAvailable = noteRepository.findById(noteId);
				if (isNoteAvailable.getIsTrash()) {
					System.out.println(isNoteAvailable.getIsTrash());
					noteRepository.setPinned(false, userId, noteId);
					noteRepository.setDelete(false, userId, noteId);
					return 1;
				} else if (!isNoteAvailable.getIsTrash()) {
					System.out.println(isNoteAvailable.getIsTrash());
					noteRepository.setPinned(false, userId, noteId);
					noteRepository.setDelete(true, userId, noteId);
					return 0;
				} else {
					System.out.println(isNoteAvailable.getIsTrash());
					return -1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;

	}

	@Override
	public Notes deleteParmanet(long noteId, String token) {
		try {
			long userId = jwtGenerator.parse(token);
			System.out.println(token);
			User isUserAvailable = userRepository.findById(userId);
			System.out.println(isUserAvailable.getUserId());
			if (isUserAvailable.isVerified()) {
				Notes note = noteRepository.findById(noteId);
				System.out.println(note.getNoteId() + " " + note.isVerified() + " " + note.getIsTrash());

				if (note.getIsTrash()) {
					noteRepository.deleteNote(noteId, userId);
					return note;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Notes> searchByUserId(long userId, String token) {
		try {
			long user = jwtGenerator.parse(token);
			System.out.println(token);
			User isUserVerified = userRepository.findById(userId);
			if (isUserVerified.isVerified()) {
				System.out.println("Inside");
				List<Notes> note = noteRepository.searchAllNoteByUserId(userId);
				for (Notes notes : note) {
					System.out.println(notes.getDescription());
				}
				return note;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Notes> searchByNoteId(long noteId, String token) {
		try {
			long user = jwtGenerator.parse(token);
			System.out.println(token);
			User isUserVerified = userRepository.findById(noteId);
			if (isUserVerified != null) {
				System.out.println("Inside" + " " + isUserVerified.getUserId());
				List<Notes> note = noteRepository.searchAllNotesByNoteId(isUserVerified.getUserId(), noteId);
				for (Notes notes : note) {
					System.out.println(notes.getDescription());
				}
				return note;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public Notes verify(String token) {
		try {
			long id = jwtGenerator.parse(token);
			Notes note = noteRepository.findById(id);
			if (!note.isVerified()) {
				noteRepository.updateIsVarified(id);
				System.out.println("save details");
				return note;
			} else {
				System.out.println("already varified");
				return note;
			}
		} catch (JWTVerificationException | IllegalArgumentException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return null;
	}

}
