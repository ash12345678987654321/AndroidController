import socket
import pyautogui


def control(cmd):
   #print("DEBUG "+cmd);

   #python does not have a switch statement lol
   #time for some epic spqghatti coding

   if cmd=="Q down":
      pyautogui.keyDown('q')
   elif cmd=="Q up":
      pyautogui.keyUp('q')
   elif cmd=="W down":
      pyautogui.keyDown('w')
   else:
      pyautogui.keyUp('w')


def main():
   #making pyautogui faster but more unsafety
   pyautogui.PAUSE=0
   pyautogui.FAILSAFE = False
   
   s = socket.socket()          
   print ("Socket successfully created")
     
   port = 7800 #why did i choose 7800            

   s.bind(('', port))         
   print ("socket binded to %s" %(port))
     
   s.listen(5)      
   print ("socket is listening")           

     
   while True: 
     
      c, addr = s.accept()
      #print ('Got connection from', addr)

      message=c.recv(4096).decode("utf-8"); #message comes in byte array so change it to string first
      message=message.split("|") #i use | to differtiate commands
      
      #print("DEBUG: ",message)
      
      for i in range(0,len(message)-1):
         control(message[i])
         
      c.close()



if __name__=="__main__":
   main()

