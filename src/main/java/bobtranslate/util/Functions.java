package bobtranslate.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.Files.readString;

/**
 * The {@code Functions} enum.
 */
public enum Functions {;

    private static final Type MAP_TYPE = new TypeToken<Map<String, String>>() {}.getType();
    /**
     * Returns the result of {@code loadTranslationsMap}.
     *
     * @param gson the gson
     * @param path the path
     * @return the result
     * @throws IOException if the operation fails
     */
    public static Map<String, String> loadTranslationsMap(final Gson gson, final Path path) throws IOException {
        final Map<String, String> result = gson.fromJson(readString(path), MAP_TYPE);
        return result == null ? new HashMap<>() : result;
    }

    /**
     * Returns the result of {@code isSuccessFul}.
     *
     * @param response the response
     * @return whether the condition holds
     */
    public static boolean isSuccessFul(final HttpResponse<?> response) {
        return response.statusCode() >= 200 && response.statusCode() <= 299;
    }

}
