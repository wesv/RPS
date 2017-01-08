/*
    Author: Kayler Renslow (made changes), jlongster (wrote the majority of the code.) [https://github.com/jlongster/]

*/

(function () {
    var resourceCache = {};
    var loading = [];
    var readyCallbacks = [];

    // Load an image url or an array of image urls
    function load(urlOrArr) {
        if (urlOrArr instanceof Array) {
            urlOrArr.forEach(function (image) {
                _load(image);
            });
        }
        else {
            _load(urlOrArr);
        }
    }

    function _load(image) {
        if (resourceCache[image.imgURL]) {
            return resourceCache[image.imgURL];
        }
        else {
            var img = new Image();
            img.onload = function () {
                resourceCache[image.imgURL] = img;

                if (isReady()) {
                    readyCallbacks.forEach(function (func) { func(); });
                }
            };
            resourceCache[image.imgURL] = false;
            img.src = image.imgURL;
        }
    }

    function get(image) {
        return resourceCache[image.imgURL];
    }

    function isReady() {
        var ready = true;
        for (var k in resourceCache) {
            if (resourceCache.hasOwnProperty(k) &&
               !resourceCache[k]) {
                ready = false;
            }
        }
        return ready;
    }

    function onReady(func) {
        readyCallbacks.push(func);
    }

    window.resources = {
        load: load,
        get: get,
        onReady: onReady,
        isReady: isReady
    };
})();