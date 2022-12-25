function formErrorFormater(error) {
    const result = {};

    error.errors.forEach(element => {
        result[element.param] = element.msg;
    });

    return result;
}

module.exports = {
    formErrorFormater
}