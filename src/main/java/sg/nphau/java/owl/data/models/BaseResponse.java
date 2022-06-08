package sg.nphau.java.owl.data.models;

import javax.annotation.Nullable;

public class BaseResponse<T> {
    private String query;
    private @Nullable T data;

    public BaseResponse(String query, @Nullable T data) {
        this.query = query;
        this.data = data;
    }

    @Nullable
    public T getData() {
        return data;
    }

    public void setData(@Nullable T data) {
        this.data = data;
    }
}
