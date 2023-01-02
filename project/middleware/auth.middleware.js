const jwt = require("jsonwebtoken");

const authenticated = function(req, res, next) {
    const token = req.cookies.Authenticate;

    if (token == null) {
        return res.redirect("/auth/login");
    }

    jwt.verify(token, process.env.JWT_TOKEN, (err, _id) => {
        if (err) {
            console.log(err);
            return res.redirect("/auth/login");
        }

        req.user = _id;
        next();
    })
}

module.exports = authenticated;