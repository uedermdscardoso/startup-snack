package dev.uedercardoso.snack.web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.uedercardoso.snack.domain.model.snack.Snack;
import dev.uedercardoso.snack.domain.services.SnackService;
import dev.uedercardoso.snack.exceptions.EmptyListException;
import dev.uedercardoso.snack.exceptions.SnackNotFoundException;

@RestController
@RequestMapping("/snacks")
public class SnackController {

	@Autowired
	private SnackService snackService;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<Snack>> findAll(){
		try {
			List<Snack> snacks = this.snackService.getAllSnacks();
			
			return ResponseEntity.ok(snacks);
			
		} catch(EmptyListException e) {
			return ResponseEntity.noContent().build();	
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Void> save(@Valid @RequestBody List<Snack> snacks){
		try {
			
			this.snackService.save(snacks);
			
			return ResponseEntity.ok().build();
			
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Void> saveById(@RequestBody Long id, @Valid @RequestBody Snack snack){
		try {
			
			this.snackService.save(id,snack);
			
			return ResponseEntity.ok().build();
			
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Void> delete(@RequestParam Long id){
		try {
			
			this.snackService.delete(id);
			
			return ResponseEntity.ok().build();
			
		} catch(SnackNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
}
