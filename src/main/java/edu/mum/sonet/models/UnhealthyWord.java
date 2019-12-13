package edu.mum.sonet.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Jonathan on 12/12/2019.
 */

@Data
@Entity
@Table(name = "unhealthy_words")
public class UnhealthyWord extends BaseEntity {
	private String word;
}
