package co.parrolabs.util;

public final class MongoDbConstants {

    public static final String DELETED = "DELETED";
    public static final String ERROR = "ERROR";

    public static class Collections{
        public static final String CUSTOMER_COLLECTION = "#customerMongoDb";
        public static final String ORDER_CUSTOMER_COLLECTION = "#orderCustomerMongoDb";
        public static final String ORDER_PRODUCT_COLLECTION = "#orderProductMongoDb";
        public static final String PAYMENT_TYPE_COLLECTION = "#paymentTypeMongoDb";
        public static final String PRODUCT_COLLECTION = "#productMongoDb";
        public static final String SHIPPING_ADDRESS_COLLECTION = "#shippingAddressMongoDb";
    }

}
