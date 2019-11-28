package edu.mum.sonet.models;

import javax.persistence.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Advertisment extends BaseEntity{

	private String contentUrl;
	private String text;
	private String adUrl;;
	private String targetLocation;
	private String targetAge;
	private Gender targetGender; 
	
}
