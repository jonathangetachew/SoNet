package edu.mum.sonet.models;

import javax.persistence.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Comment extends BaseEntity {

	private String text;
	private Boolean isHealthy;
}
