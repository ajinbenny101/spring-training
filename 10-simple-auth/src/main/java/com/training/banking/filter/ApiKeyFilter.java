package com.training.banking.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// TODO 1: Add the @Component annotation to this class.
//         This tells Spring to register this class as a filter automatically.
//         Without it, Spring won't know this filter exists and requests will go straight
//         to the controller with no authentication check.
//         Hint: import org.springframework.stereotype.Component

// TODO 2: Make this class extend OncePerRequestFilter.
//         OncePerRequestFilter guarantees your filter logic runs exactly once per request.
//         Right now the class doesn't extend anything - add "extends OncePerRequestFilter" to the class declaration.
public class ApiKeyFilter {

    // TODO 3: Define the valid API key as a private static final constant.
    //         For this lab, we hardcode it. In a real application, this would come from
    //         a configuration file or a secrets manager.
    //
    //         private static final String API_KEY = "secret-key-123";

    // TODO 4: Override the doFilterInternal method from OncePerRequestFilter.
    //         This is the method that runs for every incoming request.
    //         The method signature is:
    //
    //         @Override
    //         protected void doFilterInternal(HttpServletRequest request,
    //                                         HttpServletResponse response,
    //                                         FilterChain filterChain)
    //                 throws ServletException, IOException {
    //
    //             // Your filtering logic goes here (TODOs 5-9)
    //
    //         }

        // TODO 5: Get the request path.
        //         Use request.getRequestURI() to find out which URL the client is trying to reach.
        //         Store it in a String variable called "path".

        // TODO 6: Check if the path starts with "/api/public".
        //         If it does, this is a public endpoint - no authentication needed.
        //         Call filterChain.doFilter(request, response) to let the request through,
        //         then return immediately (so the rest of the filter logic doesn't run).
        //
        //         if (path.startsWith("/api/public")) {
        //             filterChain.doFilter(request, response);
        //             return;
        //         }

        // TODO 7: If the path starts with "/api/admin", get the API key from the request header.
        //         The client sends the key in a header called "X-API-KEY".
        //         Use request.getHeader("X-API-KEY") and store the result in a String variable.

        // TODO 8: Check if the API key is missing or wrong.
        //         If the header value is null OR it does not equal API_KEY, reject the request:
        //           - Set the response status to 401: response.setStatus(HttpServletResponse.SC_UNAUTHORIZED)
        //           - Set the content type: response.setContentType("application/json")
        //           - Write the error JSON to the response:
        //             response.getWriter().write("{\"error\": \"UNAUTHORIZED\", \"message\": \"Missing or invalid API key\"}")
        //           - Return immediately (do NOT call filterChain.doFilter)
        //
        //         Hint: Use !API_KEY.equals(apiKey) instead of apiKey.equals(API_KEY).
        //         This avoids a NullPointerException when apiKey is null.

        // TODO 9: If the API key is valid, let the request through.
        //         Call filterChain.doFilter(request, response) to pass the request
        //         to the next filter in the chain (or to the controller if there are no more filters).

}
