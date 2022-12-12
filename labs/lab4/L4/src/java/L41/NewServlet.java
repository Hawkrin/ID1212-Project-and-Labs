/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package L41;


import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Harry
 */
@WebServlet(name="lab41", urlPatterns = {"/lab41"})
public class NewServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{
        
        //Getting the session
        HttpSession session = req.getSession();
        
        //Checking if the session has a game running.
        Model sessionGame = (Model) session.getAttribute("game");
        if (sessionGame == null) {
            sessionGame = new Model();
        }
        
        session.setAttribute("game", sessionGame);
        
        //Game logic... spokii
        if (req.getParameter("guess") != null) {
            Integer guess = Integer.parseInt(req.getParameter("guess"));
            int answer = sessionGame.isCorrect(guess);
            
            req.setAttribute("answer", answer);
            req.setAttribute("guesses", sessionGame.guesses);
            req.setAttribute("status", true);
        } else {
            req.setAttribute("status", false);
        }
        
        
        //Response
        try {
            req.getRequestDispatcher("/view.jsp").forward(req, res);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    
    
    
   

}