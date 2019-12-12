package edu.mum.sonet.models;

import javax.persistence.*;

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

	@Enumerated(EnumType.STRING)
	private Gender targetGender;
	
}
