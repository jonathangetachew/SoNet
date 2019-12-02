package edu.mum.sonet.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Claim extends BaseEntity {

	private LocalDate cliamDate;
	private String message;
	private Boolean isAccepted;
	
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnoreProperties(value= {"claims"})
	private User user;
}
