const { contextBridge, ipcRenderer} = require("electron");
const path = require("path");
const fs = require("fs");

let loadCallback = null;

let dataDir = null;
process.once("loaded",()=>{
    ipcRenderer.send("showDataDirDialog");
    ipcRenderer.on("dataDir",(event,args)=>{
        dataDir = args;
        if(callback){
            callback();
        }
    })
})

contextBridge.exposeInMainWorld("checkIfLoaded",()=>{
    if(dataDir!=null){
        return true;
    }else{
        return false;
    }
})

contextBridge.exposeInMainWorld("setDataDirLoaded",(callback)=>{
    if(typeof callback !== 'function'){
        return;
    }
    loadCallback = callback;
    if(dataDir!=null){
        loadCallback();
    }
})

contextBridge.exposeInMainWorld("getStaticParsed", function () {
    let data = fs.readFileSync(path.join(dataDir, "static.txt")).toString();
    data = data.split('\n');

    let numParts = parseFloat(data[0]);
    let long = parseFloat(data[1]);

    let parts = [];
    for (let i = 2; i - 2 < numParts; i++) {
        let partRadius = parseFloat(data[i]);
        parts.push({
            radius: partRadius
        });
    }

    return {
        numParts,
        long,
        particles: parts
    };
})

contextBridge.exposeInMainWorld("getOutputParsed", function () {
    let data = fs.readFileSync(path.join(dataDir, "output.txt")).toString();
    data = data.split("\n");

    let particles = [];
    for (const line of data) {
        let values = line.match(/\[.*\]/)[0];
        values = values.substring(1, values.length - 1);
        if (values.length > 0) {
            values = values.split(" ").map(parseFloat);
            particles.push({ id: values[0], neighbours: values.slice(1) })
        }
    }

    return particles;
})

contextBridge.exposeInMainWorld("getDynamicParsed", function () {
    let data = fs.readFileSync(path.join(dataDir, "0.txt")).toString();
    data = data.split("\n");

    let particles = [];
    for (const line of data) {
        let d = line.split(" ");
        particles.push({
            x: parseFloat(d[0]),
            y: parseFloat(d[1]),
            vx: parseFloat(d[2]),
            vy: parseFloat(d[3]),
        })
    }

    return particles;
})