"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const http_1 = __importDefault(require("http"));
const http_status_codes_1 = __importDefault(require("http-status-codes"));
const fs_1 = __importDefault(require("fs"));
/* Better ways of routing:
    1.Registering callbacks instead of the html
    2. Decorators??? @RequestHandle("/test", CallType.GET) --> would handle a request to the url test if it was of type GET

*/
const routeResponseMap = {
    "/test": "<h1>This is a test</h1>",
    "/hello": "<h1>Helloooo</h1>"
};
const staticFiles = {
    ".html": { mime: "text/html", dir: "./public/html" },
    ".js": { mime: "text/javascript", dir: "./public/js" },
    ".css": { mime: "text/css", dir: "./public/css" },
    ".png": { mime: "image/png", dir: "./public/image" },
    ".jpg": { mime: "image/jpg", dir: "./public/image" },
    ".gif": { mime: "image/gif", dir: "./public/image" }
};
const port = 3000;
const app = http_1.default.createServer((request, response) => {
    var _a;
    console.log('Receiving a request!');
    const url = (_a = request.url) !== null && _a !== void 0 ? _a : '/test';
    const extension = Object.keys(staticFiles).find(key => url.indexOf(key) !== -1);
    if (!!extension) {
        const fileInfo = staticFiles[extension];
        response.writeHead(http_status_codes_1.default.OK, { "Content-Type": fileInfo.mime });
        customReadFile(fileInfo.dir + url, response);
    }
    else {
        response.writeHead(http_status_codes_1.default.OK, {
            'Content-Type': 'text/html'
        });
        if (routeResponseMap[url]) {
            response.end(routeResponseMap[url]);
        }
        else {
            response.end("<h1>Welcome</h1>");
        }
    }
});
app.listen(port);
console.log(`The server has started and is listening on port number: ${port}`);
const sendErrorResponse = (res) => {
    1;
    res.writeHead(http_status_codes_1.default.NOT_FOUND, {
        "Content-Type": "text/html"
    });
    res.write("<h1>File Not Found!</h1>");
    res.end();
};
const customReadFile = (file_path, res) => {
    if (fs_1.default.existsSync(file_path)) {
        fs_1.default.readFile(file_path, (error, data) => {
            if (error) {
                console.log(error);
                sendErrorResponse(res);
                return;
            }
            res.write(data);
            res.end();
        });
    }
    else {
        console.log(file_path);
        sendErrorResponse(res);
    }
};
//# sourceMappingURL=app.js.map