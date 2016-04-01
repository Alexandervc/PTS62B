/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Edwin
 */
@WebServlet(name="ControllerServlet", loadOnStartup = 1, urlPatterns={"/admin", "/home", "/CreatePost","","/test","/post","/addComment","/getComments","/changeAdminMode","/getPosts","/deletePost","/editPost"})
public class ControllerServlet extends HttpServlet {

    ServletContext context;
    @Override
    public void init(ServletConfig config){
        this.context = config.getServletContext();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "/JSPPagina.jsp";
        Map map = new HashMap();
        map.put("VampAnder", "VampAnder");
        request.setAttribute("map", map);
        this.context.getRequestDispatcher(url).forward(request, response);
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }   
}
