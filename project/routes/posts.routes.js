const { Router } = require("express");
const _ = require("lodash");

const Post = require("../model/posts.model");
const PostController = require("../controller/post.controller");

const authenticated = require("../middleware/auth.middleware");
const { fullUrl } = require("../util/url");

const router = Router();

router

    //Forum page
    .get("/forumpage", authenticated, (req, res, next) => {
        PostController.findAllPost()
            .then((posts) => {

                res.render('forumpage', {
                    posts
                })
            })
            .catch((error) => {
                console.log(error);
                res.render('forumpage', {
                    error: req.flash("error", error)
                })
            })
    })

    //Creating posts
    .get("/createpost", authenticated, (req, res, next) => {
        res.render('createthread');
    })

    .post("/createpost", authenticated, (req, res, next) => {
        const {title, body} = _.pick(req.body, ["title", "body"]);

        PostController.createPost(title, body, req.user)
            .then((result) => {
                console.log(result);
                return res.redirect(fullUrl(req));
            })
            .catch((error) => {
                console.log(error);
                return res.redirect(fullUrl(req));
            })
    })


module.exports = router;