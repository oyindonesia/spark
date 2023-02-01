package spark.utils;

import org.junit.Before;
import org.junit.Test;
import spark.Request;
import spark.RequestResponseFactory;
import spark.routematch.RouteMatch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SparkUtilsTest {

    HttpServletRequest servletRequest;
    HttpSession httpSession;
    Request request;

    @Before
    public void setup() {

        servletRequest = mock(HttpServletRequest.class);
        httpSession = mock(HttpSession.class);

    }

    @Test
    public void testConvertRouteToList() throws Exception {

        List<String> expected = Arrays.asList("api", "person", ":id");

        List<String> actual = SparkUtils.convertRouteToList("/api/person/:id");

        assertThat("Should return route as a list of individual elements that path is made of",
                actual,
                is(expected));

    }

    @Test
    public void testIsParam_whenParameterFormattedAsParm() throws Exception {

        assertTrue("Should return true because parameter follows convention of a parameter (:paramname)",
                SparkUtils.isParam(":param"));

    }

    @Test
    public void testIsParam_whenParameterNotFormattedAsParm() throws Exception {

        assertFalse("Should return false because parameter does not follows convention of a parameter (:paramname)",
                SparkUtils.isParam(".param"));

    }


    @Test
    public void testIsSplat_whenParameterIsASplat() throws Exception {

        assertTrue("Should return true because parameter is a splat (*)", SparkUtils.isSplat("*"));

    }

    @Test
    public void testIsSplat_whenParameterIsNotASplat() throws Exception {

        assertFalse("Should return true because parameter is not a splat (*)", SparkUtils.isSplat("!"));

    }

    @Test
    public void testGetPathName_ShouldBeProduceStringWithUnderscore() throws Exception {
        RouteMatch match = new RouteMatch(null, "/v1/user", "/v1/user", "text/html");
        request = RequestResponseFactory.create(match, servletRequest);
        when(servletRequest.getRequestURI()).thenReturn("/v1/user");

        assertEquals("_v1_user", SparkUtils.getPathName(request));

    }

    @Test
    public void testGetPathName_WithQueryParamName() throws Exception {
        RouteMatch match = new RouteMatch(null, "/v1/user/:id/balance", "/v1/user/123/balance", "text/html");
        request = RequestResponseFactory.create(match, servletRequest);
        when(servletRequest.getRequestURI()).thenReturn("/v1/user/123/balance");

        assertEquals("_v1_user_id_balance", SparkUtils.getPathName(request));

    }
}
