package com.doncho.ppmtool.sequrity;

import com.doncho.ppmtool.exceptions.InvalidLoginResponse;
import com.google.gson.Gson;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class overrides commence. It will told whats happen when some user is not authorized to access resource
 * (some Http request)
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint
{
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException
    {
        InvalidLoginResponse loginResponse = new InvalidLoginResponse();
        String jsonLoginResponse = new Gson().toJson(loginResponse);

        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(401);
        httpServletResponse.getWriter().print(jsonLoginResponse);

    }
}
