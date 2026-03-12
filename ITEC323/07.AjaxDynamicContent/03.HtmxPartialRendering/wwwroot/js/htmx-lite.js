/*
 * Minimal offline teaching helper for the core HTMX attributes used in this sample.
 * Supports: hx-get, hx-post, hx-target, hx-swap, hx-trigger, and hx-indicator.
 */
(function () {
    "use strict";

    function getRequestElements() {
        return Array.from(document.querySelectorAll("[hx-get], [hx-post]"));
    }

    function getTarget(element) {
        const selector = element.getAttribute("hx-target");

        if (!selector || selector === "this") {
            return element;
        }

        return document.querySelector(selector);
    }

    function getIndicator(element) {
        const selector = element.getAttribute("hx-indicator");

        if (selector) {
            return document.querySelector(selector);
        }

        return element.querySelector(".htmx-indicator");
    }

    function setLoadingState(element, indicator, isLoading) {
        element.classList.toggle("htmx-request", isLoading);

        if (indicator) {
            indicator.classList.toggle("htmx-request", isLoading);
        }

        if (element instanceof HTMLButtonElement) {
            element.disabled = isLoading;
        }

        if (element instanceof HTMLFormElement) {
            const submitButton = element.querySelector("[type='submit']");
            if (submitButton) {
                submitButton.disabled = isLoading;
            }
        }
    }

    async function performRequest(element) {
        const url = element.getAttribute("hx-get") || element.getAttribute("hx-post");
        const method = element.hasAttribute("hx-post") ? "POST" : "GET";
        const target = getTarget(element);
        const indicator = getIndicator(element);
        const swap = element.getAttribute("hx-swap") || "innerHTML";

        if (!url || !target) {
            return;
        }

        setLoadingState(element, indicator, true);

        try {
            const options = { method: method };

            if (method === "POST" && element instanceof HTMLFormElement) {
                options.body = new FormData(element);
            }

            const response = await fetch(url, options);
            const html = await response.text();

            if (!response.ok) {
                throw new Error("The fragment request failed.");
            }

            swapContent(target, html, swap);
        } catch (error) {
            target.innerHTML = `<div class="fragment-card error-card"><div class="fragment-meta">Request Error</div><p class="mb-0">${error.message}</p></div>`;
        } finally {
            setLoadingState(element, indicator, false);
        }
    }

    function swapContent(target, html, swap) {
        if (swap === "outerHTML") {
            target.outerHTML = html;
            return;
        }

        if (swap === "beforeend") {
            target.insertAdjacentHTML("beforeend", html);
            return;
        }

        if (swap === "afterbegin") {
            const emptyState = target.querySelector(".empty-state");
            if (emptyState && target.children.length === 1) {
                target.innerHTML = "";
            }

            const stackList = target.querySelector(".stack-list");
            if (stackList) {
                stackList.insertAdjacentHTML("afterbegin", html);
                return;
            }

            target.insertAdjacentHTML("afterbegin", html);
            return;
        }

        target.innerHTML = html;
    }

    function bindElement(element) {
        const trigger = element.getAttribute("hx-trigger");

        if (!trigger) {
            bindDefaultTrigger(element);
            return;
        }

        if (trigger === "load") {
            window.addEventListener("load", function () {
                performRequest(element);
            }, { once: true });
            return;
        }

        if (trigger.startsWith("every ")) {
            const parts = trigger.split(" ");
            const secondsText = parts[1].replace("s", "");
            const seconds = Number(secondsText);

            if (!Number.isNaN(seconds) && seconds > 0) {
                window.setInterval(function () {
                    performRequest(element);
                }, seconds * 1000);
            }

            return;
        }

        element.addEventListener(trigger, function (event) {
            event.preventDefault();
            performRequest(element);
        });
    }

    function bindDefaultTrigger(element) {
        if (element instanceof HTMLFormElement) {
            element.addEventListener("submit", function (event) {
                event.preventDefault();
                performRequest(element);
            });

            return;
        }

        element.addEventListener("click", function (event) {
            event.preventDefault();
            performRequest(element);
        });
    }

    document.addEventListener("DOMContentLoaded", function () {
        getRequestElements().forEach(bindElement);
    });
}());
