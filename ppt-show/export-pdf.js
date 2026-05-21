const { exec } = require('child_process');
const path = require('path');
const fs = require('fs');

const htmlPath = path.resolve(__dirname, 'index.html');
const outputPath = path.resolve(__dirname, 'personal-intro.pdf');

const edgePaths = [
  'C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe',
  'C:\\Program Files\\Microsoft\\Edge\\Application\\msedge.exe',
];

let edgePath = null;
for (const p of edgePaths) {
  if (fs.existsSync(p)) {
    edgePath = p;
    break;
  }
}

if (!edgePath) {
  console.error('Microsoft Edge not found!');
  process.exit(1);
}

console.log(`Using Edge: ${edgePath}`);
console.log('Generating PDF...');

const htmlUrl = 'file:///' + htmlPath.replace(/\\/g, '/');
const cmd = `"${edgePath}" --headless --disable-gpu --no-pdf-header-footer --print-to-pdf="${outputPath}" --no-margins "${htmlUrl}"`;

exec(cmd, (error, stdout, stderr) => {
  setTimeout(() => {
    if (fs.existsSync(outputPath)) {
      console.log(`PDF saved to: ${outputPath}`);
    } else {
      console.error('PDF generation may have failed. Check the output file.');
    }
  }, 5000);
});
