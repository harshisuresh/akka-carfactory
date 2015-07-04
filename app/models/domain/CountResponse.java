package models.domain;

public class CountResponse {

    final long count;

    public CountResponse(long count) {
        this.count = count;
    }

    public long getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "CountResponse{" +
                "count=" + count +
                '}';
    }
}
