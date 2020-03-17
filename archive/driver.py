import sys
import bluetooth
import pyautogui
import socket
from time import sleep

#this should match the uuid in the app
uuid="35e7a034-25c3-4d78-a9b5-0b91e795251f"

#we are the server
def connection(): #call this everytime need too reconnect
    server_sock = bluetooth.BluetoothSocket(bluetooth.RFCOMM)
    server_sock.bind(("", bluetooth.PORT_ANY))
    server_sock.listen(1)

    port = server_sock.getsockname()[1]
    
    bluetooth.advertise_service(server_sock, "SampleServer", service_id=uuid,
                                service_classes=[uuid, bluetooth.SERIAL_PORT_CLASS],
                                profiles=[bluetooth.SERIAL_PORT_PROFILE],
                                # protocols=[bluetooth.OBEX_UUID]
                                )

    print("Waiting for connection on RFCOMM channel", port)

    client_sock, client_info = server_sock.accept()
    print("Accepted connection from", client_info)

    client_sock.settimeout(2)

    #https://stackoverflow.com/questions/16745409/what-does-pythons-socket-recv-return-for-non-blocking-sockets-if-no-data-is-r
    while True:
        try:
            msg = client_sock.recv(4096)
        except socket.timeout as e:
            err = e.args[0]
            # this next if/else is a bit redundant, but illustrates how the
            # timeout exception is setup
            if err == "timed out":
                sleep(1)
                print ("recv timed out, retry later")
                continue
            else:
                print (e)
                sys.exit(1)
        except socket.error as e:
            # Something else happened, handle error, exit, etc.
            print (e)
            sys.exit(1)
        else:
            if len(msg) == 0:
                print ("orderly shutdown on server end")
                sys.exit(0)
            else:
                print("received message: ",msg)

    client_sock.close()
    server_sock.close()
        
def main():
    while True: #keep attempting to reconnect
        connection()

if __name__=="__main__": #idk why people do this
    main()
