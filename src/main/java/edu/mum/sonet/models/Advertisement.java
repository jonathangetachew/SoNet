package edu.mum.sonet.models;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.mum.sonet.models.enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@Entity
public class Advertisement extends BaseEntity {

	@Lob
	private String contentUrl;

	@Transient
	@JsonIgnore
	private MultipartFile imageFile;

	@Lob
	private String text;

	private String adUrl;;
	private String targetLocation;
	private Integer targetAge;
	private Gender targetGender;
	
}
