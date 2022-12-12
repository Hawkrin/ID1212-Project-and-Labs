/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
/**
 *
 * @author Harry
 */
public class GuessingServlet extends HttpServlet {

   @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        //Getting session
        HttpSession session = req.getSession();

        //Checking if user has logged in.
        Integer loginState = (Integer) session.getAttribute("loginState");
        if (loginState == null) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        //User is logged in.
        try {
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)envContext.lookup("jdbc/derby");
            
            Connection conn = ds.getConnection();
            Statement stmt = conn.createStatement();
            
            //Index is the ID and value is the quiz subject.
            ArrayList<String> quizzes = new ArrayList<String>();
            ArrayList<Integer> points = new ArrayList<Integer>();
            
            ResultSet rs = stmt.executeQuery("SELECT * FROM quizzes");
            while (rs.next()) {
                quizzes.add(rs.getString("subject"));
            }
            
            req.setAttribute("quizzes", quizzes);
            req.getRequestDispatcher("/chooseQuiz.jsp").forward(req, res);
            
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
