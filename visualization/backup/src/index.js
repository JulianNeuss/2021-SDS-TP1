const { app, BrowserWindow,ipcMain, dialog } = require('electron')
const path = require("path")

function createWindow () {
  const win = new BrowserWindow({
    width: 600,
    height: 600,
    webPreferences: {
      nodeIntegration: false, // is default value after Electron v5
      contextIsolation: true, // protect against prototype pollution
      enableRemoteModule: false, // turn off remote
      preload: path.join(__dirname, "preload.js") // use a preload script
    }
  })
  win.loadFile(path.join(__dirname,"public",'index.html'));

  ipcMain.on("showDataDirDialog",async (event,args)=>{
    let res = dialog.showOpenDialogSync(win,{properties:["openDirectory"]});
    if(res){
      res = res[0];
    }else{
      res = path.join(app.getAppPath(),"data");
    }
    event.reply('dataDir',res);
  })
}


app.on("ready",createWindow);