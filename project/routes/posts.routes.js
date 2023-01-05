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
        const {title, description, body} = _.pick(req.body, ["title", "description", "body"]);

        PostController.createPost(title, description, body, req.user)
            .then((result) => {
                console.log(result);
                return res.redirect(fullUrl(req));
            })
            .catch((error) => {
                console.log(error);
                return res.redirect(fullUrl(req));
            })
    })

    .get("/post/:id", authenticated, (req, res) => {
        const {id} = _.pick(req.params, ["id"]);

        PostController.findPost(id)
            .then((post) => {
                return res.render("summary", {post}); //Ändra url
            })
            .catch((error) => {
                return res.render("forumpage", {error}); //Låt vara
            })
    })


module.exports = router;