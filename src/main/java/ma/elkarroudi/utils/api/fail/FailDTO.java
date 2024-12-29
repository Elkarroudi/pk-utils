package ma.elkarroudi.utils.api.fail;

import com.fasterxml.jackson.annotation.JsonInclude;
import ma.elkarroudi.utils.api.ApiResponse;

import java.io.Serializable;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record FailDTO(

        ApiResponse apiResponse,
        boolean success,
        FailType type,
        String message,
        Map<String, Object> errors

)  implements Serializable {

    public FailDTO(int httpStatus, FailType type, Map<String, Object> errors) {
        this( new ApiResponse(httpStatus),  false, type, null, errors );
    }

    public FailDTO(int httpStatus, FailType type, String message) {
        this( new ApiResponse(httpStatus),  false, type, message, null );
    }

}
