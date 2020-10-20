import http from 'http';
import httpStatus from 'http-status-codes';
import fs from "fs";

/* Better ways of routing: 
    1.Registering callbacks instead of the html
    2. Decorators??? @RequestHandle("/test", CallType.GET) --> would handle a request to the url test if it was of type GET 

*/
const routeResponseMap: { [key: string]: string } = {
    "/test": "<h1>This is a test</h1>",
    "/hello": "<h1>Helloooo</h1>"
}


const staticFiles: { [key: string]: { mime: string, dir: string } } = {
    ".html": { mime: "text/html", dir: "public/html" },
    ".js": { mime: "text/javascript", dir: "public/js" },
    ".css": { mime: "text/css", dir: "public/css" },
    ".png": { mime: "image/png", dir: "public/image" },
    ".jpg": { mime: "image/jpg", dir: "public/image" },
    ".gif": { mime: "image/gif", dir: "public/image" }
}
const port = 3000;
const app = http.createServer((request, response) => {
    console.log('Receiving a request!');
    const url = request.url ?? '/test';
    const extension = Object.keys(staticFiles).find(key => url.indexOf(key) !== -1);
    if (!!extension) {
        const fileInfo = staticFiles[extension];
        response.writeHead(httpStatus.OK, { "Content-Type": fileInfo.mime });
        customReadFile(fileInfo.dir + url, response);
    }
    else {
        response.writeHead(httpStatus.OK, {
            'Content-Type': 'text/html'
        });
        if (routeResponseMap[url]) {
            response.end(routeResponseMap[url])
        }
        else {
            response.end("<h1>Welcome</h1>")
        }
    }
});
app.listen(port);
console.log(`The server has started and is listening on port number: ${port}`);

const sendErrorResponse = (res: http.ServerResponse) => {
    1
    res.writeHead(httpStatus.NOT_FOUND, {
        "Content-Type": "text/html"
    });
    res.write("<h1>File Not Found!</h1>");
    res.end();
};


const customReadFile = (file_path: string, res: http.ServerResponse) => {

    if (fs.existsSync(file_path)) {

        fs.readFile(file_path, (error, data) => {
            if (error) {
                console.log(error);
                sendErrorResponse(res);
                return;
            }
            res.write(data);
            res.end();
        });
    } else {
        sendErrorResponse(res);
    }
};
