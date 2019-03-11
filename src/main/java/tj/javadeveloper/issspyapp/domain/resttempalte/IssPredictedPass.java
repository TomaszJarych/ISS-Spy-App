package tj.javadeveloper.issspyapp.domain.resttempalte;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssPredictedPass implements Serializable {

    @JsonProperty("message")
    private String message;
    @JsonProperty("request")
    private IssPassRequestData requestData;
    @JsonProperty("response")
    private List<IssPassData> passesList = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public IssPassRequestData getRequestData() {
        return requestData;
    }

    public void setRequestData(IssPassRequestData requestData) {
        this.requestData = requestData;
    }

    public List<IssPassData> getPassesList() {
        return passesList;
    }

    public void setPassesList(List<IssPassData> passesList) {
        this.passesList = passesList;
    }

    @Override
    public String toString() {
        return "IssPredictedPass{" +
                "message='" + message + '\'' +
                ", requestData=" + requestData +
                ", passesList=" + passesList +
                '}';
    }
}
