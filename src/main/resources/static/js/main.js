'use strict';

// Functions
const getAll = (selector) => Array.prototype.slice.call(document.querySelectorAll(selector), 0);

document.addEventListener('DOMContentLoaded', function () {

    // Modals

    const rootEl = document.documentElement;
    const $modals = getAll('.modal');
    const $modalButtons = getAll('.modal-trigger');
    const $modalCloses = getAll('.modal-background, .modal-close, .modal-card-head .delete, .modal-card-foot .closer');

    if ($modalButtons.length > 0) {
        $modalButtons.forEach(function ($el) {
            $el.addEventListener('click', function () {
                const target = $el.dataset.target;
                const $target = document.getElementById(target);
                rootEl.classList.add('is-clipped');
                $target.classList.add('is-active');
            });
        });
    }

    if ($modalCloses.length > 0) {
        $modalCloses.forEach(function ($el) {
            $el.addEventListener('click', function () {
                closeModals();
            });
        });
    }

    document.addEventListener('keydown', function (event) {
        const e = event || window.event;
        if (e.keyCode === 27) {
            closeModals();
        }
    });

    window.closeModals = function closeModals() {
        rootEl.classList.remove('is-clipped');
        $modals.forEach(function ($el) {
            $el.classList.remove('is-active');
        });
    }

    // Functions

});