<%-- 
    Document   : JSPPagina
    Created on : 15-Mar-2016, 13:12:29
    Author     : Edwin
--%>

<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            Map<String,String> map = (HashMap)request.getAttribute("map");
            for (Map.Entry<String, String> entry : map.entrySet())
            {
                out.println("<h1>" + entry.getKey() + "</h1>");
            }
        %> 
    </body>
</html>
