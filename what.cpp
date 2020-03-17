#include <Windows.h>

inline void press(int id){
	keybd_event( id,
		0x45,
		KEYEVENTF_EXTENDEDKEY | 0,
		0 );
}

inline void release(int id){
	keybd_event( id,
		0x45,
		KEYEVENTF_EXTENDEDKEY | KEYEVENTF_KEYUP,
		0);
}

int main() {
	for (int x=0;x<100;x++){
		press(160);
		press(65);
		release(160);
		press(65);
		Sleep(50);
	}
}
