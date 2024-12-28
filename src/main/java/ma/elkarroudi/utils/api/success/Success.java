package ma.elkarroudi.utils.api.success;

import ma.elkarroudi.utils.api.ApiResponse;

import java.io.Serializable;

public record Success<RES>(

        ApiResponse apiResponse,
        boolean success,
        RES data

) implements Serializable {

    public Success(int httpStatus, RES data) {
        this(
                new ApiResponse(httpStatus),
                true,
                data
        );
    }

}
