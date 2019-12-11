'use strict';

document.addEventListener('DOMContentLoaded', function () {
    const submit = document.querySelector("#submit.button");

    submit.onclick = async (e) => {
        e.preventDefault();

        const data = formToJSON(document.querySelector("form"));

        const response = await postData('./post', {data});

        if(response.errorType) {

        } else {

        }
        console.log(response);
    }

});