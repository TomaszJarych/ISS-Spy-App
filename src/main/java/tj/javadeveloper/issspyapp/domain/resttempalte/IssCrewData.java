package tj.javadeveloper.issspyapp.domain.resttempalte;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssCrewData implements Serializable {

    @JsonProperty("message")
    private String message;
    @JsonProperty("number")
    private Long number;
    @JsonProperty("people")
    private List<IssCrewMember> people = new ArrayList<>();
}
