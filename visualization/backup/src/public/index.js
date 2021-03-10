function main() {    
    const canvas = document.getElementById("canvas");
    /**@type {CanvasRenderingContext2D} */
    const ctx = canvas.getContext("2d");
    
    const side = Math.min(window.innerWidth, window.innerHeight);
    
    canvas.width = side;
    canvas.height = side;
    
    let static = getStaticParsed();
    let output = getOutputParsed();
    let particles = getDynamicParsed();
    
    
    if (static.numParts != particles.length || static.numParts != output.length) {
        console.error("static, dynamic and output dont match");
    } else {
        for (let i = 0; i < particles.length; i++) {
            particles[i] = {
                ...particles[i],
                id: output[i].id,
                neighbours: output[i].neighbours.reduce((acc, v) => { acc[v] = true; return acc }, {}),
                ...static.particles[i]
            }
        }
        particles = particles.reduce((acc, v) => {
            acc[v.id] = v;
            return acc;
        }, {})
        
        const lenUnit = side / static.long;
    
        function drawParticles(spid) {
            for (const p in particles) {
                if (p == spid) {
                    ctx.fillStyle = "green";
                } else if (particles[p].neighbours[spid]) {
                    ctx.fillStyle = "red";
                } else {
                    ctx.fillStyle = "black";
                }
                let x = Math.floor(lenUnit * particles[p].x);
                let y = Math.floor(lenUnit * particles[p].y);
                let radius = Math.floor(lenUnit * particles[p].radius);
                ctx.beginPath();
                ctx.arc(x, y, radius, 0, 2 * Math.PI);
                ctx.fill();
            }
        }
        drawParticles();
    
        canvas.onclick = function (e) {
            var rect = canvas.getBoundingClientRect();
            let mousePos = {
                x: e.clientX - rect.left,
                y: e.clientY - rect.top
            };
            let spid;
            for (const p in particles) {
                if (Math.sqrt(
                    Math.pow(mousePos.x - (particles[p].x * lenUnit), 2) +
                    Math.pow(mousePos.y - (particles[p].y * lenUnit), 2)
                ) < (particles[p].radius * lenUnit)) {
                    spid = p;
                    break;
                }
            }
            drawParticles(spid);
        }
    }
}
setDataDirLoaded(main);