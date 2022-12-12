/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import extra.Questions;
import java.io.IOException;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;


/**
 *
 * @author Harry
 */
public class QuizServlet extends HttpServlet {

    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        int quizId = Integer.parseInt(req.getParameter("q"));
        
        HttpSession session = req.getSession();
        ArrayList<Questions> q = (ArrayList<Questions>) session.getAttribute("questions");
        int score = 0;
        
        for (int i = 0; i < q.size(); i++) {
            try {
                if (
                    (req.getParameter(i + ",0") == null && q.get(i).getAnswer().get(0).equals("0") || q.get(i).getAnswer().get(0).equals("1") && req.getParameter(i + ",0").equals("1")) &&
                    (req.getParameter(i + ",1") == null && q.get(i).getAnswer().get(1).equals("0") || q.get(i).getAnswer().get(1).equals("1") && req.getParameter(i + ",1").equals("1")) &&
                    (req.getParameter(i + ",2") == null && q.get(i).getAnswer().get(2).equals("0") || q.get(i).getAnswer().get(2).equals("1") && req.getParameter(i + ",2").equals("1"))
                   )
                {
                    score++;
                } 
            } catch (Exception e) {
                System.out.println("No score!");
            }
        }
        
        
         try {
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)envContext.lookup("jdbc/derby");
            Connection conn = ds.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM results WHERE user_id=" + session.getAttribute("loginState") + " AND quiz_id=" + quizId);
            boolean change = false;
            while (rs.next()) {
               if (score > rs.getInt("score")) {
                   change = !change;
               }
            }
            String sql = "UPDATE results SET score = ? WHERE user_id = ? AND quiz_id = ?";
            PreparedStatement update_statement = conn.prepareStatement(sql);
            
            if (true) {
                update_statement.setInt(1, score);
                update_statement.setInt(2,(int) session.getAttribute("loginState"));
                update_statement.setInt(3,(int) quizId);

                int r = update_statement.executeUpdate();
                //rs = stmt.executeQuery("UPDATE results SET score=" + score + " WHERE user_id=" + session.getAttribute("loginState") + " AND quiz_id=" + quizId);

                System.out.println(r);
            }
            
            res.sendRedirect(req.getContextPath() + "/guess");
         } catch (Exception e) {
            System.out.println(e.getMessage());
         }
         
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        //Get what quiz we want to do.
        int quizId = Integer.parseInt(req.getParameter("q"));
        HttpSession session = req.getSession();

        try {
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)envContext.lookup("jdbc/derby");
            
            Connection conn = ds.getConnection();
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery("SELECT * FROM selector WHERE quiz_id=" + quizId);
            ArrayList<Questions> questions = new ArrayList<Questions>();
            ArrayList<Integer> questions_id = new ArrayList<Integer>();
            while (rs.next()) {
                questions_id.add(rs.getInt("question_id"));
            }
            
            
            for (int i = 0; i < questions_id.size(); i++) {
                rs = stmt.executeQuery("SELECT * FROM questions WHERE id=" + questions_id.get(i));
                while (rs.next()) {
                    Questions q = new Questions(rs.getString("text"), rs.getString("options"), rs.getString("answer"));
                    questions.add(q);
                }
            }
            
           
            rs = stmt.executeQuery("SELECT * FROM results WHERE user_id=" + session.getAttribute("loginState") + " AND quiz_id=" + quizId);
            while (rs.next()) {
              
                req.setAttribute("points", rs.getInt("score"));
            }
            
            session.setAttribute("questions", questions);
            
            req.setAttribute("questions", questions);
            req.getRequestDispatcher("/quiz.jsp").forward(req, res);
            
        } catch (Exception e) {
            System.out.println(e);
            System.out.println(e.getMessage());
        }
    }
}
