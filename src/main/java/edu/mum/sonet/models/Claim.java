package edu.mum.sonet.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Claim extends BaseEntity {

	private LocalDate claimDate;
	private String message;
	private Boolean isActive = true;

	@OneToOne
	private User user;
}
