package sls.meetup.itau.kafka.producer.util;

public class BuilderRequestUtil {

    private String url;

    private static final String URL_SIGNATURE = "/signature";

    public BuilderRequestUtil getSignatureURL() {
        this.url = URL_SIGNATURE;
        return this;
    }

    public String build(){
        return this.url;
    }

}
