const { Router } = require("express");
const _ = require("lodash");
const { fullUrl } = require("../util/url");
const { check, validationResult } = require('express-validator/check');
const { formErrorFormater } = require("../util/errorFormater");
const jwt = require("jsonwebtoken")

const router = Router();


const { register, login } = require('../controller/user.controller')

router

    //Login routes
    .get("/login", (req, res, next) => {

        res.render('login', {
            error: req.flash("error"), 
            form_error: req.flash("form-error")
        });
    })
    .post("/login", 
    
    [
        check("email", "Doesn't recognize this email")
            .isEmail()
            .normalizeEmail(),
        check("password", "Password must be entered")
            .exists()
    ],

    (req, res) => {
        const {email, password} = _.pick(req.body, ["password", "email"]);

        //Form errors.
        const errors = validationResult(req);
        if (errors.errors.length > 0) {
            req.flash("form-error", formErrorFormater(errors));
            return res.redirect(fullUrl(req));
        }

        login(email, password)
            .then((user) => {
                const token = jwt.sign(user._id.toString(), process.env.JWT_TOKEN);
                return res.cookie("Authenticate", token).redirect("/");
                
            })
            .catch((error) => {
                req.flash("error", error);
                return res.redirect(fullUrl(req));
            })

    })

    //Register routes
    .get("/register", (req, res) => {
        return res.render('register', {
            error: req.flash("error"), 
            form_error: req.flash("form-error")
        });
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
    
    
    (req, res) => {
        const {username, password, confirmpassword, email} = _.pick(req.body, ["username", "password", "confirmpassword", "email"]);


        //Form errors.
        const errors = validationResult(req);
        if (errors.errors.length > 0) {
            req.flash("form-error", formErrorFormater(errors));
            return res.redirect(fullUrl(req));
        }

        
        register(username, password, confirmpassword, email)
            .then((user) => {
                const token = jwt.sign(user._id.toString(), process.env.JWT_TOKEN);
                return res.cookie("Authenticate", token).redirect("/");
            })
            .catch((error) => {
                req.flash("error", error);
                return res.redirect(fullUrl(req));
            })
    })

module.exports = router;

