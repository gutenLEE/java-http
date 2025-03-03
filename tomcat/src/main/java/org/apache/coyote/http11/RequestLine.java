package org.apache.coyote.http11;


import java.util.Map;

public class RequestLine {

    private final static String DELIMITER = " ";
    private final static int REQUEST_LINE_CHUNK_LIMIT = 3;
    public final static RequestLine SERVER_ERROR_REQUEST_LINE = new RequestLine("GET /500.html HTTP/1.1");

    private HttpMethod method;
    private Path path;
    private HttpProtocol protocol;

    public RequestLine(String requestLine) {
        String[] infos = split(requestLine);
        if (infos.length != REQUEST_LINE_CHUNK_LIMIT) {
            throw new IllegalArgumentException("RequestLine is invalid: " + requestLine);
        }
        this.method = HttpMethod.from(infos[0]);
        this.path = new Path(infos[1]);
        this.protocol = HttpProtocol.from(infos[2]);
    }

    public boolean pathEndsWith(String value) {
        return path.endsWith(value);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path.getPath();
    }

    public Map<String, Object> getQueryParamMap() {
        return path.getQueryParamMap();
    }

    public HttpProtocol getHttpProtocol() {
        return protocol;
    }

    public String getProtocol() {
        return protocol.getProtocol();
    }

    public String getVersion() {
        return protocol.getVersion();
    }


    private static String[] split(String requestLine) {
        if (requestLine == null || requestLine.isEmpty()) {
            throw new HttpRequestLineInvalidException("RequestLine is null or empty: " + requestLine);
        }
        return requestLine.split(DELIMITER);
    }
}
