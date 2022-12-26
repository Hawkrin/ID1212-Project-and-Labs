const { Router } = require("express");
const _ = require("lodash");
const { fullUrl } = require("../util/url");
const { check, validationResult } = require('express-validator/check');
const { formErrorFormater } = require("../util/errorFormater");

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
        if (errors) {
            req.flash("form-error", formErrorFormater(errors));
            return res.redirect(fullUrl(req));
        }
        
        register(username, password, confirmpassword, email)
            .then((user) => {
                return res.json(user);
            })
            .catch((error) => {
                req.flash("error", error);
                return res.redirect(fullUrl(req));
            })
    })

module.exports = router;

