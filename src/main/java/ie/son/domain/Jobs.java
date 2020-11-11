package ie.son.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jobs {
	private int jobId;
	private String jobName;
	private String description;
}
