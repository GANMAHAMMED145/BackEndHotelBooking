FROM  openjdk:17-jdk-slim
EXPOSE 8080
ADD target/ResidenceHotelBooking.jar  ResidenceHotelBooking.jar
ENTRYPOINT ["java", "-jar", "/ResidenceHotelBooking.jar"]