const { Router } = require("express");
const _ = require("lodash");
const { check, validationResult } = require('express-validator/check');

const router = Router();

const { register } = require('../controller/user.controller')

router

    //Login routes
    .get("/login", (req, res, next) => {
        res.render('login');
    })
    .post("/login", (req, res, next) => {

    })

    //Register routes
    .get("/register", (req, res, next) => {
        res.render('register');
    })
    .post("/register", 
    
    [
        check("username", "Username has to be 3+ characters long")
            .exists()
            .isLength({min: 3}),
        check("email", "Email is not valid")
            .isEmail()
            .normalizeEmail(),
        check("password", "Password must be entered")
            .exists(),
        check("confirmpassword", "Password does not match")
            .trim()
            .exists()
            .custom(async (confirmPassword, {req}) => {
                const password = req.body.password;

                if (password !== confirmPassword) {
                    throw new Error("Password must be same.");
                }
            })
    ],
    
    
    (req, res, next) => {
        const {username, password, confirmpassword, email} = _.pick(req.body, ["username", "password", "confirmpassword", "email"]);

        
        const errors = validationResult(req);
        if (errors) {
            return res.json(errors);
            //return res.render("register", {errors});
        }


        register(username, password, confirmpassword, email)
            .then((user) => {
                return res.json(user);
            })
            .catch((error) => {
                return res.json(error);
                //res.render("register", {
                //    validation: error
                //});
            })
    })

module.exports = router;

