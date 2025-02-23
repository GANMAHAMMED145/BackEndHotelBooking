FROM  openjdk:8
EXPOSE 8080
ADD target/ResidenceHotelBooking.jar  ResidenceHotelBooking.jar
ENTRYPOINT ["java", "-jar", "/ResidenceHotelBooking.jar"]