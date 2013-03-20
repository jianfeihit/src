import socket
import mailsender

ip = socket.gethostbyname_ex(socket.gethostname())
mailSender = mailsender.MailSender()
mailSender.sendMail(str(ip))

