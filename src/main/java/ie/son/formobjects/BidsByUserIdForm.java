package ie.son.formobjects;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BidsByUserIdForm {
	@NotNull
	@Min(1) 
	@Max(5000000)
	private int userId;
	
	public BidsByUserIdForm(int userid) {
		this.userId = userid;
	}	
}
