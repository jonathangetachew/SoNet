package edu.mum.sonet.models;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Comment extends BaseEntity {

	@Lob
	@NotBlank
	private String text;

	private Boolean isHealthy = true;
	private Boolean isDisabled = false;
}
