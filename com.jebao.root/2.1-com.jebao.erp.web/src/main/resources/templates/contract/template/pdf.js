var system = require('system');
var address = system.args[1];
var output = system.args[2];
var page = require('webpage').create();
page.paperSize = {
  format: 'A4',
  orientation: 'portrait',
   border: '1cm'
};
// debug begin
// console.log(output);
// console.log(address);
// console.log(status);
// debug end
page.settings.resourceTimeout = 2000; 
page.open(address, function (status) {
    if (status !== 'success') {
        console.log('[ERROR]');
        phantom.exit();
    } else {
	console.log("[OK]");
        window.setTimeout(function () {
            // Remove all low-opacity paths. see PhantomJS  issue #364 
            page.evaluate(function () {
                var paths = document.getElementsByTagName("path");
                for (var i = paths.length - 1; i >= 0; i--) {
                    var path = paths[i];
                    var strokeOpacity = path.getAttribute('stroke-opacity');
                    if (strokeOpacity != null && strokeOpacity < 0.2)
                        path.parentNode.removeChild(path);
                }
            });
            page.render(output);
            phantom.exit();
        }, 100);
    }
});