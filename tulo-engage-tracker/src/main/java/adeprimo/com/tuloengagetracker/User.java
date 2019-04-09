package adeprimo.com.tuloengagetracker;

public class User {
    private final String userId;
    private final String paywayId;
    private final String[] states;
    private final String[] products;
    private final String positionLon;
    private final String positionLat;
    private final String location;


    private User(Builder builder) {
        this.userId = builder.userId;
        this.paywayId = builder.paywayId;
        this.states = builder.states;
        this.products = builder.products;
        this.positionLon = builder.positionLon;
        this.positionLat = builder.positionLat;
        this.location = builder.location;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getUserId() {
        return userId;
    }

    public String getPaywayId() {
        return paywayId;
    }

    public String[] getStates() {
        return states;
    }

    public String[] getProducts() {
        return products;
    }

    public String getPositionLon() {
        return positionLon;
    }

    public String getPositionLat() {
        return positionLat;
    }

    public String getLocation() {
        return location;
    }

    public static final class Builder {

        private String userId;
        private String paywayId;
        private String[] states;
        private String[] products;
        private String positionLon;
        private String positionLat;
        private String location;

        private Builder() {

        }
        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder paywayId(String paywayId) {
            this.paywayId = paywayId;
            return this;
        }

        public Builder states(String[] states) {
            this.states = states;
            return this;
        }

        public Builder products(String[] products) {
            this.products = products;
            return this;
        }

        public Builder position(String positionLon, String positionLat) {
            this.positionLon = positionLon;
            this.positionLat = positionLat;
            return this;
        }

        public Builder location(String location) {
            this.location = location;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }


}
