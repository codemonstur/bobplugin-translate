package bobtranslate;

import bobtranslate.actions.MissingLanguageKeys;
import bobtranslate.actions.MissingTranslations;
import bobtranslate.util.BadStatusCode;
import bobtranslate.util.Functions;
import bobtranslate.util.MultiPartBodyPublisher;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Flow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TranslateTest {

    @Test
    void badStatusCode_message() {
        final var e = new BadStatusCode("Api", 404, "200");
        assertTrue(e.getMessage().contains("404"));
    }

    @Test
    void functions_loadTranslationsMap(@TempDir Path dir) throws IOException {
        final Path file = dir.resolve("en.json");
        Files.writeString(file, "{\"a\":\"hello\",\"b\":\"\"}");
        final Map<String, String> map = Functions.loadTranslationsMap(new Gson(), file);
        assertEquals("hello", map.get("a"));
        assertEquals("", map.get("b"));
    }

    @Test
    void functions_loadTranslationsMap_emptyOrNull(@TempDir Path dir) throws IOException {
        final Path file = dir.resolve("empty.json");
        Files.writeString(file, "null");
        final Map<String, String> map = Functions.loadTranslationsMap(new Gson(), file);
        assertTrue(map.isEmpty());
    }

    @Test
    void functions_isSuccessFul() {
        final HttpResponse<?> ok = fakeResponse(200);
        final HttpResponse<?> fail = fakeResponse(500);
        assertTrue(Functions.isSuccessFul(ok));
        assertFalse(Functions.isSuccessFul(fail));
    }

    @Test
    void multiPartBodyPublisher_noPartsThrows() {
        final var pub = new MultiPartBodyPublisher();
        assertThrows(IllegalStateException.class, pub::build);
    }

    @Test
    void multiPartBodyPublisher_buildAndConsume() throws Exception {
        final var pub = new MultiPartBodyPublisher();
        pub.addPart("field", "value");
        final var bodyPublisher = pub.build();
        assertTrue(bodyPublisher.contentLength() == -1 || bodyPublisher.contentLength() > 0);
        // consume
        final java.util.List<java.nio.ByteBuffer> collected = new java.util.ArrayList<>();
        bodyPublisher.subscribe(new Flow.Subscriber<>() {
            private Flow.Subscription sub;
            public void onSubscribe(Flow.Subscription s) { this.sub = s; s.request(Long.MAX_VALUE); }
            public void onNext(java.nio.ByteBuffer item) { collected.add(item); }
            public void onError(Throwable t) { throw new RuntimeException(t); }
            public void onComplete() {}
        });
        Thread.sleep(200);
        assertFalse(collected.isEmpty());
    }

    @Test
    void multiPartBodyPublisher_filePart(@TempDir Path dir) throws Exception {
        final Path file = dir.resolve("f.txt");
        Files.writeString(file, "filecontent");
        final var pub = new MultiPartBodyPublisher();
        pub.addPart("file", file);
        final var bp = pub.build();
        assertNotNull(bp);
    }

    @Test
    void multiPartBodyPublisher_streamPart() throws Exception {
        final var pub = new MultiPartBodyPublisher();
        pub.addPart("stream", () -> new java.io.ByteArrayInputStream("data".getBytes(StandardCharsets.UTF_8)), "f.txt", "text/plain");
        final var bp = pub.build();
        assertNotNull(bp);
    }

    @Test
    void missingLanguageKeys_checkKeysInDir(@TempDir Path dir) throws IOException {
        Files.writeString(dir.resolve("a.json"), "{\"x\":\"1\",\"y\":\"2\"}");
        Files.writeString(dir.resolve("b.json"), "{\"x\":\"1\"}");
        int rc = MissingLanguageKeys.checkKeysInDir(dir, msg -> {});
        assertEquals(1, rc);
    }

    @Test
    void missingTranslations_checkValuesInDir(@TempDir Path dir) throws IOException {
        Files.writeString(dir.resolve("a.json"), "{\"x\":\"\",\"y\":\"v\"}");
        int rc = MissingTranslations.checkValuesInDir(dir, msg -> {});
        assertEquals(1, rc);
    }

    @SuppressWarnings("unchecked")
    private static HttpResponse<?> fakeResponse(int status) {
        return new HttpResponse<>() {
            public int statusCode() { return status; }
            public HttpRequest request() { return null; }
            public java.util.Optional<HttpResponse<Object>> previousResponse() { return java.util.Optional.empty(); }
            public java.net.http.HttpHeaders headers() { return null; }
            public Object body() { return null; }
            public java.net.URI uri() { return null; }
            public java.net.http.HttpClient.Version version() { return java.net.http.HttpClient.Version.HTTP_1_1; }
            public java.util.Optional<javax.net.ssl.SSLSession> sslSession() { return java.util.Optional.empty(); }
        };
    }

}
