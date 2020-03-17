import socket
import pyautogui
  
s = socket.socket()          
print ("Socket successfully created")
  
port = 7800                

s.bind(('', port))         
print ("socket binded to %s" %(port))
  
s.listen(5)      
print ("socket is listening")           

  
while True: 
  
   c, addr = s.accept()      
   #print ('Got connection from', addr)
  
   message=c.recv(4096).decode("utf-8") ;

   if (message=="Q down"):
      pyautogui.keyDown('q')
   if (message=="Q up"):
      pyautogui.keyUp('q')
   if (message=="W down"):
      pyautogui.keyDown('w')
   if (message=="W up"):
      pyautogui.keyUp('w')

      
   c.close()
