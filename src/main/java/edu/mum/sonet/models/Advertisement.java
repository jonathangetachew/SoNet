package edu.mum.sonet.models;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.mum.sonet.models.enums.Gender;
import edu.mum.sonet.models.enums.Location;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
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
	@NotBlank
	private String text;

	@URL
	@NotBlank
	private String adUrl;

	@Enumerated(EnumType.STRING)
	private Location location;

	@NotNull
	@Min(13)
	@Max(100)
	private Integer targetAge;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Gender targetGender;
	
}
