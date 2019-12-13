package edu.mum.sonet.models;

import javax.persistence.Entity;
import javax.persistence.Lob;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Comment extends BaseEntity {

	@Lob
	private String text;
	private Boolean isHealthy = true;
	private Boolean isDisabled = false;
}
