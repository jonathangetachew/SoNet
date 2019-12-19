package edu.mum.sonet.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Claim extends BaseEntity {

	@NotNull
	private LocalDate claimDate;

	@NotBlank
	private String message;

	private Boolean isActive = true;

	@NotNull
	@OneToOne
	private User user;
}
