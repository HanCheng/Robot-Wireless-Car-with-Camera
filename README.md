Robot-Wireless-Car-with-Camera
==============================

Implemented a robot car by using WIFI module and STM32(Cortex-M3 ARM processor) board to drive the car for four directions, 
An android app was developed to control the car and get image signals from the camera in front of car

This contains source code for andorid app part. It does not include source code for ARM 

Basically, the android app is to send some control signals through WIFI, and the car, embedded with WIFI router, can get the 
WIFI signal from WIFI router and ARM processor will get the control signal send to the motor driver board. Then robotic car 
can move for directions. 
