package edu.mum.sonet.models;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.mum.sonet.models.enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@Entity
public class Advertisement extends BaseEntity {

	@Lob
	@URL(message = "{error.url}")
	@NotBlank(message = "{error.NotBlank}")
	private String contentUrl;

	@Transient
	@JsonIgnore
	private MultipartFile imageFile;

	@Lob
	@NotBlank(message = "{error.NotBlank}")
	private String text;

	@URL(message = "{error.url}")
	@NotBlank(message = "{error.NotBlank}")
	private String adUrl;

	private String targetLocation;

	@NotNull(message = "{error.NotNull}")
	@Min(13)
	@Max(100)
	private Integer targetAge;

	@NotNull(message = "{error.NotNull}")
	@Enumerated(EnumType.STRING)
	private Gender targetGender;
	
}
