package bobtranslate.util;

import java.io.IOException;

/**
 * The {@code BadStatusCode} class.
 */
public final class BadStatusCode extends IOException {
    /**
     * Creates a new instance.
     *
     * @param apiName the api name
     * @param statusCode the status code
     * @param expected the expected
     */
    public BadStatusCode(final String apiName, final int statusCode, final String expected) {
        super(apiName + " returned incorrect status code " + statusCode + ", expected " + expected);
    }
}
