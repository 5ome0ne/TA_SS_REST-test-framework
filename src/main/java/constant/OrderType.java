package constant;

public enum OrderType {
    ASC("asc"),
    DESC("desc");

    String order;

    OrderType(String order) {
        this.order = order;
    }

    public String getOrder() {
        return order;
    }
}
