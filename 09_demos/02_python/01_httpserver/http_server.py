import BaseHTTPServer
import shutil
import logging
import mylog
try:
    from cStringIO import StringIO
except ImportError:
    from StringIO import StringIO

__log_home__ = 'D:/logs/applog.log'
log = mylog.MyLog(__log_home__)

class SimpleHTTPRequestHandler(BaseHTTPServer.BaseHTTPRequestHandler):
    
    def do_GET(self):
        self.sendMessage(message="GET Success")

    def do_POST(self):
        length = int(self.headers['content-length'])
        data = self.rfile.read(length)
        log.info(data)
        self.sendMessage("1")
        
    def sendMessage(self, message,status=200):
#        length = len(message)
#        self.send_response(status)
#        self.send_header("Content-Length", str(length))
#        self.end_headers()
#        self.send_response(status, message)
        f = StringIO()
        f.write(message)
        length = f.tell()
        f.seek(0)
        self.send_response(status)
        self.send_header("Content-Length", str(length))
        self.end_headers()
        shutil.copyfileobj(f, self.wfile)
        f.close()

if __name__ == '__main__':
    BaseHTTPServer.test(SimpleHTTPRequestHandler)
