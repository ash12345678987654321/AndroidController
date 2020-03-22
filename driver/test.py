import socket
import pyautogui


def control(cmd):
   print("DEBUG "+cmd);

   #python does not have a switch statement lol
   #time for some epic spqghatti coding

   cmd=cmd.split(" ")
   if (cmd[0]=="D"):
      pyautogui.keyDown(cmd[1])
   else:
      pyautogui.keyUp(cmd[1])

   #mouse movement handling (WIP)
   """
   x, y = pyautogui.position()
   print("curr pos: ",x," ",y)
   if (cmd.startswith("velocity")):
      cmd=cmd.split(" ")
      pyautogui.move(float(cmd[1]),-float(cmd[2]))
   """

def main():
   #making pyautogui faster but more unsafety
   pyautogui.PAUSE = 0
   pyautogui.FAILSAFE = False
   
   s = socket.socket()      
   print ("socket successfully created")


   while True:
      try:
         print("enter port: ",end="")
         port = int(input())      

         s.bind(('', port))         
         print ("socket binded to %s" %(port))
         break
      except:
         print ("error connecting to port")
     
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

