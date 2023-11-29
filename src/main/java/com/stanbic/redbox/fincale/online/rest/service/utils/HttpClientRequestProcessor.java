package com.stanbic.redbox.fincale.online.rest.service.utils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpClientRequestProcessor {
    private static final String CAPTURABLE_CHAR_SEQUENCE_FOLLOWED_BY_BACKWARD_SLASH = "(.*)\\";
    private static final String AN_ERROR_OCCURED = "An error occured: {}";
    private static final String RESPONSE_ELEMENT_LEFT_ENCLOSING_DELIMITER = "[";
    private static final String RESPONSE_ELEMENT_RIGHT_ENCLOSING_DELIMITER = "]";
    private static final String RESPONSE_STRING_BODY = "HttpResponseBody";
    private static final String RESPONSE_STRING_HEADERS = "HttpResponseHeaders";
    private static final String RESPONSE_STRING_STATUS_LINE = "HttpResponseStatusLine";
    private static final String BODY_STRING_REGEX_PATTERN = "(HttpResponseBody\\[(.*)\\](?=HttpResponseStatusLine)|HttpResponseBody\\[(.*)\\](?=HttpResponseHeaders)|HttpResponseBody\\[(.*)\\])";
    private static final int CONNECTTION_REQUEST_TIMEOUT = 60000;
    private static final int CONNECTTION_TIMEOUT = 60000;
    private static final String ENCODING = "UTF-8";
    private static final String HEADER_STRING_REGEX_PATTERN = "(HttpResponseHeaders\\[(.*)\\](?=HttpResponseStatusLine)|HttpResponseHeaders\\[(.*)\\](?=HttpResponseBody)|HttpResponseHeaders\\[(.*)\\])";
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientRequestProcessor.class);
    private static final String IS_HEADERS_HASH_MAP_EMPTY = "Is headersMap empty? {}";
    private static final String IS_HEADERS_HASH_MAP_NULL = "Is headersMap null? {}";
    private static final String IS_URL_EMPTY = "Is url empty? {}";
    private static final String IS_URL_NULL = "Is url null? {}";
    private static final String MATCHED_REGEX = "Matched: {}";
    private static final String RESPONSE_ELEMENT_DELIMITER = ":::";
    private static final int SOCKET_TIMEOUT = 30000;
    private static final String STATUS_LINE_STRING_REGEX_PATTERN = "(HttpResponseStatusLine\\[(.*)\\](?=HttpResponseHeaders)|HttpResponseStatusLine\\[(.*)\\](?=HttpResponseBody)|HttpResponseStatusLine\\[(.*)\\])";
    private HttpClientRequestProcessor() {
        throw new IllegalStateException("HttpClientRequestProcessor is a Utility class");
    }
    private static CloseableHttpClient createCloseableHttpClient(boolean isSecuredProtocol, String[] supportedProtocols, boolean useProxy, boolean proxyAuthenticationFlag, CredentialsProvider credentialsProvider, String proxyHost, int proxyPort, String proxyUsername, String proxyPassword, boolean clientAuthenticationIsRequired, String destinationServerAddress, int destinationServerPort, String destinationServerUsername, String destinationServerPassword, RequestConfig requestConfig) {
        BasicCredentialsProvider basicCredentialsProvider = null;
        LOGGER.debug("Executing createCloseableHttpClient");
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(isSecuredProtocol + " | " + supportedProtocols.length + " | " + ((supportedProtocols.length >= 1) ? supportedProtocols[0] : "supportedProtocols is empty"));
        LOGGER.debug("{}", stringBuilder);
        if (isSecuredProtocol) {
            SSLConnectionSocketFactory sslConnectionSocketFactory = null;
            try {
                sslConnectionSocketFactory = createSslConnectionSocketFactory(supportedProtocols[0], supportedProtocols);
            } catch (Exception e) {
                LOGGER.error("An error occured: {}", e);
            }
            httpClientBuilder.setSSLSocketFactory((LayeredConnectionSocketFactory) sslConnectionSocketFactory);
        }
        if (useProxy && proxyAuthenticationFlag) {
            if (credentialsProvider == null) {
                basicCredentialsProvider = new BasicCredentialsProvider();
            }
            basicCredentialsProvider.setCredentials(new AuthScope(proxyHost, proxyPort), (Credentials) new UsernamePasswordCredentials(proxyUsername, proxyPassword));
        }
        if (clientAuthenticationIsRequired) {
            if (basicCredentialsProvider == null) {
                basicCredentialsProvider = new BasicCredentialsProvider();
            }
            basicCredentialsProvider.setCredentials(new AuthScope(destinationServerAddress, destinationServerPort), (Credentials) new UsernamePasswordCredentials(destinationServerUsername, destinationServerPassword));
        }
        if (basicCredentialsProvider != null) {
            httpClientBuilder.setDefaultCredentialsProvider((CredentialsProvider) basicCredentialsProvider);
        }
        httpClientBuilder.setDefaultRequestConfig(requestConfig);
        return httpClientBuilder.build();
    }

    private static HttpDelete createHttpDeleteObject(Map<String, Object> headersMap, String url) {
        LOGGER.debug("Executing createHttpDeleteObject");
        LOGGER.debug("Is url null? {}", Boolean.valueOf((url == null)));
        LOGGER.debug("Is url empty? {}", Boolean.valueOf(url.trim().isEmpty()));
        LOGGER.debug("Is headersMap null? {}", Boolean.valueOf((headersMap == null)));
        LOGGER.debug("Is headersMap empty? {}", Boolean.valueOf(headersMap.isEmpty()));
        HttpDelete httpDelete = new HttpDelete(url);
        if (!headersMap.isEmpty()) {
            Header[] headers = getHttpRequestHeader(headersMap);
            httpDelete.setHeaders(headers);
        }
        return httpDelete;
    }

    private static HttpEntityEnclosingRequestBase createHttpDeleteObjectWithRequestBody(Map<String, Object> headersMap, String url, String encoding, String httpRequestMessage) {
        HttpEntityEnclosingRequestBase httpDeleteWithBody = new HttpEntityEnclosingRequestBase() {
            static final String METHOD_NAME = "DELETE";
            public String getMethod() {
                return "DELETE";
            }
        };

        httpDeleteWithBody.setURI(URI.create(url));
        if (!headersMap.isEmpty()) {
            Header[] headers = getHttpRequestHeader(headersMap);
            httpDeleteWithBody.setHeaders(headers);
        }
        StringEntity s = new StringEntity(httpRequestMessage, encoding);
        httpDeleteWithBody.setEntity((HttpEntity) s);
        return httpDeleteWithBody;
    }

    private static HttpGet createHttpGetObject(Map<String, Object> headersMap, String url) {
        LOGGER.debug("Executing createHttpGetObject");
        LOGGER.debug("Is url null? {}", Boolean.valueOf((url == null)));
        LOGGER.debug("Is url empty? {}", Boolean.valueOf(url.trim().isEmpty()));
        LOGGER.debug("Is headersMap null? {}", Boolean.valueOf((headersMap == null)));
        LOGGER.debug("Is headersMap empty? {}", Boolean.valueOf(headersMap.isEmpty()));
        HttpGet httpGet = new HttpGet(url);
        if (!headersMap.isEmpty()) {
            Header[] headers = getHttpRequestHeader(headersMap);
            httpGet.setHeaders(headers);
        }
        return httpGet;
    }

    private static HttpHead createHttpHeadObject(Map<String, Object> headersMap, String url) {
        LOGGER.debug("Executing createHttpHeadObject");
        HttpHead httpHead = new HttpHead(url);
        if (!headersMap.isEmpty()) {
            Header[] headers = getHttpRequestHeader(headersMap);
            httpHead.setHeaders(headers);
        }
        return httpHead;
    }

    private static HttpOptions createHttpOptionsObject(Map<String, Object> headersMap, String url) {
        LOGGER.debug("Executing createHttpOptionsObject");
        HttpOptions httpOptions = new HttpOptions(url);
        if (!headersMap.isEmpty()) {
            Header[] headers = getHttpRequestHeader(headersMap);
            httpOptions.setHeaders(headers);
        }
        return httpOptions;
    }


    private static HttpPatch createHttpPatchObject(Map<String, Object> headersMap, String url, String encoding, String httpRequestMessage) {
        LOGGER.debug("Executing createHttpPatchObject");
        HttpPatch httpPatch = new HttpPatch(url);
        if (!headersMap.isEmpty()) {
            Header[] headers = getHttpRequestHeader(headersMap);
            httpPatch.setHeaders(headers);
        }

        StringEntity s = new StringEntity(httpRequestMessage, (encoding == null || encoding.trim().isEmpty()) ? "UTF-8" : encoding);
        httpPatch.setEntity((HttpEntity) s);
        return httpPatch;
    }


    private static HttpPost createHttpPostObject(Map<String, Object> headersMap, String url, String encoding, String httpRequestMessage) {
        /* 244 */
        LOGGER.debug("Executing createHttpPostObject");
        /* 245 */
        LOGGER.debug("Is url null? {}", Boolean.valueOf((url == null)));
        /* 246 */
        LOGGER.debug("Is url empty? {}", Boolean.valueOf(url.trim().isEmpty()));
        /* 247 */
        LOGGER.debug("Is headersMap null? {}", Boolean.valueOf((headersMap == null)));
        /* 248 */
        LOGGER.debug("Is headersMap empty? {}", Boolean.valueOf(headersMap.isEmpty()));
        /* 249 */
        HttpPost httpPost = new HttpPost(url);
        /* 250 */
        if (!headersMap.isEmpty()) {
            /* 251 */
            Header[] headers = getHttpRequestHeader(headersMap);
            /* 252 */
            httpPost.setHeaders(headers);
        }

        /* 255 */
        StringEntity s = new StringEntity(httpRequestMessage, (encoding == null || encoding.trim().isEmpty()) ? "UTF-8" : encoding);
        /* 256 */
        httpPost.setEntity((HttpEntity) s);
        /* 257 */
        return httpPost;
    }


    private static HttpPut createHttpPutObject(Map<String, Object> headersMap, String url, String encoding, String httpRequestMessage) {
        /* 262 */
        LOGGER.debug("Executing createHttpPutObject");
        /* 263 */
        LOGGER.debug("Is url null? {}", Boolean.valueOf((url == null)));
        /* 264 */
        LOGGER.debug("Is url empty? {}", Boolean.valueOf(url.trim().isEmpty()));
        /* 265 */
        LOGGER.debug("Is headersMap null? {}", Boolean.valueOf((headersMap == null)));
        /* 266 */
        LOGGER.debug("Is headersMap empty? {}", Boolean.valueOf(headersMap.isEmpty()));
        /* 267 */
        HttpPut httpPut = new HttpPut(url);
        /* 268 */
        if (!headersMap.isEmpty()) {
            /* 269 */
            Header[] headers = getHttpRequestHeader(headersMap);
            /* 270 */
            httpPut.setHeaders(headers);
        }

        /* 273 */
        StringEntity s = new StringEntity(httpRequestMessage, (encoding == null || encoding.trim().isEmpty()) ? "UTF-8" : encoding);
        /* 274 */
        httpPut.setEntity((HttpEntity) s);
        /* 275 */
        return httpPut;
    }

    private static Header[] getHttpRequestHeader(Map<String, Object> headersMap) {
        /* 279 */
        BasicHeader[] arrayOfBasicHeader = new BasicHeader[headersMap.size()];
        /* 280 */
        int i = 0;
        /* 281 */
        for (Map.Entry<String, Object> entry : headersMap.entrySet()) {
            /* 282 */
            String key = entry.getKey();
            /* 283 */
            arrayOfBasicHeader[i] = new BasicHeader(key, headersMap.get(key).toString());
            /* 284 */
            i++;
        }
        /* 286 */
        return (Header[]) arrayOfBasicHeader;
    }


    private static HttpRequestBase createHttpRequestObject(String httpMethod, Map<String, Object> headersMap, String url, String encoding, String httpRequestMessage) throws Exception {
        /* 291 */
        LOGGER.debug("Executing createHttpRequestObject");
        /* 292 */
        if (httpMethod != null && httpMethod.equals("DELETE"))
            /* 293 */ return (httpRequestMessage == null) ? (HttpRequestBase) createHttpDeleteObject(headersMap, url) :
                /* 294 */         (HttpRequestBase) createHttpDeleteObjectWithRequestBody(headersMap, url, encoding, httpRequestMessage);
        /* 295 */
        if (httpMethod != null && httpMethod.equals("GET"))
            /* 296 */ return (HttpRequestBase) createHttpGetObject(headersMap, url);
        /* 297 */
        if (httpMethod != null && httpMethod.equals("HEAD"))
            /* 298 */ return (HttpRequestBase) createHttpHeadObject(headersMap, url);
        /* 299 */
        if (httpMethod != null && httpMethod.equals("OPTIONS"))
            /* 300 */ return (HttpRequestBase) createHttpOptionsObject(headersMap, url);
        /* 301 */
        if (httpMethod != null && httpMethod.equals("PATCH"))
            /* 302 */ return (HttpRequestBase) createHttpPatchObject(headersMap, url, encoding, httpRequestMessage);
        /* 303 */
        if (httpMethod != null && httpMethod.equals("POST"))
            /* 304 */ return (HttpRequestBase) createHttpPostObject(headersMap, url, encoding, httpRequestMessage);
        /* 305 */
        if (httpMethod != null && httpMethod.equals("PUT"))
            /* 306 */ return (HttpRequestBase) createHttpPutObject(headersMap, url, encoding, httpRequestMessage);
        /* 307 */
        if (httpMethod != null && httpMethod.equals("TRACE")) {
            /* 308 */
            return (HttpRequestBase) createHttpTraceObject(headersMap, url);
        }
        /* 310 */
        throw new Exception("Invalid HttpMethod parameter");
    }

    private static StatusLine createHttpResponseStatusLineFromString(StatusLine statusLine, String statusLineString) {
        BasicStatusLine basicStatusLine = null;
        /* 315 */
        LOGGER.debug("Executing createHttpResponseStatusLineFromString");
        /* 316 */
        if (statusLineString != null && !statusLineString.trim().isEmpty()) {
            /* 317 */
            String[] statusLineComponents = statusLineString.split(":::");
            /* 318 */
            if (statusLineComponents.length == 3) {



                /* 322 */
                ProtocolVersion version = new ProtocolVersion(statusLineComponents[0].split("/")[0], Integer.parseInt(statusLineComponents[0].split("/")[1].substring(0, statusLineComponents[0].split("/")[1].indexOf('.'))), Integer.parseInt(statusLineComponents[0].split("/")[1]
/* 323 */.substring(statusLineComponents[0].split("/")[1].indexOf('.') + 1)));
                /* 324 */
                int statusCode = Integer.parseInt(statusLineComponents[1]);
                /* 325 */
                String reasonPhrase = statusLineComponents[2];
                /* 326 */
                basicStatusLine = new BasicStatusLine(version, statusCode, reasonPhrase);
            }
        }
        /* 329 */
        return (StatusLine) basicStatusLine;
    }

    private static HttpTrace createHttpTraceObject(Map<String, Object> headersMap, String url) {
        /* 333 */
        LOGGER.debug("Executing createHttpTraceObject");
        /* 334 */
        HttpTrace httpTrace = new HttpTrace(url);
        /* 335 */
        if (!headersMap.isEmpty()) {
            /* 336 */
            Header[] headers = getHttpRequestHeader(headersMap);
            /* 337 */
            httpTrace.setHeaders(headers);
        }
        /* 339 */
        return httpTrace;
    }


    private static RequestConfig createRequestConfig(boolean useProxy, String proxyHost, int proxyPort, int socketTimeout, int connectionTimeout) {
        /* 344 */
        LOGGER.debug("Executing createRequestConfig");
        /* 345 */
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        /* 346 */
        if (useProxy) {
            /* 347 */
            HttpHost proxy = new HttpHost(proxyHost, proxyPort);
            /* 348 */
            requestConfigBuilder.setProxy(proxy);
        }

        /* 351 */
        if (socketTimeout >= 0 && socketTimeout != 30000) {
            /* 352 */
            requestConfigBuilder.setSocketTimeout(socketTimeout);
        } else {
            /* 354 */
            requestConfigBuilder.setSocketTimeout(30000);
        }

        /* 357 */
        if (connectionTimeout >= 0 && connectionTimeout != 60000) {
            /* 358 */
            requestConfigBuilder.setConnectTimeout(connectionTimeout);
        } else {
            /* 360 */
            requestConfigBuilder.setConnectTimeout(60000);
        }
        /* 362 */
        return requestConfigBuilder.build();
    }


    private static SSLConnectionSocketFactory createSslConnectionSocketFactory(String defaultSslProtocol, String[] supportedProtocols) throws Exception {
        /* 367 */
        LOGGER.debug("Executing createSslConnectionSocketFactory");
        /* 368 */
        SSLConnectionSocketFactory sslConnectionSocketFactory = null;


        /* 371 */
        NoopHostnameVerifier noopHostnameVerifier = new NoopHostnameVerifier() {
            public boolean verify(String requestedHost, SSLSession sslSession) {
                /* 375 */
                return true;
            }
        };

        /* 379 */
        sslConnectionSocketFactory = new SSLConnectionSocketFactory(createSslContext(defaultSslProtocol), supportedProtocols, null, (HostnameVerifier) noopHostnameVerifier);


        /* 382 */
        return sslConnectionSocketFactory;
    }

    private static SSLContext createSslContext(String defaultSslProtocol) throws Exception {
        /* 386 */
        LOGGER.debug("Executing createSslContext");
        /* 387 */
        return (new SSLContextBuilder()).setProtocol(defaultSslProtocol).loadTrustMaterial(null, (TrustStrategy) new TrustAllStrategy())
/* 388 */.build();
    }

    private static void getHttpHeaderElementFromHttpHeaderString(List<Header> headers, String httpHeaderString) {
        /* 392 */
        LOGGER.debug("Executing getHttpHeaderElementFromHttpHeaderString");
        /* 393 */
        if (httpHeaderString != null && !httpHeaderString.trim().isEmpty()) {
            /* 394 */
            String[] headerComponents = httpHeaderString.split(":::");
            /* 395 */
            for (String headerComponent : headerComponents) {
                /* 396 */
                LOGGER.debug("Retrieved... {}", headerComponent);
                /* 397 */
                if (headerComponent.startsWith(":")) {
                    /* 398 */
                    headerComponent.substring(1);
                    /* 399 */
                } else if (headerComponent.contains(":")) {

                    /* 401 */
                    BasicHeader basicHeader = new BasicHeader(headerComponent.substring(0, headerComponent.indexOf(':')), headerComponent.substring(headerComponent.indexOf(':') + 1));
                    /* 402 */
                    headers.add(basicHeader);
                } else {
                    /* 404 */
                    BasicHeader basicHeader = new BasicHeader(headerComponent, "");
                    /* 405 */
                    headers.add(basicHeader);
                }
            }
        }
    }

    public static String getHttpResponseBodyFromHttpResponseString(String httpResponseString) {
        /* 412 */
        LOGGER.debug("Executing getHttpResponseBodyFromHttpResponseString");
        /* 413 */
        String httpResponseBody = null;
        /* 414 */
        if (httpResponseString != null && !httpResponseString.trim().isEmpty()) {
            /* 415 */
            Pattern pattern = Pattern.compile("(HttpResponseBody\\[(.*)\\](?=HttpResponseStatusLine)|HttpResponseBody\\[(.*)\\](?=HttpResponseHeaders)|HttpResponseBody\\[(.*)\\])");
            /* 416 */
            Matcher matcher = pattern.matcher(httpResponseString);
            /* 417 */
            while (matcher.find()) {
                /* 418 */
                httpResponseBody = matcher.group(1);
            }
            /* 420 */
            if (httpResponseBody != null && !httpResponseBody.trim().isEmpty()) {
                /* 421 */
                Matcher matcher2 = pattern.matcher(httpResponseBody);
                /* 422 */
                while (matcher2.find()) {
                    /* 423 */
                    httpResponseBody = matcher2.group(4);
                }
            }
            /* 426 */
            LOGGER.debug("Matched: {}", httpResponseBody);
        }
        /* 428 */
        return httpResponseBody;
    }

    public static List<Header> getHttpResponseHeaderFromHttpResponseString(String httpResponseString) {
        /* 432 */
        LOGGER.debug("Executing getHttpResponseHeaderFromHttpResponseString");
        /* 433 */
        List<Header> headers = new ArrayList<>();
        /* 434 */
        String headersString = null;
        /* 435 */
        if (httpResponseString != null && !httpResponseString.trim().isEmpty()) {
            /* 436 */
            Pattern pattern = Pattern.compile("(HttpResponseHeaders\\[(.*)\\](?=HttpResponseStatusLine)|HttpResponseHeaders\\[(.*)\\](?=HttpResponseBody)|HttpResponseHeaders\\[(.*)\\])");
            /* 437 */
            Matcher matcher = pattern.matcher(httpResponseString);
            /* 438 */
            while (matcher.find()) {
                /* 439 */
                headersString = matcher.group(1);
            }
            /* 441 */
            if (headersString != null && !headersString.trim().isEmpty()) {
                /* 442 */
                Matcher matcher2 = pattern.matcher(headersString);
                /* 443 */
                while (matcher2.find()) {
                    /* 444 */
                    headersString = matcher2.group(4);
                }
            }
            /* 447 */
            LOGGER.debug("Matched: {}", headersString);
            /* 448 */
            getHttpHeaderElementFromHttpHeaderString(headers, headersString);
        }
        /* 450 */
        return headers;
    }


    private static String getHttpResponseHeaderStringFromHttpResponseString(boolean getHttpResponseHeaderFromHttpResponseStringFlag, CloseableHttpResponse closeableHttpResponse, String httpResponseHeadersAsString) {
        /* 456 */
        LOGGER.debug("Executing getHttpResponseHeaderStringFromHttpResponseString");
        /* 457 */
        if (getHttpResponseHeaderFromHttpResponseStringFlag) {
            /* 458 */
            Header[] headers = closeableHttpResponse.getAllHeaders();
            /* 459 */
            httpResponseHeadersAsString = httpResponseHeadersAsString + "HttpResponseHeaders[";
            /* 460 */
            StringBuilder stringBuilder = new StringBuilder(httpResponseHeadersAsString);
            /* 461 */
            for (Header header : headers) {
                /* 462 */
                stringBuilder.append(header.getName() + ":" + header.getValue() + ":::");
            }
            /* 464 */
            httpResponseHeadersAsString = stringBuilder.toString().replaceAll(":::$", "") + "]";

            /* 466 */
            LOGGER.debug("the response header: {}", httpResponseHeadersAsString);
        }
        /* 468 */
        return httpResponseHeadersAsString;
    }


    public static String getHttpResponseHeaderValueFromHeaderList(List<Header> httpResponseHeaders, String httpResponseHeaderName) {
        /* 473 */
        LOGGER.debug("Executing getHttpResponseHeaderValueFromHeaderList");
        /* 474 */
        String httpResponseHeaderValue = null;
        /* 475 */
        if (httpResponseHeaders != null && !httpResponseHeaders.isEmpty()) {
            /* 476 */
            for (Header header : httpResponseHeaders) {
                /* 477 */
                if (header.getName().equalsIgnoreCase(httpResponseHeaderName)) {
                    /* 478 */
                    httpResponseHeaderValue = header.getValue();
                    break;
                }
            }
        }
        /* 483 */
        LOGGER.debug("Found: {}", httpResponseHeaderValue);
        /* 484 */
        return httpResponseHeaderValue;
    }

    public static String getHttpResponseReasonPhraseFromStatusLine(StatusLine statusLine) {
        /* 488 */
        LOGGER.debug("Executing getHttpResponseReasonPhraseFromStatusLine");
        /* 489 */
        String reasonPhrase = null;
        /* 490 */
        if (statusLine != null) {
            /* 491 */
            reasonPhrase = String.valueOf(statusLine.getReasonPhrase());
        }
        /* 493 */
        return reasonPhrase;
    }

    public static String getHttpResponseStatusCodeFromStatusLine(StatusLine statusLine) {
        /* 497 */
        LOGGER.debug("Executing getHttpResponseStatusCodeFromStatusLine");
        /* 498 */
        String statusCode = null;
        /* 499 */
        if (statusLine != null) {
            /* 500 */
            statusCode = String.valueOf(statusLine.getStatusCode());
        }
        /* 502 */
        return statusCode;
    }

    public static StatusLine getHttpResponseStatusLineFromHttpResponseString(String httpResponseString) {
        /* 506 */
        LOGGER.debug("Executing getHttpResponseStatusLineFromHttpResponseString");
        /* 507 */
        StatusLine statusLine = null;
        /* 508 */
        String statusLineString = null;
        /* 509 */
        if (httpResponseString != null && !httpResponseString.trim().isEmpty()) {
            /* 510 */
            Pattern pattern = Pattern.compile("(HttpResponseStatusLine\\[(.*)\\](?=HttpResponseHeaders)|HttpResponseStatusLine\\[(.*)\\](?=HttpResponseBody)|HttpResponseStatusLine\\[(.*)\\])");
            /* 511 */
            Matcher matcher = pattern.matcher(httpResponseString);
            /* 512 */
            while (matcher.find()) {
                /* 513 */
                statusLineString = matcher.group(1);
            }
            /* 515 */
            if (statusLineString != null && !statusLineString.trim().isEmpty()) {
                Matcher matcher2 = pattern.matcher(statusLineString);
                while (matcher2.find()) {
                    statusLineString = matcher2.group(4);
                }
            }
            LOGGER.debug("Matched: {}", statusLineString);
            statusLine = createHttpResponseStatusLineFromString(statusLine, statusLineString);
        }
        return statusLine;
    }


    private static String getHttpResponseStatusLineStringFromHttpResponseString(boolean getHttpResponseStatusLineFromHttpResponseStringFlag, CloseableHttpResponse closeableHttpResponse, String httpResponseStatusLineAsString) {
        LOGGER.debug("Executing getHttpResponseStatusLineStringFromHttpResponseString");
        if (getHttpResponseStatusLineFromHttpResponseStringFlag) {
            httpResponseStatusLineAsString = httpResponseStatusLineAsString + "HttpResponseStatusLine[" + closeableHttpResponse.getStatusLine().getProtocolVersion() + ":::" + closeableHttpResponse.getStatusLine().getStatusCode() + ":::" + closeableHttpResponse.getStatusLine().getReasonPhrase() + "]";
            LOGGER.debug("the response status line: {}", httpResponseStatusLineAsString);
        }
        return httpResponseStatusLineAsString;
    }


    private static String getResponseBodyFromHttpResponseString(boolean getHttpResponseStatusLineFromHttpResponseStringFlag, boolean getHttpResponseHeaderFromHttpResponseStringFlag, CloseableHttpResponse closeableHttpResponse, String httpResponseBody) throws IOException {
        LOGGER.debug("Executing getResponseBodyFromHttpResponseString");
        HttpEntity entity = closeableHttpResponse.getEntity();
        if (entity != null) {
            String responseBody = EntityUtils.toString(closeableHttpResponse.getEntity());
            EntityUtils.consume(entity);
            responseBody = responseBody.replaceAll("\\n", "");
            responseBody = responseBody.replaceAll("\\r", "");
            if (!getHttpResponseHeaderFromHttpResponseStringFlag && !getHttpResponseStatusLineFromHttpResponseStringFlag) {
                httpResponseBody = responseBody;
            } else {
                httpResponseBody = httpResponseBody + "HttpResponseBody[" + responseBody + "]";
            }
            LOGGER.debug("the response body: {}", httpResponseBody);
        }
        return httpResponseBody;
    }


    public static String sendHttpRequest(boolean useProxy, String proxyHost, int proxyPort, boolean isSecuredProtocol, String[] supportedProtocols, boolean proxyAuthenticationFlag, String proxyUsername, String proxyPassword, boolean clientAuthenticationIsRequired, String destinationServerUsername, String destinationServerPassword, String destinationServerAddress, int destinationServerPort, String httpMethod, Map<String, Object> headersMap, String url, String encoding, String httpRequestMessage, boolean getHttpResponseStatusLineFromHttpResponseStringFlag, int socketTimeout, int connectionTimeout, int connectionRequestTimeout, boolean getHttpResponseHeaderFromHttpResponseStringFlag) {
        LOGGER.debug("Executing sendHttpRequest");
        System.out.println();
        System.out.println();
        System.out.println("ABOUT TO SEND HTTP");
        String httpResponseMessage = "";
        CloseableHttpClient httpClient = null;
        CredentialsProvider credentialsProvider = null;
        RequestConfig requestConfig = null;
        CloseableHttpResponse closeableHttpResponse = null;
        String httpResponseStatusLineAsString = "";
        String httpResponseHeadersAsString = "";
        String httpResponseBody = "";
        HttpRequestBase httpObject = null;

        try {
            requestConfig = createRequestConfig(useProxy, proxyHost, proxyPort, socketTimeout, connectionTimeout);
            httpClient = createCloseableHttpClient(isSecuredProtocol, supportedProtocols, useProxy, proxyAuthenticationFlag, credentialsProvider, proxyHost, proxyPort, proxyUsername, proxyPassword, clientAuthenticationIsRequired, destinationServerAddress, destinationServerPort, destinationServerUsername, destinationServerPassword, requestConfig);
            httpObject = createHttpRequestObject(httpMethod, headersMap, url, encoding, httpRequestMessage);
            LOGGER.debug("about to send the http request...");
            closeableHttpResponse = httpClient.execute((HttpUriRequest) httpObject);
            httpResponseStatusLineAsString = getHttpResponseStatusLineStringFromHttpResponseString(getHttpResponseStatusLineFromHttpResponseStringFlag, closeableHttpResponse, httpResponseStatusLineAsString);
            httpResponseHeadersAsString = getHttpResponseHeaderStringFromHttpResponseString(getHttpResponseHeaderFromHttpResponseStringFlag, closeableHttpResponse, httpResponseHeadersAsString);
            httpResponseBody = getResponseBodyFromHttpResponseString(getHttpResponseStatusLineFromHttpResponseStringFlag, getHttpResponseHeaderFromHttpResponseStringFlag, closeableHttpResponse, httpResponseBody);
            System.out.println("BABABABABABAB" + httpResponseBody.toString());
        }catch (Exception e) {
            System.out.println("HEHEHEHEHEHEH ERROR OHH");
            LOGGER.error("An error occured: {}", e);
            httpResponseBody = e.getMessage();
        } finally {
            try {
                if (closeableHttpResponse != null) {
                    closeableHttpResponse.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (Exception e) {
                LOGGER.error("An error occured: {}", e);
            }
        }
        httpResponseMessage = httpResponseStatusLineAsString + httpResponseHeadersAsString + httpResponseBody;

        return httpResponseMessage;
    }
}
