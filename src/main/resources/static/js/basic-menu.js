let userContext = null;

$(function () {
    "use strict";
    var url = window.location + "";
    var path = url.replace(
        window.location.protocol + "//" + window.location.host + "/",
        ""
    );
    var element = $("ul#sidebarnav a").filter(function () {
        return this.href === url || this.href === path; // || url.href.indexOf(this.href) === 0;
    });
    element.parentsUntil(".sidebar-nav").each(function (index) {
        if ($(this).is("li") && $(this).children("a").length !== 0) {
            $(this).children("a").addClass("active");
            $(this).parent("ul#sidebarnav").length === 0
                ? $(this).addClass("active")
                : $(this).addClass("selected");
        } else if (!$(this).is("ul") && $(this).children("a").length === 0) {
            $(this).addClass("selected");
        } else if ($(this).is("ul")) {
            $(this).addClass("in");
        }
    });

    element.addClass("active");
    $("#sidebarnav a").on("click", function (e) {
        if (!$(this).hasClass("active")) {
            // hide any open menus and remove all other classes
            $("ul", $(this).parents("ul:first")).removeClass("in");
            $("a", $(this).parents("ul:first")).removeClass("active");

            // open our new menu and add the open class
            $(this).next("ul").addClass("in");
            $(this).addClass("active");
        } else if ($(this).hasClass("active")) {
            $(this).removeClass("active");
            $(this).parents("ul:first").removeClass("active");
            $(this).next("ul").removeClass("in");
        }
    });
    $("#sidebarnav >li >a.has-arrow").on("click", function (e) {
        e.preventDefault();
    });
});

document.addEventListener("DOMContentLoaded", async () => {
    await includeBasicPanel(); // HTML 및 사용자 정보 로드
});

async function includeBasicPanel() {
    try {
        await loadHtml();
        setUserInfo();
    } catch (error) {
        console.error("Error loading HTML or fetching user info:", error);
    }
}

function loadHtml() {
    return new Promise((resolve, reject) => {
        let z = document.getElementsByTagName("*");
        let pendingRequests = 0;

        for (let i = 0; i < z.length; i++) {
            let elmnt = z[i];
            let file = elmnt.getAttribute("include-html");
            if (file) {
                pendingRequests++;
                let xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (this.readyState === 4) {
                        if (this.status === 200) {
                            elmnt.innerHTML = this.responseText;
                        } else if (this.status === 404) {
                            elmnt.innerHTML = "Page not found.";
                        }
                        elmnt.removeAttribute("include-html");
                        if (--pendingRequests === 0) resolve();
                    }
                };
                xhttp.open("GET", file, true);
                xhttp.send();
            }
        }

        if (pendingRequests === 0) resolve();
    });
}

function setUserInfo() {
    fetch("/me", {
        method: "GET"
    }).then(response => {
        return response.json();
    }).then(data => {
        userContext = data;
        const userDep = document.getElementById("user-department");
        const userName = document.getElementById("user-name");

        if (userName && userDep && userContext) {
            userDep.textContent = " [" + userContext.department + "] ";
            userName.textContent = userContext.nickname;
        }
    }).catch(error => {
        console.error("Error fetching user info:", error);
    });
}