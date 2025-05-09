# TravelBooking
This is a Java-based Travel Booking System built with Jakarta EE, using technologies like JSF (Jakarta Faces), JPA (Jakarta Persistence API), and MySQL for data storage. The system allows users to browse hotels, select room types, make reservations, and complete bookings with integrated payment support.

✨ Features
Hotel and Room Listing (Standard, Deluxe, Family, Luxury)

City-based hotel search

User-friendly booking form with guest details

Check-in and Check-out date selection

Real-time room availability

Payment integration (e.g., Paystack/Ozow/PayU)

Admin panel for managing hotels, rooms, and reservations

Secure user authentication and role-based access (if implemented)

🛠️ Tech Stack
Jakarta EE

JSF (Jakarta Faces)

JPA / Hibernate

MySQL

Maven

GlassFish / Payara Server

HTML5, CSS3, PrimeFaces (optional UI components)

📁 Project Structure
entities/ – JPA Entity Classes

dao/ – Data Access Objects

services/ – Business Logic

beans/ – JSF Managed Beans

webapp/ – XHTML Pages and UI

resources/ – CSS, JS, and images

META-INF/, WEB-INF/ – Configuration files

🚀 Getting Started
Clone the repository

Set up a local MySQL database

Import the SQL schema provided

Deploy on GlassFish or Payara Server

Visit http://localhost:8080/travelbooking
