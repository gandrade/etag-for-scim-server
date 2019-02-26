package org.springframework.web.filter;

import org.springframework.http.HttpMethod;
import org.springframework.util.ClassUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

public class CustomShallowEtagHeaderFilter extends ShallowEtagHeaderFilter {


    private static final String HEADER_CACHE_CONTROL = "Cache-Control";

    private static final String DIRECTIVE_NO_STORE = "no-store";

    /**
     * Checking for Servlet 3.0+ HttpServletResponse.getHeader(String)
     */
    private static final boolean servlet3Present =
            ClassUtils.hasMethod(HttpServletResponse.class, "getHeader", String.class);

    /**
     * Indicates whether the given request and response are eligible for ETag generation.
     * <p>The default implementation returns {@code true} if all conditions match:
     * <ul>
     * <li>response status codes in the {@code 2xx} series</li>
     * <li>response Cache-Control header is not set or does not contain a "no-store" directive</li>
     * </ul>
     *
     * @param request            the HTTP request
     * @param response           the HTTP response
     * @param responseStatusCode the HTTP response status code
     * @param inputStream        the response body
     * @return {@code true} if eligible for ETag generation, {@code false} otherwise
     */
    protected boolean isEligibleForEtag(HttpServletRequest request, HttpServletResponse response,
                                        int responseStatusCode, InputStream inputStream) {

        if (responseStatusCode >= 200 && responseStatusCode < 300) {
            String cacheControl = null;
            if (servlet3Present) {
                cacheControl = response.getHeader(HEADER_CACHE_CONTROL);
            }
            if (cacheControl == null || !cacheControl.contains(DIRECTIVE_NO_STORE)) {
                return true;
            }
        }
        return false;
    }
}
