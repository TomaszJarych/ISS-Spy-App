package tj.javadeveloper.issspyapp.domain.resttempalte;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssPredictedPass implements Serializable {

    @JsonProperty("message")
    private String message;
    @JsonProperty("request")
    private IssPassRequestData requestData;
    @JsonProperty("response")
    private List<IssPassData> passesList = new ArrayList<>();

    @Builder
    private IssPredictedPass(String message, IssPassRequestData requestData, List<IssPassData> passesList) {
        this.message = message;
        this.requestData = requestData;
        this.passesList = passesList;
    }
}
